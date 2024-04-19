package com.emailvalidator.client.maileroo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailerooRequest {

    private String apiKey;
    private String email;
}
