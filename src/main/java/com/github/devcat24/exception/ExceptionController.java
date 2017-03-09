package com.github.devcat24.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller("ExceptionController")
public class ExceptionController {

    @RequestMapping(value = "/app_not_found")
    public String notFoundException() throws Exception {
        String errLogMsg = System.lineSeparator()
                + "----------------------------------------------" + System.lineSeparator()
                + (new SimpleDateFormat("yyyyMMdd HH:mm:ss")).format(new Date()) + "] " + System.lineSeparator()
                + "----------------------------------------------" + System.lineSeparator()
                + "404 Page Not Found" + System.lineSeparator()
                + "----------------------------------------------";
        log.error(errLogMsg);
        return "gb_exception";
    }

    @RequestMapping(value = "/intr_error")
    public String internalServerError() throws Exception {
        String errLogMsg = System.lineSeparator()
                + "----------------------------------------------" + System.lineSeparator()
                + (new SimpleDateFormat("yyyyMMdd HH:mm:ss")).format(new Date()) + "] " + System.lineSeparator()
                + "----------------------------------------------" + System.lineSeparator()
                + "500 Internal Server Error (Servlet Exception)" + System.lineSeparator()
                + "----------------------------------------------";
        log.error(errLogMsg);
        return "gb_exception";
    }


}
