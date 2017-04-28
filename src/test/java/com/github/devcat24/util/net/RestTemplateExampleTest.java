package com.github.devcat24.util.net;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@TestPropertySource("classpath:junit_test.properties")
@TestPropertySource(properties = {"fetch.size=5"}) // directly, overrides values in 'application.properties'
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class RestTemplateExampleTest {
    /*@InjectMocks @Autowired
    private RestTemplateExample restTemplateExample;

    @Mock
    private RestTemplate mockRestTemplate;
    */

    /*@Autowired
    RestTemplateExample restTemplateExample;

    @InjectMocks
    RestTemplateExample mockRestTemplateExample;*/


    @Test
    public void testRestTemplate() throws Exception {
        RestTemplateExample mockRestTemplateExample = mock(RestTemplateExample.class);
        //when(mockRestTemplateExample.getJsonPostExample01()).thenReturn("ok!!!");
        when(mockRestTemplateExample.getJsonPostExample01()).thenReturn("ok 2 !!!");

        log.info("--------------------------------------------------------------");
        log.info("--------------------------------------------------------------");
        log.info("--------------------------------------------------------------");
        log.info(">>>>>>>>>>: " + mockRestTemplateExample.getJsonPostExample01());
        log.info("--------------------------------------------------------------");
        log.info("--------------------------------------------------------------");
        log.info("--------------------------------------------------------------");




        /*ResponseEntity<String> res = new ResponseEntity<String>("Mock Body", HttpStatus.OK);

        RestTemplate mockRestTemplate = mock(RestTemplate.class);
        when(mockRestTemplate.exchange(Mockito.anyString(),
                                        Mockito.<HttpMethod> any(),
                                        Mockito.<HttpEntity<?>> any(),
                                        Mockito.<Class<?>> any() //, Mockito.<String, String> anyMap()
                //any(String.class), any(HttpMethod), any(HttpEntity), any(Object)
                //url_01, Mockito.<HttpMethod> eq(HttpMethod.GET), Mockito.<HttpEntity<?>> any(), Mockito.<Class<Object>> any())
                //restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        )).thenReturn(res);*/


    }
}
