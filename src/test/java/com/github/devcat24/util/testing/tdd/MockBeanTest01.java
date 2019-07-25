package com.github.devcat24.util.testing.tdd;

import com.github.devcat24.mvc.db.entity.hr.Emp01;
import com.github.devcat24.mvc.svc.HREmpSvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class MockBeanTest01 {
    @Mock
    HREmpSvc hrEmpSvc;

    @Before
    public void setup(){
        List<Emp01> empList = new ArrayList<>();
        empList.add(Emp01.builder().empno(100).ename("MockUser_with_MockBean").job("MGR").mgr(100).build());

        Mockito.when(hrEmpSvc.getAllEmp()).thenReturn(empList);
    }

    @Test
    public void mockGetAllEmpTest() throws Exception {
        List<Emp01> mockList = hrEmpSvc.getAllEmp();
        mockList.stream().forEach(s -> {
            System.out.println("-> " + s.ename);
        });
    }
}
