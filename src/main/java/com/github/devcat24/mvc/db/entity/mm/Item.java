package com.github.devcat24.mvc.db.entity.mm;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode(exclude = {"categories"})      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString(exclude = {"categories"})               // @ToString (exclude = {"deptno"})
//@Builder                // Support builder pattern method for creating instance ex. Item item = Item.builder().itemno(1002).name("pen").build();
@Slf4j

@Entity(name="jpa_lab_item")
@Table(name="jpa_lab_item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE")
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    @Setter @Getter
    private Long id;

    @Setter @Getter
    private String name;

    @Setter @Getter
    private int price;

    @Setter @Getter
    private int stockQuantity;

    @Setter @Getter
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}
