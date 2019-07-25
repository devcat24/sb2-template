package com.github.devcat24;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
// @WebMvcTest -> only test for controller without dependency of service layer
@WebMvcTest(controllers = Sb2TemplateApplication.class)
//@Import(SpringSecurityConfig.class)   // include security config to WebMvcTest
@AutoConfigureMockMvc(secure=false)
public class Sb2TemplateApplicationTests {
	@Before
	public void setUp(){
	}

	@Test
	public void runAppTest(){
		assertTrue(true);
	}
}
