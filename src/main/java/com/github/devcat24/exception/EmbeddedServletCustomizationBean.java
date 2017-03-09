package com.github.devcat24.exception;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class EmbeddedServletCustomizationBean implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/app_not_found"));
        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/intr_error"));
    }
}
