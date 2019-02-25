// https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html
package com.github.devcat24.mvc.controller;

import com.github.devcat24.exception.CustomIntlSvrError;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebMVCController {

    @CrossOrigin(origins="http://localhost:9000") // This method can be invoked from "http://localhost:9000" as the exception of SOP(Single-Origin Policy)
    @RequestMapping("/cors_call")
    public String userKey(@RequestParam(required=false, defaultValue="10000A") String userName) {
        return userName.substring(0, 1) + "00001";
    }

    @RequestMapping("/jsp_page")
    public String jspPage(){
        return "jsp-01";
    }
    @RequestMapping(value={"/ftl_page"})
    public String ftlPage(Model model) {
        model.addAttribute("title", "Hello");
        model.addAttribute("body", "World");
        return "ftl-01" ;
    }
    @RequestMapping("/err5xx")
    public String err5xx() throws CustomIntlSvrError {
        throw new CustomIntlSvrError("Manual RuntimeException is thrown");
    }
}
