package com.example.reactivefunctional.employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EmployeeRepository {
    Mono<Employee> save(Employee employee);
    Flux<List<Employee>> findAll();
}
