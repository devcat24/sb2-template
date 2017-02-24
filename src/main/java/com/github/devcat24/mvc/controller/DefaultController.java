package com.github.devcat24.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@Controller("DefaultController")
public class DefaultController {
    //private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DefaultController.class);

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value={"/"})
    public String welcome(HttpServletRequest req, HttpServletResponse res) throws Exception {
        return "welcome_jstl" ;
    }

}
