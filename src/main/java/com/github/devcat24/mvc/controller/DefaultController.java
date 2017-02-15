package com.github.devcat24.mvc.controller;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("DefaultController")
public class DefaultController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DefaultController.class);

    @RequestMapping(value={"/", "wel"})
    public String welcome() throws Exception {
        return "welcome_jstl" ;
    }

    @ResponseBody
    @RequestMapping(value="/items")
    public String items() throws Exception {
        String items = "{\n" +
                "    \"id\": \"0001\",\n" +
                "    \"name\": \"Cake\",\n" +
                "    \"image\": {\n" +
                "        \"url\": \"images/0001.jpg\",\n" +
                "        \"width\": 200,\n" +
                "        \"height\": 200,\n" +
                "        \"orders\": [\n" +
                "            {\"id\": \"1001\", \"size\": \"Regular\"},\n" +
                "            {\"id\": \"1002\", \"size\": \"Small\"}\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        return items;
    }

}
