package com.github.devcat24.mvc.entity.mm;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString               // @ToString (exclude = {"deptno"})
//@Builder                // Support builder pattern method for creating instance ex. Item item = Item.builder().itemno(1002).name("pen").build();
@Slf4j
@MappedSuperclass
public class BaseEntity {
    @Getter @Setter
    private Date createDate;

    @Getter @Setter
    private Date lastModifiedDate;
}
