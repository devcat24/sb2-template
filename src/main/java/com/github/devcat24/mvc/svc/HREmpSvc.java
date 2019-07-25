package com.github.devcat24.mvc.svc;

import com.github.devcat24.exception.ResourceNotFoundException;
import com.github.devcat24.mvc.controller.HREmpController;
import com.github.devcat24.mvc.db.entity.hr.Emp01;
import com.github.devcat24.mvc.db.repo.hr.Emp01Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("HREmpSvc")
public class HREmpSvc {
    private Emp01Repo emp01Repo;

    @Autowired
    void setEmp01Repo(Emp01Repo emp01Repo) {
        this.emp01Repo = emp01Repo;
    }

    public List<Emp01> getAllEmp() {
        System.out.println("-> HREmpSvc.getAllEmp() invoked" );
        return emp01Repo.findAll();
    }

    public Optional<Emp01> getEmpById(Integer empno) {
        return emp01Repo.findById(empno);
    }

    public Emp01 save(Emp01 emp01) {
        return emp01Repo.save(emp01);
    }

    public void delete(Integer empno) throws ResourceNotFoundException {
        Emp01 emp = emp01Repo.findById(empno).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this empno :: " + empno));
        emp01Repo.delete(emp);
    }


}
