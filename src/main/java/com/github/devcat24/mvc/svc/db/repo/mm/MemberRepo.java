package com.github.devcat24.mvc.svc.db.repo.mm;

import com.github.devcat24.mvc.svc.db.dto.mm.MemberDTO;
import com.github.devcat24.mvc.svc.db.entity.mm.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("MemberRepo")
public interface MemberRepo extends JpaRepository<Member, Long> {
    List<Member> findAll();

    // 1. Simple JPA Repository invoke
    Member findById(Long id);

    // 2. JPA invoke using JPQL (Param type #1)
    @Query("SELECT m FROM com.github.devcat24.mvc.svc.db.entity.mm.Member m WHERE m.id > ?1")
    List<Member> findAllMembersFromId(Long startingID);

    // 3. JPA invoke using JPQL (Param type #2)
    // passing parameter with parameter name using '@Param("***")'
    @Query("SELECT m FROM com.github.devcat24.mvc.svc.db.entity.mm.Member m WHERE m.id > :queryID")
    List<Member> findAllMembersFromIdParam(@Param("queryID") Long startingID);

    // 4. JPA invoke using Native query - return type with 'entity' class
    @Query(value="SELECT * FROM jpa_lab_member m WHERE m.MEMBER_ID > :queryID", nativeQuery=true)
    List<Member> findAllMembersWithNativeSQL(@Param("queryID") Long startingID);

    // 5. JPQL with Projection Return type
    //  -> return object should be specified with 'AllArgsConstructor -> new XXX(...)'
    @Query("SELECT                                                                                                                                   " +
            "    new com.github.devcat24.mvc.svc.db.dto.mm.MemberDTO (m.id as id, m.name as name, m.city as city, m.street as street, m.zipcode as zipcode) " +
            " FROM com.github.devcat24.mvc.svc.db.entity.mm.Member m WHERE m.id > :queryID                                                                     ")
    List<MemberDTO> findAllMembersFromIdParamAsProjectionResult(@Param("queryID") Long startingID);

    // 6. JPQL invoke with external XML file (orm.xml)
    @Query
    List<Member> findAllMembersWithSQLFile(@Param("nameString") String nameString);

    // 7. Native query using external XML file (orm.xml)
    //   a. If results are (collection of) entity class, projection for 'result-set-mapping' is not required.
    //      Unfortunately, most of native query requires custom result class type & does not need to be entity object
    //       -> need 'result-set-mapping' which is correspond to JPA projection
    //   b. With JPA 2.1, 'result-set-mapping' with 'constructor-result' & 'target-class' is supported
    //       -> XML schema for 'orm.xml' should be 'orm version 2.1'
    //       -> define 'result-set-mapping' & 'column' which call constructor of DTO(projection) class
    @Query(nativeQuery=true)
    List<MemberDTO> findAllMembersWithNativeSQLFile(@Param("nameString") String nameString);

    // @Procedure (procedureName="get_member_after")
    // List<MemberDTO> getMemberAfterWithStoredProcedure(@Param("startingID") Long startingID);


    // 10. StoredProcedure using NativeQuery
    // -> '@NamedStoredProcedureQuery' or '@Procedure' is simple and convenient way to invoke 'stored procedure'
    //    But, if ResultSet is not 'Entity' Object type, it becomes complicated and tricky.
    //    (Although JPA 2.1 supports 'constructor-result' type mapping but, only works well with @NativeQuery.)
    //    Moreover, considering the characteristics of stored procedure, usually it is not limited single table,
    //    which means 'resultset-mapping' is required most time(Projection in JPA).
    // -> Instead of '@NamedStoredProcedureQuery', using '@NamedNativeQuery' could be good alternative for this case
    @Query(nativeQuery=true)
    List<MemberDTO> getMemberAfterStoredProcedureUsingNamedQuery(@Param("startingID") Long startingID);

}
