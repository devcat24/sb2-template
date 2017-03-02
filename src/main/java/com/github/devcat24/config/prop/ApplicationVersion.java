package com.github.devcat24.config.prop;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
public class ApplicationVersion implements Serializable {
    public static String applicationVersion = "";
}
