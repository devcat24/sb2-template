package com.github.devcat24.mvc.db.repo.hr;

import com.github.devcat24.mvc.db.entity.hr.Emp01;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


// 1. Sample data creation
//   $ curl -i -X POST -H "Content-Type:application/json" -d '{"empno": 12333,"ename": "BUTTON","job": "Admin","mgr": 123}' http://localhost:8080/restemp
// 2. Query user
//   $ curl -X GET http://localhost:8080/restemp/12333

//@PreAuthorize("hasRole('USER')")      // integration with Spring Security
@SuppressWarnings("unused")

// Temporary Disabled to prevent the conflict between 'Swagger 2.9.2' and '@RepositoryRestResource annotation'
//@RepositoryRestResource(collectionResourceRel = "restemp", path = "restemp")
public interface Emp01RestRepo  extends JpaRepository<Emp01, Integer> {
//public interface Emp01RestRepo  extends PagingAndSortingRepository<Emp01, Integer> {

    //@PreAuthorize("hasRole('ADMIN')") // integration with Spring Security
    List<Emp01> findByEmpno(@Param("id") Integer empno);
}
