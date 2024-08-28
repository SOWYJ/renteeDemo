package com.example.moviegateway.config;

/**
 * @Author liwei
 * @Date 2024/8/16 10:22
 * @Version 1.0
 */

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
public class CorsFilterConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(CorsFilterConfiguration.class);
    private static final String ALLOWED_HEADERS = "x-requested-with, Content-Type, Authorization, credential, X-XSRF-TOKEN,token,username,client,SiteID,bachelor_authorization";
    private static final String ALLOWED_METHODS = "*";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String ALLOWED_Expose = "*";
    private static final String MAX_AGE = "18000L";

    public CorsFilterConfiguration() {
    }

    @Bean
    public WebFilter corsFilter() {
        return (ctx, chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            logger.info("intercept request with uri : " + request.getURI());
            if (CorsUtils.isCorsRequest(request)) {
                List<String> list = request.getHeaders().get("Origin");
                String origin = (String) list.get(0);
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();
                headers.add("Access-Control-Allow-Origin", StringUtils.isEmpty(origin) ? "*" : origin);
                headers.add("Access-Control-Allow-Methods", "*");
                headers.add("Access-Control-Max-Age", "18000L");
                headers.add("Access-Control-Allow-Headers", "x-requested-with, Content-Type, Authorization, credential, X-XSRF-TOKEN,token,username,client,SiteID,bachelor_authorization");
                headers.add("Access-Control-Expose-Headers", "*");
                headers.add("Access-Control-Allow-Credentials", "true");
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }

            return chain.filter(ctx);
        };
    }
}
