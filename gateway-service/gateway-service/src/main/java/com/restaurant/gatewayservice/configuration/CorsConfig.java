package com.restaurant.gatewayservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * CorsConfig class configures the Cross-Origin Resource Sharing (CORS) settings for the application.
 * It allows specified origins, methods, and headers for incoming requests.
 */
@Configuration  // Indicates that this class contains Spring configuration
public class CorsConfig {

    /**
     * Configures and returns a CorsWebFilter bean to handle CORS settings.
     *
     * @return CorsWebFilter with the defined CORS configuration
     */
    @Bean
    public CorsWebFilter corsFilter() {
        // Create a new CorsConfiguration object to define CORS settings
        CorsConfiguration config = new CorsConfiguration();
        
        // Allow requests from the specified origin
        config.addAllowedOrigin("http://127.0.0.1:5500");
        
        // Allow specific HTTP methods
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        
        // Allow specific HTTP headers
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("X-Requested-With");
        config.addAllowedHeader("Accept");
        
        // Allow credentials to be included in requests
        config.setAllowCredentials(true);
        
        // Create a UrlBasedCorsConfigurationSource object and register the CORS configuration for all paths
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        // Return a new CorsWebFilter with the defined source
        return new CorsWebFilter(source);
    }
}
