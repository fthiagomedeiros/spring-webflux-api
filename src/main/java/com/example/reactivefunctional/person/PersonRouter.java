package com.example.reactivefunctional.person;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class PersonRouter {

    @Bean
    public RouterFunction<ServerResponse> personRoutes(PersonHandler handler) {
        return RouterFunctions.route()
                .path("/person", builder -> builder
                        .GET("/{id}", accept(MediaType.APPLICATION_JSON), handler::getPersonById)
                        .GET("", accept(MediaType.APPLICATION_JSON), handler::getAllPersons))
                .build();
    }
}