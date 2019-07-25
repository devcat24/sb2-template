package com.github.devcat24.util.testing.tdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.devcat24.Sb2TemplateApplication;
import com.github.devcat24.mvc.controller.HREmpController;
import com.github.devcat24.mvc.db.entity.hr.Emp01;
import com.github.devcat24.mvc.svc.HREmpSvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
// @WebMvcTest -> only test for controller without dependency of service layer
@WebMvcTest(controllers = Sb2TemplateApplication.class)
//@Import(SpringSecurityConfig.class)   // include security config to WebMvcTest
@AutoConfigureMockMvc(secure=false)
public class SpringMockMvcTest01 {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    HREmpSvc hrEmpSvc;

    @InjectMocks
    HREmpController hrEmpController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(hrEmpController).build();
    }


    @Test
    public void mockGetAllEmpTest() throws Exception {
        List<Emp01> empList = new ArrayList<>();
        empList.add(Emp01.builder().empno(100).ename("MockUser").job("MGR").mgr(100).build());

        // Type #1: Mocking 'controller' directly
        //    Mockito.when(hrEmpController.getAllEmp()).thenReturn(empList);

        // Type #2: In order to mock service layer -> need to 'inject' the 'mocked object(service layer)' to controller layer
        //   a. define 'service layer' with '@Mock'
        //   b. define 'controller' with '@InjectMocks'
        //   c. add 'MockitoAnnotations.initMocks(this);' & 'this.mockMvc = MockMvcBuilders.standaloneSetup(hrEmpController).build();' to 'setUp()'
        //   d. define mock condition using 'Mockito.when(...)'
        Mockito.when(hrEmpSvc.getAllEmp()).thenReturn(empList);


        mockMvc.perform(get("/api/v1/emp")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("[{'empno':100,'ename':'MockUser','job':'MGR','mgr':100}]"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ename").value("MockUser"))
                .andExpect(content().string(containsString("mgr")))
                .andDo(print());

        MvcResult result = mockMvc.perform(get("/api/v1/emp"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        String rtnContents = result.getResponse().getContentAsString();
        assertEquals(rtnContents, "[{\"empno\":100,\"ename\":\"MockUser\",\"job\":\"MGR\",\"mgr\":100}]");
    }

    @Test
    public void createEmpTest() throws Exception {
        Emp01 emp01 = Emp01.builder().empno(100).ename("MockUser").job("MGR").mgr(100).build();

        // Also need to mock for backend(service) layer
        Mockito.when(hrEmpSvc.save(emp01)).thenReturn(emp01);

        mockMvc.perform(post("/api/v1/emp")
                .contentType(MediaType.APPLICATION_JSON)
                .content( new ObjectMapper().writeValueAsString(emp01)))
                //.andExpect(status().isCreated());
                .andExpect(status().isOk())
                .andDo(print());
    }

}
