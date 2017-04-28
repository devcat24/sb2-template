package com.github.devcat24.mvc.svc.db.entity.mm;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode //(callSuper = false)      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString               // @ToString (exclude = {"deptno"})
@Builder                // Support builder pattern method for creating instance ex. Item item = Item.builder().itemno(1002).name("pen").build();
@Slf4j

@Entity(name="jpa_lab_item_album")
@Table  //(name="jpa_lab_item")
@DiscriminatorValue("A")
public class Album extends Item {
    @Setter @Getter
    private String artist;

    @Setter @Getter
    private String etc;
}
