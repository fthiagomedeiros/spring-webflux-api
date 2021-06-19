package com.example.reactivefunctional.employee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Component
@AllArgsConstructor
public class EmployeeHandler {

    private final EmployeeRepository repo;

    public Mono<ServerResponse> createEmployee(ServerRequest request) {
        Mono<Employee> employee = request.bodyToMono(Employee.class);
        return ServerResponse.ok()
                .body(fromPublisher(employee.flatMap(repo::save), Employee.class));
    }

    public Mono<ServerResponse> getEmployee(ServerRequest serverRequest) {
        return null;
    }

    public Mono<ServerResponse> getAllEmployees(ServerRequest serverRequest) {
        Flux<List<Employee>> listOfEmployees = repo.findAll();
        return ServerResponse.ok().body(repo.findAll(), Employee.class);
    }
}
