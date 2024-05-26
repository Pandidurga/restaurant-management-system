package com.restaurant.gatewayservice.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ApiConfig class is used to configure the routes for the Spring Cloud Gateway.
 * It defines how the incoming requests are routed to different microservices.
 */
@Configuration  // Indicates that this class contains Spring configuration
public class ApiConfig {

    /**
     * Defines custom routes for the gateway using the RouteLocatorBuilder.
     * This method maps incoming paths to the URIs of the respective microservices.
     *
     * @param builder RouteLocatorBuilder to build the custom route locator
     * @return RouteLocator with the defined routes
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("menuitem-service", r -> r  // Route for menu-item service
                .path("/api/menu-items/**")  // Matches paths starting with /api/menu-items/
                .uri("http://localhost:8082"))  // Forwards to menu-item service running on port 8082
            .route("order-service", r -> r  // Route for order service
                .path("/api/orders/**")  // Matches paths starting with /api/orders/
                .uri("http://localhost:8085"))  // Forwards to order service running on port 8085
            .route("order-details-service", r -> r  // Route for order-details service
                .path("/api/order-details/**")  // Matches paths starting with /api/order-details/
                .uri("http://localhost:8085"))  // Forwards to order-details service running on port 8085
            .route("table-management-service", r -> r  // Route for table-management service
                .path("/api/restaurant-tables/**")  // Matches paths starting with /api/restaurant-tables/
                .uri("http://localhost:8084"))  // Forwards to table-management service running on port 8084
            .build();  // Builds the RouteLocator with the defined routes
    }
}
