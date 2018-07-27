package com.github.devcat24.util.activemq;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class AMQJmxJolokiaClient {

    public void getAMQJmxJolokiaInfo(){
        String url = "http://130.217.105.27:8161/api/jolokia";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic YWRtaW46YWRtaW4=");
        // authorization key -> encoded by base64 with 'username:passowrd' value

        String requestJson  = "{\"type\":\"read\",\"mbean\":\"org.apache.activemq:type=Broker,brokerName=*\"}";
        // invoke basic broker info
        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);


        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, request, String.class );
        String brokerInfo = res.getBody().toString();

        System.out.println("==================================");
        System.out.println(brokerInfo);
        System.out.println("==================================");

        JsonObject objStep1 = new JsonParser().parse(brokerInfo).getAsJsonObject();
        System.out.println("===> " + objStep1.get("value").getAsJsonObject().get("org.apache.activemq:brokerName=localhost,type=Broker"));



    }

    // *** Joloki REST endpoint & post data
    // 0. Endpoint
    //    http://localhost:8161/api/jolokia                              -> for ActiveMQ
    //    http://isg-apachemq-d1.its.waikato.ac.nz:8181/hawtio/jolokia   -> for JBoss Hawtio
    //
    // 1. Queue & Topic List
    //    {"type":"read","mbean":"org.apache.activemq:type=Broker,brokerName=*"}
    //
    // 2. Queue statics
    //    [
    //        {
    //              "type": "read",
    //              "mbean": "org.apache.activemq:type=Broker,brokerName=*,destinationType=Queue,destinationName=P2P.Command.Maxicloud.EBS.Asset.Create"
    //          },
    //          {
    //              "type": "read",
    //              "mbean": "org.apache.activemq:type=Broker,brokerName=amq-a-master,destinationType=Queue,destinationName=P2P.Event.MyEquals.Sits.StudentDocumentStatus"
    //          }
    //      ]
    //
    // 3. Queue Detail (browse() operation) - only works for queue type
    //   [
    //      {
    //          "type":"exec",
    //          "mbean":"org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName=AMQ.TEST.QUEUE.01",
    //          "operation":"browse()"
    //      },
    //      {   "type":"exec",
    //          "mbean":"org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName=P2P.Command.Maxicloud.EBS.Asset.Create",
    //          "operation":"browse()"
    //      }
    //    ]


}
