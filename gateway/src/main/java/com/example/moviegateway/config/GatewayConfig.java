package com.example.moviegateway.config;

/**
 * @Author liwei
 * @Date 2024/8/23 22:02
 * @Version 1.0
 */

import com.example.moviegateway.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("vehicle", r -> r.path("/vehicle/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://vehicle"))
                .route("user-services", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-services"))
                .route("movie-provider", r -> r.path("/provider/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://movie-provider"))
                .build();
    }
}
