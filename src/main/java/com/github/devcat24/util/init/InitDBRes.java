package com.github.devcat24.util.init;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.devcat24.mvc.svc.db.repo.fi.Item01Repo;
import com.github.devcat24.mvc.svc.db.repo.hr.Emp01Repo;
import com.github.devcat24.mvc.svc.db.repo.mm.ItemRepo;
import com.github.devcat24.mvc.svc.db.repo.mm.MemberRepo;
import com.github.devcat24.mvc.svc.db.repo.mm.OrderItemRepo;
import com.github.devcat24.mvc.svc.db.repo.mm.OrderRepo;
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

    @PostConstruct
    public void buildData() throws JsonProcessingException {
        jpaService.loadInitialData();
        //jpaService.jpaRepositorySampleInvoke();



    }

}
