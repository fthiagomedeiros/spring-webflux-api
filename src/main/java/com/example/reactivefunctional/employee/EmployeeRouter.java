package com.example.reactivefunctional.employee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class EmployeeRouter {

    @Bean
    public RouterFunction<ServerResponse> employeeRoutes(EmployeeHandler employeeHandler) {
        return RouterFunctions.route()
                .path("/employee", builder -> builder
                        .GET("date", accept(MediaType.APPLICATION_JSON), employeeHandler::validateDate)
                        .POST("", accept(MediaType.APPLICATION_JSON), employeeHandler::createEmployee)
                        .GET("/{id}", accept(MediaType.APPLICATION_JSON), employeeHandler::getEmployee)
                        .GET("", accept(MediaType.APPLICATION_JSON), employeeHandler::getAllEmployees))
                .build();
    }
}
