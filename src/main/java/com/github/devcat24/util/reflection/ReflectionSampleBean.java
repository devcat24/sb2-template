package com.github.devcat24.util.reflection;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unused")
@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString               // @ToString (exclude = {"deptno"})
@Builder
@Slf4j
public class ReflectionSampleBean {
    @Getter @Setter @Autowired
    String name;
    String address;
    String phone;

    void setAddress(String address){
        this.address = address;
        System.out.println("ReflectionSampleBean: Address setter is invoked by Java Reflection API !");
    }
}
