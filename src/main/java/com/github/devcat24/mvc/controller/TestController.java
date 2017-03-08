package com.github.devcat24.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Slf4j
@Controller("TestController")
public class TestController {
    //private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TestController.class);

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value={"wel"})
    public String welcome() throws Exception {
        return "welcome_jstl" ;
    }

    @ResponseBody
    @RequestMapping(value="/msgSrc")
    public String msgSrc() throws Exception {
        log.info("Local.US     : " + messageSource.getMessage("hello.test", null, "no such message", Locale.US));
        log.info("Local.KR     : " + messageSource.getMessage("hello.test", null, "no such message", Locale.KOREA));
        return "ok !";
    }

}
