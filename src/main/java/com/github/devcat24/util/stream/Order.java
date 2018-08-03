package com.github.devcat24.util.stream;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
class Order     {
    private Long id;
    private Date date;
    private User user;
}