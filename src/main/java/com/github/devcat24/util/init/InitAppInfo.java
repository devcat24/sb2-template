package com.github.devcat24.util.init;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.devcat24.config.prop.ApplicationVersion;
import com.github.devcat24.mvc.repo.fi.Item01Repo;
import com.github.devcat24.mvc.repo.hr.Emp01Repo;
import com.github.devcat24.mvc.repo.mm.ItemRepo;
import com.github.devcat24.mvc.repo.mm.MemberRepo;
import com.github.devcat24.mvc.repo.mm.OrderItemRepo;
import com.github.devcat24.mvc.repo.mm.OrderRepo;
import com.github.devcat24.mvc.svc.JPAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
