package com.github.devcat24.util.init;

import com.github.devcat24.mvc.entity.fi.Item01;
import com.github.devcat24.mvc.entity.hr.Emp01;
import com.github.devcat24.mvc.repo.fi.Item01Repo;
import com.github.devcat24.mvc.repo.hr.Emp01Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component("InitDBRes")
public class InitDBRes {
    @Autowired
    Emp01Repo emp01Repo;

    @Autowired
    Item01Repo item01Repo;

    @PostConstruct
    @Transactional(propagation = Propagation.REQUIRED)
    public void buildData(){
        Emp01 e01 = new Emp01();
        e01.setEmpno(1001);
        e01.setEname("JohnDoe");
        e01.setJob("Sales");
        e01.setMgr(1000);

        emp01Repo.saveAndFlush(e01);

        Item01 i01 = new Item01();
        i01.setItemno(2001);
        i01.setProductName("CPU");

        item01Repo.saveAndFlush(i01);
    }

}
