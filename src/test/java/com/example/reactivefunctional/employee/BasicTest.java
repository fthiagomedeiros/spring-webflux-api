package com.example.reactivefunctional.employee;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class BasicTest {

    @Test
    public void testFunctional() {
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(elements::add);

        Flux.just("1", "2").map(e -> e).log().map(value -> value).subscribe(System.out::printf);

        System.out.println(elements);
    }
}
