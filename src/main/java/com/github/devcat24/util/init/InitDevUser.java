package com.github.devcat24.util.init;

import com.github.devcat24.config.prop.DevUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component("InitDevUser")
public class InitDevUser {
    @Value("${template.auth.loginAsDevUser}")
    private boolean loginAsDevUser;

    @Value("${template.auth.devUserName}")
    private String devUserName;

    @PostConstruct
    private void loadDevUser(){
        //if( StringUtils.trimToEmpty(loginAsDevUser).equals("true")
        if( loginAsDevUser
                && (!StringUtils.trimToEmpty(devUserName).equals("nobody"))){

            String newLine = System.getProperty("line.separator");
            String loginWithDevMode     = newLine
                    + "====================================" + newLine
                    + " Set authentication as dev mode     " + newLine
                    + "      login username: " + StringUtils.trimToEmpty(devUserName) + newLine
                    + "====================================" + newLine;
            log.warn(loginWithDevMode);

            DevUser.userName = StringUtils.trimToEmpty(devUserName);
            DevUser.loginAsDevUser =  true;
        }
    }

}
