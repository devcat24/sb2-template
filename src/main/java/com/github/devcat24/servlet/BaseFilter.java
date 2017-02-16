package com.github.devcat24.servlet;

import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;


public class BaseFilter implements Filter {
    @SuppressWarnings("unused")
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BaseFilter.class);

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private FilterConfig config;

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        /* String prop = config.getInitParameter("prop1"); */
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        long startTime = System.currentTimeMillis();
        logger.info("BaseFilter.doFilter: before processing : ");
        // actions before servlet processing

        chain.doFilter(req, res);

        logger.info("BaseFilter.doFilter: after processing : " + (startTime - System.currentTimeMillis()));
        // actions after servlet processing
    }
}
