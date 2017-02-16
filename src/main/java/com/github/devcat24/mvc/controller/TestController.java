package com.github.devcat24.mvc.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("TestController")
public class TestController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value={"wel"})
    public String welcome(HttpServletRequest req, HttpServletResponse res) throws Exception {
        return "welcome_jstl" ;
    }

    @ResponseBody
    @RequestMapping(value="/items", produces={"application/json;charset=UTF-8"})
    public String items() throws Exception {
        return "{\n" +
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
    }

    @ResponseBody
    @RequestMapping(value="/msgSrc")
    public String msgSrc(HttpServletRequest req, HttpServletResponse res) throws Exception {
        return "ok !";
    }

}
