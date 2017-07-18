package com.github.devcat24.mockito;

import com.github.devcat24.mvc.svc.resttemplate.RestTemplateSvc;
import com.github.devcat24.util.net.RestTemplateExample;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@TestPropertySource("classpath:junit_test.properties")
@SuppressWarnings("unchecked")
@TestPropertySource(properties = {"fetch.size=5"}) // directly, overrides values in 'application.properties'
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class MockitoExampleTest {

    // Mock Injection type #1.1 - using annotation
    @Mock
    private RestTemplateSvc restTemplateSvc;

    @Autowired
    public void setRestTemplateSvc(RestTemplateSvc restTemplateSvc) {
        this.restTemplateSvc = restTemplateSvc;
    }
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testRestTemplateSvc() throws Exception {
        // Mock injection only works for direct dependencies of an object (not for nest method) !

        ResponseEntity<String> responseEntity = new ResponseEntity<>("my response body", HttpStatus.OK);

        // Mock Injection type #2.1 - using static method
        RestTemplate restTemplate = mock(RestTemplate.class);
        // List<MyObj> anyMyObj = any();  // -> any object array type
        // Long runId = anyLong();        // -> any object type
        Class strClass = any();           // -> any class type
        // any() -> do not allow 'null' / anyObject() & any(String.class) -> include 'null'
        when(restTemplate.exchange(anyString(), anyObject(), any(HttpEntity.class), strClass)).thenReturn(responseEntity);
        // -> when method has return type

        // Mock Injection type #2.1 - using static method
        String url = "https://jsonplaceholder.typicode.com/posts/1";
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        log.info("testRestTemplateMock: " + res.toString());


        // Mock Injection type #1.1 - using annotation
        when(restTemplateSvc.fetchAsStringFromRestAPI()).thenReturn(responseEntity.toString());
        String fetchedString = restTemplateSvc.fetchAsStringFromRestAPI();
        log.info("testRestTemplateSvc: " + fetchedString);


        // Mockito.doThrow(new Exception("Exception Test on RomsHarvest")).when(restTemplate).setErrorHandler(anyHandler);
        //   -> void type method -> 'Mockito.doThrow() / Mockito.doAnswer() / Mockito.doNothing() / Mockito.doReturn()' pattern

    }

}
