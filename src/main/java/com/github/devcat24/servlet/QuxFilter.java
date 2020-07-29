package com.github.devcat24.servlet;

import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class QuxFilter implements Filter {
    @SuppressWarnings("unused")
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(QuxFilter.class);

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

        if(! ((HttpServletRequest)req).getRequestURL().toString().contains("template/ping") ){
            logger.info("QuxFilter.doFilter: before processing : ");
        }
        // actions before servlet processing

        chain.doFilter(req, res);

        if(! ((HttpServletRequest)req).getRequestURL().toString().contains("template/ping") ){
            logger.info("QuxFilter.doFilter: after processing : " + (startTime - System.currentTimeMillis()));
        }
        // actions after servlet processing
    }
}
