package com.github.devcat24.mvc.entity.mm;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString               // @ToString (exclude = {"deptno"})
@Builder                // Support builder pattern method for creating instance ex. Item item = Item.builder().itemno(1002).name("pen").build();
@Slf4j

@Entity(name="jpa_lab_item_book")
@Table  //(name="jpa_lab_item")
@DiscriminatorValue("B")
public class Book extends Item {
    @Setter @Getter
    private String author;

    @Setter @Getter
    private String isbn;
}
