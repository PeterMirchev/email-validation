package com.emailvalidator.client.validemail;

import com.emailvalidator.client.validemail.dto.ValidEmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "validEmailClient", url = "https://api.ValidEmail.net")
public interface ValidEmailClient {

    @GetMapping("/")
    ValidEmailResponse validate(@RequestParam(name = "email") String email,
                                @RequestParam(name = "token") String token);
}
