package com.github.devcat24.mvc.controller;

import com.github.devcat24.config.prop.ApplicationVersion;
import com.github.devcat24.mvc.svc.JMSSvc;
import com.github.devcat24.mvc.svc.JPASvc;
import com.github.devcat24.util.json.GsonUtil;
import com.github.devcat24.util.net.RestTemplateExample;
import com.github.devcat24.util.regex.RegExpExample;
import io.swagger.v3.oas.annotations.Hidden;
// import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
// import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller("DefaultController")
public class DefaultController {
    private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

    private JMSSvc jmsSvc;
    @Autowired
    public void setJmsSvc(JMSSvc jmsSvc){
        this.jmsSvc = jmsSvc;
    }

    @Value("${template.keep.alive.ping.interval}")
    private Long keepAlivePing;

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

    @SuppressWarnings("unused")
    @RequestMapping(value={"/"})
    public String welcome(@SuppressWarnings("unused") HttpServletRequest req, HttpServletResponse res, Model model){
        model.addAttribute("applicationVersion", ApplicationVersion.applicationVersion);
        model.addAttribute("keepAlivePing", keepAlivePing);
        //noinspection SpringMVCViewInspection
        return "welcome_jstl" ;
    }


    private JPASvc jpaSvc;
    @Autowired
    void setJpaSvc(JPASvc jpaSvc){
        this.jpaSvc = jpaSvc;
    }

    private RestTemplateExample restTemplateExample;
    @Autowired
    void setRestTemplateExample(RestTemplateExample restTemplateExample){
        this.restTemplateExample = restTemplateExample;
    }

    @RequestMapping(value={"/basicContents"})
    public String welcome(Model model) {
        model.addAttribute("applicationVersion", ApplicationVersion.applicationVersion);
        //noinspection SpringMVCViewInspection
        return "basic_contents" ;
    }

    @RequestMapping(value={"/dbTemplate"})
    public String dbTemplate(Model model){
        model.addAttribute("allMembers", jpaSvc.getAllMembers());
        return "db_template" ;
    }

    @ResponseBody
    @RequestMapping(value="/findMemberById", produces={"application/json;charset=UTF-8"})
    @Hidden
    public String findMemberById(Long id){
        /*Member foundMember = jpaSvc.findMemberById(id);

        GsonBuilder gsonBuilder = new GsonBuilder();
        new GraphAdapterBuilder().addType(Member.class).registerOn(gsonBuilder);
        Gson gson = gsonBuilder.setPrettyPrinting().setDateFormat("dd/MM/yyyy").create();
        return gson.toJson(foundMember);
        */
        return GsonUtil.toJsonString(jpaSvc.findMemberById(id));
    }

    @ResponseBody
    @RequestMapping(value="/findAllMembersFrom", produces={"application/json;charset=UTF-8"})
    @Hidden
    public String findAllMembersFrom(Long id){
        return GsonUtil.toJsonString(jpaSvc.findAllMembersFrom(id));
    }



    @ResponseBody
    @RequestMapping(value="/msgSrc", method = RequestMethod.GET)
    @Hidden // '@ApiIgnore' can be replaced by '@Parameter(hidden = true)' or '@Operation(hidden = true)' or @Hidden
    public String msgSrc(){
        logger.info("Local.US     : " + messageSource.getMessage("hello.test", null, "no such message", Locale.US));
        logger.info("Local.KR     : " + messageSource.getMessage("hello.test", null, "no such message", Locale.KOREA));
        return "ok !";
    }

    @ResponseBody
    @RequestMapping(value="/regExpExamples", method = RequestMethod.GET)
    //@Hidden // '@ApiIgnore' can be replaced by '@Parameter(hidden = true)' or '@Operation(hidden = true)' or @Hidden
    public String regExpExamples(){
        RegExpExample regExpExample = new RegExpExample();
        regExpExample.regExp01();
        return "ok !";
    }

    @ResponseBody
    @RequestMapping(value="/restTemplateExamples")
    @Hidden // '@ApiIgnore' can be replaced by '@Parameter(hidden = true)' or '@Operation(hidden = true)' or @Hidden
    public String restTemplateExample() throws Exception {
        return restTemplateExample.getJsonPostExample01();
    }


    /** Test code for Exception Handler */
    @ResponseBody
    @RequestMapping(value="/exceptionTest01")
    @Hidden // '@ApiIgnore' can be replaced by '@Parameter(hidden = true)' or '@Operation(hidden = true)' or @Hidden
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

    /**
     * Response ping (prvent session timeout)
     */
    @ResponseBody
    @RequestMapping(value="/ping", produces = "application/json")
    @Hidden // '@ApiIgnore' can be replaced by '@Parameter(hidden = true)' or '@Operation(hidden = true)' or @Hidden
    public String ping(HttpServletRequest request) {

        Map<String, String> map = new HashMap<>();
        map.put("status", "alive");
        String now = (new SimpleDateFormat("yyyyMMdd HH:mm:ss")).format(new Date());
        map.put("now", now);
        try {
            String ipAddr = request.getHeader("X-FORWARDED-FOR");
            if (ipAddr == null) {
                ipAddr = request.getRemoteAddr();
            }
            map.put("ip", ipAddr);
        } catch (Exception e) {
            //throw e;
        }
        return GsonUtil.toJsonString(map);
    }

    @ResponseBody
    @RequestMapping(value="/sendAmqQueue01")
    @Hidden
    public String sendAmqQueue01(){
//        String qName = "amq.exam.queue01";
//        //jmsTemplate.setPubSubDomain(true);  // -> publish as topic
//        jmsTemplate.convertAndSend("amq.exam.queue01", "New Message !");
        if(jmsSvc.sendAmqQueue01()) {
            return "sent!";
        }

        return "fail to send message!";
    }


}
