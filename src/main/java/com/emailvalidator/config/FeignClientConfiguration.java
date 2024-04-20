package com.emailvalidator.config;

import feign.Feign;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                // Add custom configurations here
                .logLevel(Logger.Level.BASIC)
                .errorDecoder(new ErrorDecoder.Default());
    }
}
