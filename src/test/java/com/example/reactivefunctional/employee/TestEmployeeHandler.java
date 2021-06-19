package com.example.reactivefunctional.employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes =
        {EmployeeRouter.class, EmployeeHandler.class, EmployeeRepositoryImpl.class, EmployeeRepository.class})
@WebFluxTest
public class TestEmployeeHandler {

    @Autowired
    private ApplicationContext context;

    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    public void testGetHello() {
        webTestClient
                .get()
                .uri("/employees/1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("name").isEqualTo("Francisco");
    }

    @Test
    public void shouldCreateNewEmployee() {
        //Test should fail because of different firstName

        Employee e = Employee.builder()
                .firstName("francisco")
                .lastName("medeiros")
                .build();

        webTestClient
                .post()
                .uri("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(e), Employee.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("firstName").isEqualTo("Francisco");
    }

}

