package com.emailvalidator.api;

import com.emailvalidator.client.maileroo.dto.MailerooRequest;
import com.emailvalidator.client.maileroo.dto.MailerooResponse;
import com.emailvalidator.service.MailService;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mail-bulk-validations")
@EnableFeignClients
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }


    @PostMapping("/check")
    public List<MailerooResponse> check(@RequestBody List<String> mails) {


        return mailService.checkMails(mails);
    }

    private MailerooRequest mapToRequest(String m) {
        return MailerooRequest.builder()
                .emailAddress(m)
                .build();
    }
}
