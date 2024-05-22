package com.emailvalidator.service;

import com.emailvalidator.client.dto.EmailResponse;
import com.emailvalidator.client.emaillistverify.EmailListVerifyClient;
import com.emailvalidator.config.EmailListVerifyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    public List<EmailResponse> checkMails(List<String> emails) {

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

        return responses;
    }

    private EmailResponse mapToResponse(String response, String email) {

        return EmailResponse.builder()
                .verifier("emaillistverify.com")
                .email(email)
                .response(response)
                .build();
    }

    private void sendProgressUpdate(int size, int totalEmailsProcessed) {

        double progressPercentage = ((double) totalEmailsProcessed / size) * 100;
        System.out.printf("File Progress: %.2f%%%n", progressPercentage);
        messagingTemplate.convertAndSend("/topic/progress", progressPercentage);
    }
}
