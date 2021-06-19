package com.example.reactivefunctional.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@AllArgsConstructor
public class EmployeeHandler {

    private final EmployeeRepository repo;

    public Mono<ServerResponse> createEmployee(ServerRequest request) {
        Mono<Employee> employee = request.bodyToMono(Employee.class);
        return ok()
                .body(fromPublisher(employee.flatMap(repo::save), Employee.class));
    }

    public Mono<ServerResponse> getEmployee(ServerRequest serverRequest) {
        return null;
    }

    public Mono<ServerResponse> getAllEmployees(ServerRequest serverRequest) {
        Flux<List<Employee>> listOfEmployees = repo.findAll();
        return ok().body(repo.findAll(), Employee.class);
    }

    public Mono<ServerResponse> validateDate(ServerRequest serverRequest) {
        Mono<Optional<String>> v1 = Mono.just(serverRequest.queryParam("dateTo"));
        Mono<Optional<String>> v2 = Mono.just(serverRequest.queryParam("dateFrom"));

        Mono<Boolean> monoTest = Mono.zip(v1, v2, this::validateDate);

        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(monoTest, Boolean.class);
    }

    private Boolean validateDate(Optional<String> v3, Optional<String> v4) {
        return LocalDate.parse(v3.get()).isBefore(LocalDate.parse(v4.get())) ||
        LocalDate.parse(v3.get()).isEqual(LocalDate.parse(v4.get()));
    }

}
