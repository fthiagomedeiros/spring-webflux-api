package com.example.reactivefunctional.person;

import com.example.reactivefunctional.person.domain.Person;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonService {
  private final List<Person> persons;

  public PersonService() {
    this.persons = Stream.of(
      // Create new instance of Person here, as many as you wish
      new Person("0", "", "", 0),
      new Person("1", "foo", "bar", 1),
      new Person("2", "two", "bar", 91),
      new Person("3", "three", "bar", 15)
   ).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
  }

  public Flux<Person> list() {
    return Flux.fromIterable(this.persons);
  }

  public Mono<Person> get(String id) {
    return Mono.just(persons.get(Integer.parseInt(id)));
  }
}