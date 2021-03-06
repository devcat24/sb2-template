package com.github.devcat24.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @SuppressWarnings("unused")
    @ExceptionHandler(CustomIntlSvrError.class)
    public ModelAndView generalExceptionHandler(Exception ex, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        mav.addObject("timestamp", new java.util.Date());
        mav.addObject("error", ex.getMessage());
        mav.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject("message", ex.getMessage());
        mav.addObject("exception", ex);
        mav.addObject("trace", ex.toString());

        //mav.setViewName("/5xx");
        mav.setViewName("/503");
        // -> send to error page
        //     a. this does not need any extra-controller
        //     b. automatically search for the matching
        //         /resources/public/error/*.html
        //         /resources/templates/***/***.ftl (template engine files)
        //         /webapp/WEB-INF/view/*.jsp  (jsp files)

        return mav;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidExceptionHandler(Exception ex,  WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ex.printStackTrace();
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
