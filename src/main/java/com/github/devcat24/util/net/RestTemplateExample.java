package com.github.devcat24.util.net;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("RestTemplateExample")
public class RestTemplateExample {

/*@Autowired
RestTemplate restTemplate;*/

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    public String getJsonPostExample01 () throws Exception {
        String url = "https://jsonplaceholder.typicode.com/posts";

        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.getInterceptors().add(getBasicAuthorizationInterceptor());

        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, null, String.class);


        System.out.println("<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>");
        System.out.println(res.getBody().toString());
        System.out.println("<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>");

        mockInvokeTest();
        return  res.getBody().toString();
    }


    public String mockInvokeTest() throws Exception{
        System.out.println("########################################");
        System.out.println("########################################");
        System.out.println("        mockInvokeTest                  ");
        System.out.println("########################################");

        return "skipping?  ";
    }

    /*private BasicAuthorizationInterceptor getBasicAuthorizationInterceptor(){
        return new BasicAuthorizationInterceptor(username, password);
    }*/
}
