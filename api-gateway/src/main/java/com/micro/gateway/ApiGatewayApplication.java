package com.micro.gateway;

import com.micro.gateway.filter.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthFilter filter) {
        return builder.routes()
                .route("product", r -> r.path("/api/*/products/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://PRODUCT-SERVICE"))
                .route("order", r -> r.path("/api/*/orders/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://ORDER-SERVICE"))
                .route("auth", r -> r.path("/auth/**")
                        .uri("lb://IDENTITY-SERVICE"))
                .build();
    }

}

