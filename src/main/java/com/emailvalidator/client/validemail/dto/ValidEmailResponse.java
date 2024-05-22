package com.emailvalidator.client.validemail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidEmailResponse {

    public Boolean isValid;
    public Integer score;
    public String email;
    public String state;
    public String reason;
    public String domain;
    public Boolean free;
    public Boolean role;
    public Boolean disposable;
    public Boolean acceptAll;
    public Boolean tag;
    public String mXRecord;
    public List<String> emailAdditionalInfo;
}
