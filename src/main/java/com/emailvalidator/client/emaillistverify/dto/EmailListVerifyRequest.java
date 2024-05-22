package com.emailvalidator.client.emaillistverify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailListVerifyRequest {

    private String secret;

    private String email;
}
