package com.emailvalidator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "email-list-verify.external.client")
public class EmailListVerifyProperties {

    private String secret;
}
