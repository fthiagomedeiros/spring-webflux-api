package com.example.reactivefunctional.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class BasicTest {

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testFunctional() {
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(elements::add);

        Flux.just("1", "2").map(e -> e).log().map(value -> value).subscribe(System.out::printf);

        System.out.println(elements);
    }

    @Test
    public void testObjectMapper() throws JsonProcessingException {
        //Json formatter in Java

        String json =
                "[\n" +
                        "   {\n" +
                        "      \"id\":\"1\",\n" +
                        "      \"name\":\"XYZ\",\n" +
                        "      \"attributes\":{\n" +
                        "         \"key1\":\"1\",\n" +
                        "         \"key2\":\"2\"\n" +
                        "      }\n" +
                        "   }\n" +
                        "]";

        List<Student> participantJsonList = mapper.readValue(json, new TypeReference<>() {});
        System.out.println(participantJsonList);

        Student.builder()
                .name("Francisco")
                .id("10")
                .attributes(Maps.newHashMap("A", 1))
                .build();

    }
}

@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
@Data
class Student {
    private String id;
    private String name;
    private Map<String, Integer> attributes;
}
