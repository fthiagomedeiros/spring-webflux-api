package com.example.reactivefunctional.employee;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
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
        return ok()
                .body(Mono.just(String.format("It works for %s ", serverRequest.pathVariable("id"))), String.class);
    }

    public Mono<ServerResponse> getAllEmployees(ServerRequest serverRequest) {
        Flux<List<Employee>> listOfEmployees = repo.findAll();
        return ok().body(repo.findAll(), Employee.class);
    }

    public Mono<ServerResponse> validateDate(ServerRequest serverRequest) {
        log.debug(String.format("call for method %s", serverRequest.queryParam("dateFrom")));
        Mono<Optional<String>> v1 = Mono.just(serverRequest.queryParam("dateTo"));
        Mono<Optional<String>> v2 = Mono.just(serverRequest.queryParam("dateFrom"));

        Mono<DateResponseV1> monoTest = Mono.zip(v1, v2, this::validateDate);

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(monoTest, DateResponseV1.class);
    }

    private DateResponseV1 validateDate(Optional<String> v3, Optional<String> v4) {
        if (v3.isPresent() && v4.isPresent()) {
            return new DateResponseV1(LocalDate.parse(v3.get()).isBefore(LocalDate.parse(v4.get())) ||
                    LocalDate.parse(v3.get()).isEqual(LocalDate.parse(v4.get())));
        }
        return new DateResponseV1(false);
    }

}