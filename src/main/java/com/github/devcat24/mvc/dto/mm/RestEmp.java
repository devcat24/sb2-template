package com.github.devcat24.mvc.dto.mm;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString               // @ToString (exclude = {"deptno"})
@Builder
// Support builder pattern method for creating instance ex. Item item = Item.builder().itemno(1002).name("pen").build();
@Slf4j
// lombok annotation for logging (@Slf4j/@Log4j/@Log4j2/@CommonsLog/@Log) -> invoke simply with 'log.info("Sample message");'


// To prevent infinite loop in JPA, Jackson annotation '@JsonManagedReference' & '@JsonBackReference' should be defined
//@Entity(name="RestEmp")
//@Table(name="tb_rest_emp")
public class RestEmp implements Serializable {
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    // @JsonProperty("MyName")  // -> matches 'Json' data field name (to object variable name)
    private String name;
}
