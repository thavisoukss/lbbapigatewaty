package com.lbb.apigateway.config;

import com.lbb.apigateway.filter.MyLogResponseRewriteFunction;
import com.lbb.apigateway.filter.MyLogRewriteFunction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    // ອ່ານຄ່າຈາກ environment variables ຫຼື application.properties
    @Value("${services.customer.url:http://customer-service:8081}")
    private String customerServiceUrl;

    @Value("${services.corebanking.url:http://corebanking-service:8082}")
    private String corebankingServiceUrl;

    @Value("${services.deeplink.url:http://deeplink-service:8083}")
    private String deeplinkServiceUrl;

    @Value("${services.lmps.url:http://lmps-service:8084}")
    private String lmpsServiceUrl;


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           MyLogRewriteFunction requestLog,
                                           MyLogResponseRewriteFunction responseLog) {
        return builder.routes()
                .route("customer_service_route", r -> r.path("/api/customers/**")
//                        .filters(f -> f
//
//                                .modifyRequestBody(String.class, String.class, requestLog)
//                                .modifyResponseBody(String.class, String.class, responseLog)
//                        )
                        .uri(customerServiceUrl))


                .route("core_banking_route", r -> r.path("/api/corebanking/**")
                        .filters(f -> f
                                .modifyRequestBody(String.class, String.class, requestLog)
                                .modifyResponseBody(String.class, String.class, responseLog)
                        )
                        .uri(corebankingServiceUrl))


                        .route("deeplink_route", r -> r.path("/api/deeplink/**")
                .filters(f -> f
                        .modifyRequestBody(String.class, String.class, requestLog)
                        .modifyResponseBody(String.class, String.class, responseLog)
                )
                .uri(deeplinkServiceUrl))

                .route("lmps_route", r -> r.path("/api/lmps/**")
                        .filters(f -> f
                                .modifyRequestBody(String.class, String.class, requestLog)
                                .modifyResponseBody(String.class, String.class, responseLog)
                        )
                        .uri(lmpsServiceUrl))
                .build();
    }
}
