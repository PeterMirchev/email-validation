package com.emailvalidator.service;

import com.emailvalidator.client.dto.EmailCollectionResponse;
import com.emailvalidator.client.dto.EmailResponse;
import com.emailvalidator.client.emaillistverify.EmailListVerifyClient;
import com.emailvalidator.config.EmailListVerifyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.emailvalidator.config.Messages.*;

@Service
public class EmailService {

    private final SimpMessagingTemplate messagingTemplate;
    private final EmailListVerifyProperties emailListVerifyProperties;
    private final EmailListVerifyClient emailListVerifyClient;

    @Autowired
    public EmailService(SimpMessagingTemplate messagingTemplate,
                        EmailListVerifyProperties emailListVerifyProperties,
                        EmailListVerifyClient emailListVerifyClient) {

        this.messagingTemplate = messagingTemplate;
        this.emailListVerifyProperties = emailListVerifyProperties;
        this.emailListVerifyClient = emailListVerifyClient;
    }

    public EmailCollectionResponse checkMails(MultipartFile file) {

        List<String> emails;

        try {
            emails = parseEmailsFromFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<EmailResponse> responses = new ArrayList<>();
        String secret = emailListVerifyProperties.getSecret();

        int size = emails.size();
        AtomicInteger totalEmailsProcessed = new AtomicInteger();

        emails.forEach(email -> {
            String eVResponse = emailListVerifyClient.validate(secret, email);
            EmailResponse validated = mapToResponse(eVResponse, email);
            totalEmailsProcessed.getAndIncrement();
            responses.add(validated);
            sendProgressUpdate(size, totalEmailsProcessed.get());
        });

        return EmailCollectionResponse.builder()
                .verifier(VERIFIER)
                .emails(responses)
                .build();
    }

    private EmailResponse mapToResponse(String response, String email) {

        return EmailResponse.builder()
                .email(email)
                .response(response)
                .build();
    }

    private void sendProgressUpdate(int size, int totalEmailsProcessed) {

        double progressPercentage = ((double) totalEmailsProcessed / size) * 100;
        System.out.printf(FILE_PROGRESS, progressPercentage);
        messagingTemplate.convertAndSend(TOPIC_PROGRESS, progressPercentage);
    }

    private List<String> parseEmailsFromFile(MultipartFile file) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        List<String> emails = new ArrayList<>();

        String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);

        switch (extension) {
            case "csv":
                reader.lines().forEach(emails::add);
                break;
            case "txt":
            case "file":
                reader.lines()
                        .map(line -> line.split("[, ]+"))
                        .forEach(emailsArray -> emails.addAll(Arrays.asList(emailsArray)));
                break;
            default:
                throw new IllegalStateException(UNSUPPORTED_FILE_FORMAT);
        }

        return emails;
    }
}
