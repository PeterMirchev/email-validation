package com.emailvalidator.client.validemail.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidEmailRequest {

    @JsonProperty("email")
    private String email;
    @JsonProperty("token")
    private String token;
}
