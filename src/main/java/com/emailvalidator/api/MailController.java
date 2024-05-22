package com.emailvalidator.api;

import com.emailvalidator.client.dto.EmailResponse;
import com.emailvalidator.service.EmailService;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mail-bulk-validations")
@EnableFeignClients
@CrossOrigin(origins = "http://localhost:3000")
public class MailController {

    private final EmailService mailService;

    public MailController(EmailService mailService) {
        this.mailService = mailService;
    }


    @PostMapping(value = "/emails", consumes = "application/json", produces = "application/json")
    public List<EmailResponse> validateEmails(@RequestBody List<String> mails) {


        return mailService.checkMails(mails);
    }

}
