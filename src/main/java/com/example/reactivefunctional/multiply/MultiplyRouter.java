package com.example.reactivefunctional.multiply;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class MultiplyRouter {

    @Bean
    public RouterFunction<ServerResponse> multiplyRoutes(OperatorHandler operatorHandler) {
        return RouterFunctions.route()
                .path("/multiply", builder -> builder
                        .GET(accept(MediaType.APPLICATION_JSON), operatorHandler::multiply)
                ).path("sum", builder -> builder
                        .GET(accept(MediaType.APPLICATION_JSON), operatorHandler::sum)
                )
                .build();
    }

}
