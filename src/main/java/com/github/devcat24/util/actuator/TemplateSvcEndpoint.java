package com.github.devcat24.util.actuator;

import com.github.devcat24.util.json.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TemplateSvcEndpoint  implements Endpoint<String> {
    /*private TemplateActuatorCheckSvc templateActuatorCheckSvc;
    @Autowired
    void setTemplateActuatorCheckSvc(TemplateActuatorCheckSvc templateActuatorCheckSvc){
        this.templateActuatorCheckSvc = templateActuatorCheckSvc;
    }*/

    public String getId() {
        return "TemplateSvc";
    }

    public boolean isEnabled() {
        return true; //  Return if the endpoint is enabled.
    }

    public boolean isSensitive() {
        return false; // Return true, if this endpoint wants to be exposed on security disabled condition
    }

    public String invoke(){
        //return templateActuatorCheckSvc.getCurrentStatus();

        Map<String, Object> map = new HashMap<>();
        map.put("status", "ok");
        map.put("success", 101);
        map.put("fail", 1);

        return GsonUtil.toJsonString(map);
    }

}
