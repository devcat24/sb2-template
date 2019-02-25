package com.github.devcat24.exception;

import lombok.*;

import java.util.Date;

@SuppressWarnings("WeakerAccess")
@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString               // @ToString (exclude = {"deptno"})
@Builder                // Support builder pattern method for creating instance ex. Item item = Item.builder().itemno(1002).name("pen").build();
public class ErrorDetails {

    @Setter @Getter
    private Date timestamp;
    @Setter @Getter
    private String message;
    @Setter @Getter
    private String details;
}