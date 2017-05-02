package com.github.devcat24.config.prop;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
public class DevUser implements Serializable {
    public static boolean loginAsDevUser = false;
    public static String userName = "nobody";
}
