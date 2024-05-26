package com.emailvalidator.client.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmailCollectionResponse {

    private String verifier;
    private List<EmailResponse> emails;
}
