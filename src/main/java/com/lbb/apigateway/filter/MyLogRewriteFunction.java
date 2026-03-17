package com.lbb.apigateway.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyLogRewriteFunction implements RewriteFunction<String, String> {

    private static final Logger logger = LogManager.getLogger(MyLogRewriteFunction.class);

    @Override
    public Publisher<String> apply(ServerWebExchange exchange, String body) {
        // ດຶງ Trace ID ມາສະແດງນຳ (ຖ້າມີ)
        String path = exchange.getRequest().getPath().toString();

        if (body != null) {
            // ເກັບ Log JSON Body ທີ່ສົ່ງເຂົ້າມາ
            logger.info("--- Request Body Log ---");
            logger.info("Path: {}, Payload: {}", path, body);
        } else {
            logger.info("Path: {}, Payload: Empty", path);
        }

        // ສົ່ງ Body ຕໍ່ໄປຫາ Service ໂດຍບໍ່ປ່ຽນແປງຂໍ້ມູນ
        return Mono.justOrEmpty(body);
    }
}
