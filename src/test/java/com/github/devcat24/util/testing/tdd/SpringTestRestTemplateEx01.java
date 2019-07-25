package com.github.devcat24.util.testing.tdd;

import com.github.devcat24.Sb2TemplateApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
//Testing with running server environment
@SpringBootTest(classes = Sb2TemplateApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringTestRestTemplateEx01 {

    @Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Value(value = "${server.servlet.context-path}")
	String contextPath;

	private String getRootUrl(){
		return "http://localhost:" + port + contextPath;
	}

    @Before
    public void setUp(){
    }
    @Test
	public void testGetAllEmp(){
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/v1/emp", HttpMethod.GET, entity, String.class);
		assertNotNull(response.getBody());
	}
}
