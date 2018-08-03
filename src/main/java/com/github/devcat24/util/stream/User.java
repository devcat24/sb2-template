package com.github.devcat24.util.stream;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
class User      {
    private Long id;
    private String name;
    private Address address;
    private List<String> interest;

}
