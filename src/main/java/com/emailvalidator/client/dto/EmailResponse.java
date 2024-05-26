package com.emailvalidator.client.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailResponse {

    private String email;
    private String response;
}
