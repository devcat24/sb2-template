package com.github.devcat24.config.init;

import com.github.devcat24.mvc.db.entity.hr.Emp01;
import com.github.devcat24.mvc.svc.HREmpSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("InitDBRes")
public class InitDBRes {

    private HREmpSvc hrEmpSvc;
    @Autowired
    void setHrEmpSvc(HREmpSvc hrEmpSvc) {
        this.hrEmpSvc = hrEmpSvc;
    }

    @PostConstruct
    public void buildData() { // throws JsonProcessingException {
        hrEmpSvc.save( Emp01.builder().empno(101).ename("Olivia").job("Manager").mgr(101).build() );
        hrEmpSvc.save( Emp01.builder().empno(102).ename("Amelia").job("Manager").mgr(101).build() );
        hrEmpSvc.save( Emp01.builder().empno(103).ename("Hazel").job("Sales").mgr(102).build() );
        hrEmpSvc.save( Emp01.builder().empno(104).ename("Lily").job("Developer").mgr(110).build() );
        hrEmpSvc.save( Emp01.builder().empno(105).ename("Sophia").job("Admin").mgr(110).build() );
        hrEmpSvc.save( Emp01.builder().empno(106).ename("Isabella").job("Sales").mgr(102).build() );
        hrEmpSvc.save( Emp01.builder().empno(107).ename("Zoe").job("Developer").mgr(110).build() );
        hrEmpSvc.save( Emp01.builder().empno(108).ename("Aria").job("Developer").mgr(110).build() );
        hrEmpSvc.save( Emp01.builder().empno(109).ename("Grace").job("Sales").mgr(102).build() );
        hrEmpSvc.save( Emp01.builder().empno(110).ename("Ava").job("Manager").mgr(101).build() );
    }
}
