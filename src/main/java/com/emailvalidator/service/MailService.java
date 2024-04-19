package com.emailvalidator.service;

import com.emailvalidator.client.maileroo.MailerooClient;
import com.emailvalidator.client.maileroo.dto.MailerooRequest;
import com.emailvalidator.client.maileroo.dto.MailerooResponse;
import com.emailvalidator.config.MailerooProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailService {


    private final MailerooClient mailerooClient;
    private final MailerooProperties mailerooProperties;


    //TODO: fix this problem with the bean exception creation
    public MailService(MailerooClient mailerooClient, MailerooProperties mailerooProperties) {
        this.mailerooClient = mailerooClient;
        this.mailerooProperties = mailerooProperties;
    }

    public List<MailerooResponse> checkMails(List<MailerooRequest> mailerooRequests) {

        List<MailerooResponse> responses = new ArrayList<>();
        String apiKey = mailerooProperties.getApiKey();



        mailerooRequests.forEach(m -> {
            m.setApiKey(apiKey);
            MailerooResponse check = mailerooClient.check(m);
            responses.add(check);
        });

        return responses;
    }
}
