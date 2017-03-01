package com.github.devcat24.mvc.svc;

import com.github.devcat24.mvc.dao.mm.MemberDAO;
import com.github.devcat24.mvc.dto.mm.MemberDTO;
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

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

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

    //@Autowired
    @PersistenceContext(unitName = "fooPersistence")
    private EntityManager entityManager;

    @Autowired
    private MemberDAO memberDAO;


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

        Member member02 = Member.builder().id(10002L).name("JaneSmith").city("Hamilton").street("Rototuna").zipcode("21203").orders(null).build();
        member02 = memberRepo.saveAndFlush(member02);


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
    public void jpaRepositorySampleInvoke()  {

        log.info("------- JPA Repository invocation --------");
        // 1. Simple JPA Repository invoke
        Member m01 = memberRepo.findById(1L);
        log.info(" 1. Simple JPA: " +m01.getName() + "(" + m01.getId() + ")");

        // 2. JPA invoke using JPQL (Param type #1)
        List <Member> mList02 = memberRepo.findAllMembersFromId(0L);
        for(Member mem : mList02) { log.info(" 2. Simple JPQL-1: " + mem.getName() + "(" + mem.getId() + ")"); }

        // 3. JPA invoke using JPQL (Param type #2)
        List <Member> mList03 = memberRepo.findAllMembersFromIdParam(0L);
        for(Member mem : mList03) { log.info(" 3. Simple JPQL-2: " + mem.getName() + "(" + mem.getId() + ")"); }

        // 4. JPA invoke using Native query - return type with 'entity' class
        List <Member> mList04 = memberRepo.findAllMembersWithNativeSQL(0L);
        for(Member mem : mList04) { log.info(" 4. Native query - Simple: " + mem.getName() + "(" + mem.getId() + ")"); }

        // 5. JPQL with Projection Return type
        //  -> return object should be specified with 'AllArgsConstructor -> new XXX(...)'
        List <MemberDTO> mList05 = memberRepo.findAllMembersFromIdParamAsProjectionResult(0L);
        for(MemberDTO mem : mList05) { log.info(" 5. JPQL - Projection: " + mem.getName() + "(" + mem.getId() + ")"); }

        // 6. JPQL invoke with external XML file (orm.xml)
        List <Member> mList06 = memberRepo.findAllMembersWithSQLFile("J");
        for(Member mem : mList06) { log.info(" 6. External JPQL: " + mem.getName() + "(" + mem.getId() + ")"); }

        // 7. Native query using external XML file (orm.xml)
        //   a. If results are (collection of) entity class, projection for 'result-set-mapping' is not required.
        //      Unfortunately, most of native query requires custom result class type & does not need to be entity object
        //       -> need 'result-set-mapping' which is correspond to JPA projection
        //   b. With JPA 2.1, 'result-set-mapping' with 'constructor-result' & 'target-class' is supported
        //       -> XML schema for 'orm.xml' should be 'orm version 2.1'
        //       -> define 'result-set-mapping' & 'column' which call constructor of DTO(projection) class
        List <MemberDTO> mList07 = memberRepo.findAllMembersWithNativeSQLFile("J");
        for(MemberDTO mem : mList07) { log.info(" 7. External NativeQuery with Projection: " + mem.getName() + "(" + mem.getId() + ")"); }



        // To Test 8/9/10, stored procedure is required
        // For mysql/mariadb
        //   -- CREATE PROCEDURE get_member_after (IN search_id int)
        //   -- BEGIN select member_id as id, name, city, street, zipcode from jpa_lab_member where MEMBER_ID > search_id ;
        //   -- END
        /*
        // 8. invoke Stored Procedure using Entity Manager
        StoredProcedureQuery storedQuery
                = entityManager.createStoredProcedureQuery("get_member_after")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .setParameter(1, 0L);
        storedQuery.execute();
        //noinspection unchecked
        List <Object[]> mList08 = storedQuery.getResultList();
        for(Object[] memObj: mList08){
            log.info(" 8. Stored Procedure call as Array Object ResultSet: " + memObj[1] + "(" + memObj[0] + ")");
        }

        // 9. invoke Stored Procedure using NamedStoredProcedureQuery (only recommended for entity type results)
        // -> Basic feature works well with 'Entity' result type but,
        //    with projection type result it doesn't work smoothly like other cases.(especially with ResultSet-Mappings)
        // -> utilize 'NamedNativeQuery' type !
        //  ---------------------------------------------------------------------
        //  StoredProcedureQuery storedProcedureQuery02
        //            = entityManager.createNamedStoredProcedureQuery("Member.memberAfterWithNamedStoredProcedure")
        //                            .registerStoredProcedureParameter("search_id", Long.class, ParameterMode.IN);
        //  storedProcedureQuery02.setParameter("search_id", 0L);
        //  storedProcedureQuery02.execute();
        //  List <MemberDTO> mList09 = storedProcedureQuery02.getResultList();
        //
        //  ---------------------------------------------------------------------

        // 10. Stored Procedure call using Native query (external XML file - orm.xml)
        // -> '@NamedStoredProcedureQuery' or '@Procedure' is simple and convenient way to invoke 'stored procedure'
        //    But, if ResultSet is not 'Entity' Object type, it becomes complicated and tricky.
        //    (Although JPA 2.1 supports 'constructor-result' type mapping but, only works well with @NativeQuery.)
        //    Moreover, considering the characteristics of stored procedure, usually it is not limited single table,
        //    which means 'resultset-mapping' is required most time(Projection in JPA).
        // -> Instead of '@NamedStoredProcedureQuery', using '@NamedNativeQuery' could be good alternative for this case
        List <MemberDTO> mList10 = memberRepo.getMemberAfterStoredProcedureUsingNamedQuery(0L);
        for(MemberDTO mem : mList10) { log.info(" 10. Stored Procedure using Native Query: " + mem.getName() + "(" + mem.getId() + ")"); }
        */

        // 11. JdbcTemplate with RowMapper
        List <MemberDTO> mList11 = memberDAO.getAllMemberFromId(0L);
        for(MemberDTO mem : mList11) { log.info(" 11. JdbcTemplate with RowMapper: " + mem.getName() + "(" + mem.getId() + ")"); }
    }


}
