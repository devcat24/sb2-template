package com.github.devcat24.util.activemq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AMQJmxJolokiaClient {

    public void getAMQJmxJolokiaInfo() throws IOException {

        String url = "http://130.217.105.27:8161/api/jolokia";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic YWRtaW46YWRtaW4=");

        // step 1 --> list of queues & brokers
        String requestJson  = "{\"type\":\"read\",\"mbean\":\"org.apache.activemq:type=Broker,brokerName=*\"}";
        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, request, String.class );
        String brokerInfo = res.getBody();

        ObjectMapper objMapper = new ObjectMapper();
        JsonNode jsonObj = objMapper.readTree(brokerInfo);

        JsonNode queueObj = null ;
        try{
            queueObj = jsonObj.get("value").get("org.apache.activemq:brokerName=localhost,type=Broker").get("Queues");
        } catch(NullPointerException ignored) {}


        List<String> qList = new ArrayList<>();
        for(JsonNode qNode : queueObj){
            //"org.apache.activemq:brokerName=localhost,destinationName=AMQ.TEST.QUEUE.01,destinationType=Queue,type=Broker"
            qList.add(qNode.get("objectName").toString().split(":")[1].split(",")[1]);
        }

        //return objMapper.writeValueAsString(qList);


        //  step 2 --> statistics of queues
        int checkLast = 0;
        String qStatsReqString = "[";
        for(JsonNode qNode : queueObj){
            checkLast = checkLast + 1;
            qStatsReqString = qStatsReqString + " { \"" +
                    "type\": \"read\", \"mbean\": " + qNode.get("objectName").toString()   + " } " ;
            if(checkLast < queueObj.size()) {
                qStatsReqString = qStatsReqString + ",";
            }
        }
        qStatsReqString = qStatsReqString + "]";

        request = new HttpEntity<>(qStatsReqString, headers);
        res = restTemplate.exchange(url, HttpMethod.POST, request, String.class );
        String queueStats = res.getBody();

        //System.out.println(queueStats);

        jsonObj = objMapper.readTree(queueStats);

        checkLast = 0;
        String qStatResult = "[ ";
        for(JsonNode qNode : jsonObj){
            checkLast = checkLast + 1;
            // String mbeanName = qNode.get("request").get("mbean").toString();
            // String qName = mbeanName.split(":")[1].split(",")[1].split("=")[1];
            String qName = qNode.get("value").get("Name").toString();

            int enqueueCnt = qNode.get("value").get("EnqueueCount").asInt();
            int dequeueCnt = qNode.get("value").get("DequeueCount").asInt();
            int queueSize = qNode.get("value").get("QueueSize").asInt();

            qStatResult = qStatResult + "{\"Name\":"+ qName +",\"EnqueueCount\":"+ enqueueCnt +",\"DequeueCount\":"+dequeueCnt+",\"queueSize\":"+queueSize+"}";

            if(checkLast < jsonObj.size()) {
                qStatResult = qStatResult + ",";
            }
        }
        qStatResult = qStatResult + " ]";

        //return queueStats;
        //return qStatResult;

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
