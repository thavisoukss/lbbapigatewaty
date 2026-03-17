package com.lbb.apigateway.config;

import com.lbb.apigateway.filter.MyLogResponseRewriteFunction;
import com.lbb.apigateway.filter.MyLogRewriteFunction;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           MyLogRewriteFunction requestLog,
                                           MyLogResponseRewriteFunction responseLog) {
        return builder.routes()
                .route("customer_service_route", r -> r.path("/api/customers/**")
                        .filters(f -> f

                                .modifyRequestBody(String.class, String.class, requestLog)
                                .modifyResponseBody(String.class, String.class, responseLog)
                        )
                        .uri("http://localhost:8081"))


                .route("core_banking_route", r -> r.path("/api/corebanking/**")
                        .filters(f -> f
                                .modifyRequestBody(String.class, String.class, requestLog)
                                .modifyResponseBody(String.class, String.class, responseLog)
                        )
                        .uri("http://localhost:8082"))


                        .route("deeplink_route", r -> r.path("/api/deeplink/**")
                .filters(f -> f
                        .modifyRequestBody(String.class, String.class, requestLog)
                        .modifyResponseBody(String.class, String.class, responseLog)
                )
                .uri("http://localhost:8083"))

                .route("lmps_route", r -> r.path("/api/lmps/**")
                        .filters(f -> f
                                .modifyRequestBody(String.class, String.class, requestLog)
                                .modifyResponseBody(String.class, String.class, responseLog)
                        )
                        .uri("http://localhost:8084"))
                .build();
    }
}
