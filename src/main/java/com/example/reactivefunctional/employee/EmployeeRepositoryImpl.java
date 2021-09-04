package com.example.reactivefunctional.employee;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component("employeeRepo")
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final List<Employee> employee = new ArrayList<>();

    public Mono<Employee> save(Employee employee) {
        this.employee.add(employee);
        return Mono.just(employee);
    }

    @Override
    public Flux<Employee> findAll() {
        return Flux.fromIterable(employee);
    }

}
