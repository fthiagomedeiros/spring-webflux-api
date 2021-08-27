package com.example.reactivefunctional.multiply;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@AllArgsConstructor
public class OperatorHandler {

    public Mono<ServerResponse> multiply(final ServerRequest request) {
        Mono<Double> multiply = Mono.zip(Mono.just(request.queryParam("value1")),
                Mono.just(request.queryParam("value2")),
                (v1, v2) -> Double.parseDouble(v1.get()) * Double.parseDouble(v2.get()));
        return ok().body(multiply, Double.class);
    }

    public Mono<ServerResponse> sum(final ServerRequest request) {
        Mono<Double> sum = Mono.zip(Mono.just(request.queryParam("value1")),
                Mono.just(request.queryParam("value2")),
                (v1, v2) -> Double.parseDouble(v1.get()) + Double.parseDouble(v2.get()));
        return ok().body(sum, Double.class);
    }

    public Mono<ServerResponse> square(final ServerRequest request) {
        Mono<Double> square = Mono.fromSupplier(() ->
                Double.parseDouble(request.queryParam("value1").get()) *
                Double.parseDouble(request.queryParam("value1").get()));

        return ok().body(square, Double.class);
    }

    public Mono<ServerResponse> multiplyTo10(final ServerRequest request) {
        Flux<String> square = Flux.range(1, 10)
                .map(e -> String.format("%s x %s = %s\n", request.queryParam("value1").get(), e,
                        + e * Double.parseDouble(request.queryParam("value1").get())))
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(BAD_REQUEST,
                        "Ferrou")));

        return ok().body(square, String.class);
    }
}
