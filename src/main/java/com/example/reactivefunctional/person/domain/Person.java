package com.example.reactivefunctional.person.domain;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
public class Person {
    private final String id;
    private final String firstName;
    private final String familyName;
    private final int age;
}
