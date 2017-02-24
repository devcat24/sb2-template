package com.github.devcat24.mvc.entity.mm;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString               // @ToString (exclude = {"deptno"})
@Builder // Support builder pattern method for creating instance ex. Item item = Item.builder().itemno(1002).name("pen").build();
@Slf4j

@Entity(name="jpa_lab_member_trends")
@Table(name="jpa_lab_member_trends")
public class MemberTrends implements Serializable{
    @EmbeddedId
    @Getter @Setter
    private MemberTrendsPK memberTrendsPK;

    @Getter @Setter
    private int count;

}
