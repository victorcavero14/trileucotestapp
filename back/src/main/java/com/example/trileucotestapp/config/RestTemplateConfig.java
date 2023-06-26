package com.example.trileucotestapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {

    /**
     * Creates and configures a RestTemplate bean.
     *
     * @return the RestTemplate bean.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
