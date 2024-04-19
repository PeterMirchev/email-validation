package com.emailvalidator.client.maileroo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailerooResponse {

    private String email;
    private boolean format_valid;
    private boolean mx_found;
    private boolean disposable;
    private boolean role;
    private boolean free;
    private String domain_suggestion;
}
