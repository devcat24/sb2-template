package com.github.devcat24.util.testing.tdd;

import com.github.devcat24.mvc.db.entity.hr.Emp01;
import com.github.devcat24.mvc.svc.HREmpSvc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

// @RunWith(SpringRunner.class)         ==> Junit4
// @RunWith(MockitoJUnitRunner.class)   ==> Junit 4 with Mockito
// @ExtendWith(SpringExtension.class)   // ==> Junit5
@ExtendWith(SpringExtension.class)
// @AutoConfigureMockMvc
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JUnit5MockBeanTest01 {
    private static final Logger log = Logger.getLogger(JUnit5MockBeanTest01.class.getName());

    @BeforeAll
    static void setup() {
        log.info("@BeforeAll - executes once before all test methods in this class");
    }

    @MockBean
    private HREmpSvc hrEmpSvc;

    @Test
    public void mockGetAllEmpTest() throws Exception {
        // given
        List<Emp01> empList = new ArrayList<>();
        empList.add(Emp01.builder().empno(100).ename("MockUser_with_MockBean").job("MGR").mgr(100).build());
        given(hrEmpSvc.getAllEmp()).willReturn(empList);
        //given(hrEmpSvc.getAllEmp(any(String.class), any(String.class))).willReturn(empList);     // -> ArgumentMatchers

        // when
        List<Emp01> mockList = hrEmpSvc.getAllEmp();

        // then
        assertThat(mockList.get(0).ename).isEqualToIgnoringCase("MockUser_WiTh_MockBeAN");

        mockList.stream().forEach(s -> {
            System.out.println("-> " + s.ename);
        });
    }


}
