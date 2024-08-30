package com.example.moviegateway.filter;

/**
 * @Author liwei
 * @Date 2024/8/23 19:20
 * @Version 1.0
 */

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {
    /**
     * 开放api列表
     */
    public static final List<String> openApiEndpoints = List.of(
            "/auth/login",
            "/auth/enroll",
            "/activity/getAllCoupons",
            "/vehicle/getAllCarts"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
