package com.github.devcat24.mvc.controller;

import com.github.devcat24.config.prop.ApplicationVersion;
import com.github.devcat24.mvc.svc.JPAService;
import com.github.devcat24.util.json.GsonUtil;
import com.github.devcat24.util.net.RestTemplateExample;
import com.github.devcat24.util.reflection.ReflectionSampleBean;
import com.github.devcat24.util.regex.RegExpExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Controller("DefaultController")
public class DefaultController {
    //private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DefaultController.class);


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

    @RequestMapping(value={"/"})
    public String welcome(HttpServletRequest req, HttpServletResponse res, Model model) throws Exception {
        model.addAttribute("applicationVersion", ApplicationVersion.applicationVersion);
        model.addAttribute("keepAlivePing", keepAlivePing);
        return "welcome_jstl" ;
    }




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

    @RequestMapping(value={"/basicContents"})
    public String welcome(Model model) throws Exception {
        model.addAttribute("applicationVersion", ApplicationVersion.applicationVersion);
        return "basic_contents" ;
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





    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value="/reflectionEx01")
    public String reflectionEx01() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ReflectionSampleBean rsBean = new ReflectionSampleBean();
        Class rsBeanClass = rsBean.getClass();
        //rsBeanClass.getName(); rsBeanClass.getPackage();

        Field[] fields = rsBeanClass.getDeclaredFields();  // rsBeanClass.getFields();       =>  from inheritance

        String [] fieldStringList = new String[fields.length];
        for(int ins=0; ins < fields.length ; ins++) {
            //for(Field field : fields){
            fields[ins].setAccessible(true);

            Annotation[] annotations = fields[ins].getAnnotations();
            String [] annotationNames = new String[annotations.length];
            for(int inx=0; inx < annotations.length ; inx ++) {
                Class<? extends Annotation> type = annotations[inx].annotationType();
                annotationNames[inx] = type.getName();
            }
            String annotationString = StringUtils.join(annotationNames, ", ");
            fieldStringList[ins] = fields[ins].getName() + " [" + annotationString + "]";
        }
        //Method [] methods = rsBeanClass.getDeclaredMethods();  // rsBeanClass.getMethods();  =>  from inheritance
        Method setAddrMethod = rsBeanClass.getDeclaredMethod("setAddress", String.class);
        setAddrMethod.setAccessible(true);
        setAddrMethod.invoke(rsBean,"New Address");

        return StringUtils.join(fieldStringList, ", ");
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

    /**
     * Response ping (prvent session timeout)
     */
    @ResponseBody
    @RequestMapping(value="/ping", produces = "application/json")
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
}

