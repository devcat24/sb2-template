package com.github.devcat24.mvc.svc.db.dto.mm;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Slf4j
public class MemberDTO implements Serializable {
    @Setter @Getter
    private Long id;

    @Setter @Getter
    private String name;

    @Setter @Getter
    private String city;

    @Setter @Getter
    private String street;

    @Setter @Getter
    private String zipcode;

}
