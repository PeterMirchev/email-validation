package com.emailvalidator.client.maileroo;

import com.emailvalidator.client.maileroo.dto.MailerooRequest;
import com.emailvalidator.client.maileroo.dto.MailerooResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mailerooClient", url = "https://verify.maileroo.net")
public interface MailerooClient {

    @PostMapping("/check")
    MailerooResponse check(@RequestBody MailerooRequest mailerooRequest);
}
