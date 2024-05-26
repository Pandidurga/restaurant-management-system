package com.restaurant.orderservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for the OrderService application.
 * Defines a bean for RestTemplate.
 */
@Configuration
public class OrderServiceConfig {

    /**
     * Creates a RestTemplate bean.
     * 
     * @return A new RestTemplate instance.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
