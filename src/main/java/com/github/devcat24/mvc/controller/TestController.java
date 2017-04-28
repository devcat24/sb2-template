package com.github.devcat24.mvc.controller;

import antlr.debug.MessageAdapter;
import com.github.devcat24.config.prop.ApplicationVersion;
import com.github.devcat24.mvc.svc.JPAService;
import com.github.devcat24.mvc.svc.db.entity.mm.Member;
import com.github.devcat24.util.json.GraphAdapterBuilder;
import com.github.devcat24.util.json.GsonUtil;
import com.github.devcat24.util.net.RestTemplateExample;
import com.github.devcat24.util.regex.RegExpExample;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    // 1. Spring dependency injection - type 1: using field dependency injection
    //    > quite popular practice but, not recommended by Spring
    // @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    // @Autowired
    // private MessageSource messageSource;

    // 2. Spring dependency injection - type 2: using setter dependency injection
    //   > Official recommendation from Spring 3.x
    private MessageSource messageSource;
    @Autowired
    public void setMessageSource(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    // 3. Spring dependency injection - type 3: using constructor dependency injection
    //   > From Spring 4.x, officially setter injection over constructor is no longer encouraged
    //   > From Spring 4.3, '@Autowird' is no longer required for 'single-constructor'
    //     - https://spring.io/blog/2016/03/04/core-container-refinements-in-spring-framework-4-3
    // private MessageSource messageSource;
    // @Autowired
    // public TestController(MessageSource messageSource){
    //    this.messageSource = messageSource;
    // }

    private JPAService jpaService;
    @Autowired
    void setJpaService(JPAService jpaService){
        this.jpaService = jpaService;
    }

    private RestTemplateExample restTemplateExample;
    @Autowired
    void setRestTemplateExample(RestTemplateExample restTemplateExample){
        this.restTemplateExample = restTemplateExample;
    }

    @RequestMapping(value={"wel"})
    public String welcome(Model model) throws Exception {
        model.addAttribute("applicationVersion", ApplicationVersion.applicationVersion);
        return "welcome_jstl" ;
    }
    @RequestMapping(value={"/dbTemplate"})
    public String dbTemplate(Model model) throws Exception {
        model.addAttribute("allMembers", jpaService.getAllMembers());
        return "db_template" ;
    }

    @ResponseBody
    @RequestMapping(value="/findMemberById", produces={"application/json;charset=UTF-8"})
    public String findMemberById(Long id){
        /*Member foundMember = jpaService.findMemberById(id);

        GsonBuilder gsonBuilder = new GsonBuilder();
        new GraphAdapterBuilder().addType(Member.class).registerOn(gsonBuilder);
        Gson gson = gsonBuilder.setPrettyPrinting().setDateFormat("dd/MM/yyyy").create();
        return gson.toJson(foundMember);
        */
        return GsonUtil.toJsonString(jpaService.findMemberById(id));
    }

    @ResponseBody
    @RequestMapping(value="/findAllMembersFrom", produces={"application/json;charset=UTF-8"})
    public String findAllMembersFrom(Long id){
        return GsonUtil.toJsonString(jpaService.findAllMembersFrom(id));
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

    @ResponseBody
    @RequestMapping(value="/restTemplateExamples")
    public String restTemplateExample() throws Exception {
        return restTemplateExample.getJsonPostExample01();
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
