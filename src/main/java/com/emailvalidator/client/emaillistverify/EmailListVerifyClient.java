package com.emailvalidator.client.emaillistverify;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "emailListVerify", url = "https://apps.emaillistverify.com/api")
public interface EmailListVerifyClient {

    @GetMapping("/verifyEmail")
    String validate(@RequestParam(name = "secret") String secret,
                             @RequestParam(name = "email") String email);
}
