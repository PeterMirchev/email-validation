package com.emailvalidator.client.maileroo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailerooRequest {

    @JsonProperty("api_key")
    private String apiKey;
    @JsonProperty("email_address")
    private String emailAddress;
}
