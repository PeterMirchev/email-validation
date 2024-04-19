package com.emailvalidator.api;

import com.emailvalidator.client.maileroo.dto.MailerooRequest;
import com.emailvalidator.client.maileroo.dto.MailerooResponse;
import com.emailvalidator.service.MailService;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mail-bulk-validations")
@EnableFeignClients
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }


    @PostMapping()
    public List<MailerooResponse> check(List<String> mails) {
        List<MailerooRequest> requests = new ArrayList<>();
        mails.forEach(m -> {
            MailerooRequest mailerooRequest = mapToRequest(m);
            requests.add(mailerooRequest);
        });

        return mailService.checkMails(requests);
    }

    private MailerooRequest mapToRequest(String m) {
        return MailerooRequest.builder()
                .email(m)
                .build();
    }
}
