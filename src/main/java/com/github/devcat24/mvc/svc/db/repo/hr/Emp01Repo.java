package com.github.devcat24.mvc.svc.db.repo.hr;

import com.github.devcat24.mvc.svc.db.entity.hr.Emp01;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("Emp01Repo")
public interface Emp01Repo extends JpaRepository<Emp01, Integer> {
    Emp01 findByEmpno(Integer empno);
}
