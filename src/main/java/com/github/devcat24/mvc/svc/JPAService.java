package com.github.devcat24.mvc.svc;

import com.github.devcat24.mvc.entity.fi.Item01;
import com.github.devcat24.mvc.entity.hr.Emp01;
import com.github.devcat24.mvc.entity.mm.Member;
import com.github.devcat24.mvc.repo.fi.Item01Repo;
import com.github.devcat24.mvc.repo.hr.Emp01Repo;
import com.github.devcat24.mvc.repo.mm.ItemRepo;
import com.github.devcat24.mvc.repo.mm.MemberRepo;
import com.github.devcat24.mvc.repo.mm.OrderItemRepo;
import com.github.devcat24.mvc.repo.mm.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j  // lombok annotation for logging (@Slf4j/@Log4j/@Log4j2/@CommonsLog/@Log) -> invoke simply with 'log.info("Sample message");'
@Service("PAService")
public class JPAService {
    private static final Logger logger = LoggerFactory.getLogger(JPAService.class);

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

    @Transactional(value="fooTransactionManager", propagation = Propagation.REQUIRED)
    public void loadInitialData()  {
        log.info("------------- in JPAService.loadInitialData() ----------------");
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


        Member member01 = new Member(10001L, "JohnDoe", "Hamilton", "Hillcrest", "21230", null);
        // <- Id may be re-written by the policy '@GeneratedValue(strategy = GenerationType.IDENTITY)'
        member01 = memberRepo.saveAndFlush(member01);


//        Order order01 = new Order(10001L, new Date(), OrderStatus.ORDER, member01, null);
//        // <- Id may be re-written by the policy '@GeneratedValue(strategy = GenerationType.IDENTITY)'
//        order01 = orderRepo.saveAndFlush(order01);
//
//        Item item01 = new Item(20001L, "Mouse", 45, 200);
//        item01 = itemRepo.saveAndFlush(item01);
//
//
//        OrderItem orderItem01 = new OrderItem(10001L, item01, order01, 41, 10);
//        orderItem01 = orderItemRepo.saveAndFlush(orderItem01);


/*        ObjectMapper mapper = new ObjectMapper();
        //mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
//        logger.info(":::::::::::::::::::::::::>" + mapper.writeValueAsString(order01));
//        logger.info(":::::::::::::::::::::::::>" + mapper.writeValueAsString(order01));
//        logger.info(":::::::::::::::::::::::::>" + mapper.writeValueAsString(order01));
//        logger.info(":::::::::::::::::::::::::>" + mapper.writeValueAsString(order01));

        Gson gson = (new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy")).create();
        logger.info(":::::::::::::::::::::::::>" + gson.toJson(order01));
        logger.info(":::::::::::::::::::::::::>" + gson.toJson(order01));
        logger.info(":::::::::::::::::::::::::>" + gson.toJson(order01));*/

//        Order order02 = orderRepo.findById(10001L);
//        logger.info("::::::::::::::::::::::::: " + order02.getId() + ", " + order02.getStatus());
//        Member member02 = order02.getMember();
//        logger.info("::::::::::::::::::::::::: " + order02.getMember() + " ] "+ order02.getOrderItems().get(0));
//        logger.info("::::::::::::::::::::::::: " + order02.getMember() + " ] "+ order02.getOrderItems().get(0));
//        logger.info("::::::::::::::::::::::::: " + order02.getMember() + " ] "+ order02.getOrderItems().get(0));
//        logger.info("::::::::::::::::::::::::: " + order02.getMember() + " ] "+ order02.getOrderItems().get(0));

    }

    @Transactional(value="fooTransactionManager", propagation = Propagation.REQUIRED)
    public void searchInitialData()  {
//        Order order02 = orderRepo.findById(1L);
//        Member member02 = order02.getMember();
//        logger.info("::::::::::::::::::::::::: " + member02.getName() + " ] "+ order02.getOrderItems().get(0));
//        logger.info("::::::::::::::::::::::::: " + member02.getName() + " ] "+ order02.getOrderItems().get(0));
//        logger.info("::::::::::::::::::::::::: " + member02.getName() + " ] "+ order02.getOrderItems().get(0));
//        logger.info("::::::::::::::::::::::::: " + member02.getName() + " ] "+ order02.getOrderItems().get(0));
//        logger.info("::::::::::::::::::::::::: " + member02.getName() + " ] "+ order02.getOrderItems().get(0));

    }


}
