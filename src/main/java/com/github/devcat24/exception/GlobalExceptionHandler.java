package com.github.devcat24.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView generalExceptionHandler(Exception ex, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exObj", ex);
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        mav.addObject("fullTrace", sw.toString());
        mav.addObject("exMsg", ex.getMessage());
        mav.setViewName("/gb_exception");

        log.error(sw.toString());
        return mav;
    }

    /* @ExceptionHandler(SQLException.class)
    public ModelAndView generalExceptionHandler(SQLException ex, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exObj", ex);
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        mav.addObject("fullTrace", sw.toString());
        mav.addObject("exMsg", ex.getMessage());
        mav.setViewName("/gb_exception");

        log.error(sw.toString());
        return mav;
    } */

}