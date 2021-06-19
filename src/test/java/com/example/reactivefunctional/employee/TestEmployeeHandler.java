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
@ContextConfiguration(classes = {
                    EmployeeRouter.class,
                    EmployeeHandler.class,
                    EmployeeRepositoryImpl.class,
                    EmployeeRepository.class})
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
                .uri("/employee/1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$").isNotEmpty();
    }

    @Test
    public void shouldCreateNewEmployee() {
        Employee e = Employee.builder()
                .firstName("Francisco")
                .lastName("Medeiros")
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

    @Test
    public void dateToIsBeforeOrTheSameDateFrom() {
        String path = "/employee/date?dateTo=2020-01-01&dateFrom=2020-01-01";

        webTestClient
                .get()
                .uri(path)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .consumeWith(System.out::print)
                .jsonPath("response")
                .isEqualTo("true");
    }

    @Test
    public void dateToIsAfterOrTheSameDateFrom() {
        String path = "/employee/date?dateTo=2020-01-02&dateFrom=2020-01-01";

        webTestClient
                .get()
                .uri(path)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .consumeWith(System.out::print)
                .jsonPath("response")
                .isEqualTo("false");
    }

}

