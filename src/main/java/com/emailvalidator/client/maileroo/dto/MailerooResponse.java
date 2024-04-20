package com.emailvalidator.client.maileroo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailerooResponse {

    private boolean success;
    @JsonProperty("error_code")
    private String errorCode;
    private String message;
    private Map<String, Object> data;
}
