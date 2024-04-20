package com.emailvalidator.service;

import com.emailvalidator.client.maileroo.MailerooClient;
import com.emailvalidator.client.maileroo.dto.MailerooRequest;
import com.emailvalidator.client.maileroo.dto.MailerooResponse;
import com.emailvalidator.config.MailerooProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailService {

    private final MailerooClient mailerooClient;
    private final MailerooProperties mailerooProperties;

    public MailService(MailerooClient mailerooClient, MailerooProperties mailerooProperties) {
        this.mailerooClient = mailerooClient;
        this.mailerooProperties = mailerooProperties;
    }

    public List<MailerooResponse> checkMails(List<String> emails) {

        List<MailerooResponse> responses = new ArrayList<>();
        String apiKey = mailerooProperties.getApiKey();

        emails.forEach(email -> {
            MailerooRequest request = new MailerooRequest(apiKey, email);
            MailerooResponse response = mailerooClient.check(request);
            responses.add(response);
        });

        return responses;
    }
}
