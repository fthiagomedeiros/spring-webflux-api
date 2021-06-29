package com.example.reactivefunctional.person;

import com.example.reactivefunctional.writer.JsonWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
@AllArgsConstructor
public class PersonHandler {

    private final PersonService personService;

    public Mono<ServerResponse> getAllPersons(ServerRequest serverRequest) {
        return this.personService.list() // returns a Flux<Person>
                .collectList()
                // turns the flux into a Mono<List<T>> to allow sending a single response
                .flatMap(JsonWriter::write)
                .flatMap((json) -> ServerResponse.ok()
                        .body(Mono.just(json), String.class)
                ).onErrorResume(
                        JsonProcessingException.class,
                        (e) -> ServerResponse.status(INTERNAL_SERVER_ERROR)
                                .body(Mono.just(e.getMessage()), String.class)
                );
    }


    public Mono<ServerResponse> getPersonById(ServerRequest serverRequest) {
        return this.personService.get(serverRequest.pathVariable("id"))
                .flatMap(JsonWriter::write)
                .flatMap((json) -> ServerResponse.ok()
                        .body(Mono.just(json), String.class));
    }

}
