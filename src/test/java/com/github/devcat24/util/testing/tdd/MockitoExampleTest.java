package com.github.devcat24.util.testing.tdd;

import com.github.devcat24.mvc.svc.RestTemplateSvc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

//import java.net.URI;
//import java.util.Map;

// static method 'org.mockito.Matchers.*' is deprecated, use 'org.mockito.ArgumentMatchers.*'
//import static org.mockito.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    @SuppressWarnings("unused")
    @Mock
    private RestTemplateSvc restTemplateSvc;

    @Autowired
    public void setRestTemplateSvc(RestTemplateSvc restTemplateSvc) {
        this.restTemplateSvc = restTemplateSvc;
    }
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testRestTemplateSvc() {
        // Mock injection only works for direct dependencies of an object (not for nest method) !

        ResponseEntity<String> responseEntity = new ResponseEntity<>("my response body", HttpStatus.OK);

        // Mock Injection type #2.1 - using static method
        RestTemplate restTemplate = mock(RestTemplate.class);
        // List<MyObj> anyMyObj = any();  // -> any object array type
        // Long runId = anyLong();        // -> any object type
        Class strClass = any();           // -> any class type
        // any() -> do not allow 'null' / anyObject() & any(String.class) -> include 'null'
        // anyObject() is deprecated in Mockito 3.0  !
        //when(restTemplate.exchange(anyString(), anyObject(), any(HttpEntity.class), strClass)).thenReturn(responseEntity);
        when(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), strClass)).thenReturn(responseEntity);
        // -> when method has return type

        // Mock Injection type #2.1 - using static method
        String url = "http://jsonplaceholder.typicode.com/posts/1";


        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Content-Type", "Application/Json");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", "spring");
        map.add("password", "boot");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, requestHeaders);

        restTemplate = new RestTemplate();
        // -> need to assign proper 'RestTemplate' instance (restTemplate from above as 'Mocked RestTemplate')
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        log.info("RestTemplate > RequestEntity : " + requestEntity.toString());
        log.info("RestTemplate > Response (" + res.getStatusCode() + ") : " + res.getBody());

        // Mockito.doThrow(new Exception("Exception Test on RomsHarvest")).when(restTemplate).setErrorHandler(anyHandler);
        //   -> void type method -> 'Mockito.doThrow() / Mockito.doAnswer() / Mockito.doNothing() / Mockito.doReturn()' pattern

    }

}
