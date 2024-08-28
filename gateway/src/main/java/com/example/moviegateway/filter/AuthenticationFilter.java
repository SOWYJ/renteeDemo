package com.example.moviegateway.filter;

/**
 * @Author liwei
 * @Date 2024/8/23 19:19
 * @Version 1.0
 */

import com.example.moviegateway.token.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {
    private final RouterValidator routerValidator;

    @Autowired
    public AuthenticationFilter(RouterValidator routerValidator) {
        this.routerValidator = routerValidator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        System.out.println(request.getURI());
        //如果是被保护的，则进一步处理（获取token，进行验证）
        if (routerValidator.isSecured.test(request)) {
            if (this.isAuthMissing(request)) {
                //没有携带token，返回401（不转发请求）
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            final String token = this.getAuthHeader(request);
            System.out.println(token);
            try {
                //token失效，返回403（禁止访问）
                if (!JwtTokenUtils.validateToken(token.substring(7))) {
                    return this.onError(exchange, HttpStatus.FORBIDDEN);
                }
            } catch (Exception e) {
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }
        }
        //如果是开放API，直接放行
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    /**
     * 获取Header中的token
     * @param request
     * @return
     */
    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    /**
     * 判断是否携带了token
     * @param request
     * @return
     */
    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

}
