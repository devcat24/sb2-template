package com.github.devcat24.util.stream;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address   {
    private Integer zipcode;
    private String street;
    private String city;
}