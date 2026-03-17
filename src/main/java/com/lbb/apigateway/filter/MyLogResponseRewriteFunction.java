package com.lbb.apigateway.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyLogResponseRewriteFunction implements RewriteFunction<String, String> {

    private static final Logger logger = LogManager.getLogger(MyLogResponseRewriteFunction.class);

    @Override
    public Publisher<String> apply(ServerWebExchange exchange, String body) {
        // ເກັບ Log ຂໍ້ມູນທີ່ Service ຕອບກັບມາ
        if (body != null) {
            logger.info("--- Response Body Log ---");
            logger.info("Path: {}, Payload: {}", exchange.getRequest().getPath(), body);
        }

        // ສົ່ງ Body ຄືນໃຫ້ Client ໂດຍບໍ່ປ່ຽນແປງ
        return Mono.justOrEmpty(body);
    }
}
