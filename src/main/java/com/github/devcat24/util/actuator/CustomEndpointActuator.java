package com.github.devcat24.util.actuator;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Component
//@JmxEndpoint(id = "custom-endpoint")    // -> expose as 'JMX'
//@WebEndpoint(id = "custom-endpoint")    // -> expose as 'Web'
@Endpoint(id = "custom-endpoint")         // -> expose as 'Web' & 'JMX'
public class CustomEndpointActuator {
    @ReadOperation
    public Map<String, Object> readOperation() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "ok");
        map.put("success", 101);
        map.put("fail", 1);
        map.put("server.date", LocalDate.now().toString());
        map.put("server.time", LocalTime.now().toString());

        return map;
    }
    @WriteOperation
    public String writeOperation() {
        return "";
    }
    @DeleteOperation
    public String deleteOperation() {
        return "";
    }
}
