package com.github.devcat24.util.init;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.devcat24.mvc.db.entity.hr.Emp01;
import com.github.devcat24.mvc.db.repo.fi.Item01Repo;
import com.github.devcat24.mvc.db.repo.hr.Emp01Repo;
import com.github.devcat24.mvc.db.repo.mm.ItemRepo;
import com.github.devcat24.mvc.db.repo.mm.MemberRepo;
import com.github.devcat24.mvc.db.repo.mm.OrderItemRepo;
import com.github.devcat24.mvc.db.repo.mm.OrderRepo;
import com.github.devcat24.mvc.svc.HREmpSvc;
import com.github.devcat24.mvc.svc.JPAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


// === Jackson api & Gson api ===
// === Jackson api & Gson api ===
//   > http://kin.naver.com/qna/detail.nhn?d1id=1&dirId=1040201&docId=266024851&qb=b2JqZWN0bWFwcGVyIGphY2tzb24=&enc=utf8&section=kin&rank=3&search_sort=0&spq=0&pid=T6ol3wpVuFRssug1maCsssssssh-409468&sid=Ynq5Fqrb87chFfrQ9Nfgww%3D%3D
//   > http://gracefulife.xyz/220515129440
//   > http://viralpatel.net/blogs/spring-4-mvc-rest-example-json/
//   > http://blog.naver.com/dev4unet/120196105458

@Slf4j
@Component("InitDBRes")
public class InitDBRes {
    //private static final org.slf4j.Logger logger = LoggerFactory.getLogger(InitDBRes.class);

    @Autowired
    Emp01Repo emp01Repo;

    @Autowired
    Item01Repo item01Repo;


    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ItemRepo itemRepo;


    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private JPAService jpaService;

    private HREmpSvc hrEmpSvc;
    @Autowired
    void setHrEmpSvc(HREmpSvc hrEmpSvc) {
        this.hrEmpSvc = hrEmpSvc;
    }

    @PostConstruct
    public void buildData() throws JsonProcessingException {
        jpaService.loadInitialData();
        //jpaService.jpaRepositorySampleInvoke();

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
