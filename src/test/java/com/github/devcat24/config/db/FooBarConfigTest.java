package com.github.devcat24.config.db;

//import com.github.devcat24.mvc.svc.db.entity.fi.Item01;
//import com.github.devcat24.mvc.svc.db.entity.hr.Emp01;
//import com.github.devcat24.mvc.svc.db.repo.fi.Item01Repo;
//import com.github.devcat24.mvc.svc.db.repo.hr.Emp01Repo;
import com.github.devcat24.mvc.db.entity.fi.Item01;
import com.github.devcat24.mvc.db.entity.hr.Emp01;
import com.github.devcat24.mvc.db.repo.fi.Item01Repo;
import com.github.devcat24.mvc.db.repo.hr.Emp01Repo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// 1. Test for datasource & entity-manager configuration
// 2. Entity design & Repository interface related tests could be done by '@DataJpaTest'
//    which does not load 'ApplicationContext' (instead uses in memory database)
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class FooBarConfigTest {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FooBarConfigTest.class);

    @Autowired
    private Item01Repo item01Repo;

    @Autowired
    private Emp01Repo emp01Repo;


    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void createObjects(){
        Emp01 e01= emp01Repo.findByEmpno(1002);
        Item01 i01 = item01Repo.findByItemno(2002);

        if(e01 == null && i01 == null){
            // e01 = new Emp01();
            // e01.setEmpno(1002);
            // e01.setEname("JaneSmith");
            // e01.setJob("Sales");
            // e01.setMgr(2000);
            e01 = Emp01.builder().empno(1002).ename("JaneSmith").job("Sales").mgr(2000).build();
            emp01Repo.saveAndFlush(e01);

            e01 = emp01Repo.findByEmpno(1002);
            assertThat(e01.getEname()).isEqualTo("JaneSmith");

            i01 = new Item01();
            i01.setItemno(2002);
            i01.setProductName("RAM");
            item01Repo.saveAndFlush(i01);

            emp01Repo.delete(e01);
            item01Repo.delete(i01);
        }   else {
            logger.info("Testing with already existing rows");
            assertTrue(false);  // -> can be simplified with ' fail();'
            // Or create another objects for testing
        }
    }

}
