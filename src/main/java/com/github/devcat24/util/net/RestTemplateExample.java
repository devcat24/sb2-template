package com.github.devcat24.util.net;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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


        // --- Basic Authentication & Post ---
        //
        // String url = "http://130.217.105.27:8161/api/jolokia";
        // RestTemplate restTemplate = new RestTemplate();
        //
        // HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_JSON);
        // headers.add("Authorization", "Basic YWRtaW46YWRtaW4=");
        //
        // String requestJson  = "{\"type\":\"read\",\"mbean\":\"org.apache.activemq:type=Broker,brokerName=*\"}";
        // HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
        //
        // ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, request, String.class );
        // String brokerInfo = res.getBody().toString();

    }


    public String mockInvokeTest() throws Exception {
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
