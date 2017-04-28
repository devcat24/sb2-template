package com.github.devcat24.util.init;

import com.github.devcat24.config.prop.ApplicationVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component("InitAppInfo")
public class InitAppInfo {

    @PostConstruct
    public void loadAppVersion() throws Exception {
        log.info("Loading application version info");
        ApplicationVersion.applicationVersion = new AppVersionLoader().getApplicationTitleNVersion();
    }
}
