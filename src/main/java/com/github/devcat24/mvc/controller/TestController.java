package com.github.devcat24.mvc.controller;

import com.github.devcat24.config.prop.ApplicationVersion;
import com.github.devcat24.util.regex.RegExpExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

@Slf4j
@Controller("TestController")
public class TestController {
    //private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TestController.class);

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value={"wel"})
    public String welcome(Model model) throws Exception {
        model.addAttribute("applicationVersion", ApplicationVersion.applicationVersion);
        return "welcome_jstl" ;
    }

    @ResponseBody
    @RequestMapping(value="/msgSrc")
    public String msgSrc() throws Exception {
        log.info("Local.US     : " + messageSource.getMessage("hello.test", null, "no such message", Locale.US));
        log.info("Local.KR     : " + messageSource.getMessage("hello.test", null, "no such message", Locale.KOREA));
        return "ok !";
    }

    @ResponseBody
    @RequestMapping(value="/regExpExamples")
    public String regExpExamples() throws Exception {
        RegExpExample regExpExample = new RegExpExample();
        regExpExample.regExp01();
        return "ok !";
    }


    /** Test code for Exception Handler */
    @ResponseBody
    @RequestMapping(value="/exceptionTest01")
    public String exceptionTest01(String errType) throws Exception {
        if(StringUtils.isNotBlank(errType) && errType.equals("type1")){
            throw new SQLException("SQL Exception !!! (type1)");
        }   else if (StringUtils.isNotBlank(errType) && errType.equals("type2")){
            throw new IOException("Now IO Exception !!! (type2)");
        }    else if (StringUtils.isNotBlank(errType) && errType.equals("type3")) {
            throw new RuntimeException("Now Runtime Exception !!! (type3)");
        }   else if (StringUtils.isNotBlank(errType) && errType.equals("type4")) {
            throw new NullPointerException("Now NullPointerException !!! (type4)");
        }
        return "ok";
    }

}
