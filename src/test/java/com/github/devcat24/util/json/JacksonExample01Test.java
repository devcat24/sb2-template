package com.github.devcat24.util.json;

import org.junit.Test;

import java.io.IOException;

public class JacksonExample01Test {
    @Test
    public void testJsonWithJackson() throws IOException {
        JacksonExample01 jacksonExample01 = new JacksonExample01();
        jacksonExample01.jsonWithJackson();
    }
}
