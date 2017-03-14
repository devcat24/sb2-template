package com.github.devcat24.mvc.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.devcat24.mvc.dto.mm.RestEmp;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Slf4j
@Controller("RESTSvcController")
public class RESTSvcController {
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    // -> http://www.baeldung.com/spring-httpmessageconverter-rest
    // -> https://www.mkyong.com/java/jaxb-hello-world-example/

    // 1. 'MappingJackson2HttpMessageConverter' for returning JSon result from java object
    //    -> mapping java object should have 'getter/setter' methods
    //    -> curl 'http://localhost:8200/template/foos/3'
    @RequestMapping(method=RequestMethod.GET, value="/rest/{id}")
    @ResponseBody
    public RestEmp getFooEmp01(@PathVariable long id){
        String names [] = {"JohnDoe", "JaneSmith", "PaulButton"};
        RestEmp emp = RestEmp.builder().id(id).name(names[(int) id % 3]).build();
        return emp;
    }

    // 2. '@RequestBody' for receiving Json type parameters
    //    -> mapping java object should have 'getter/setter' methods
    //    -> curl -i -X PUT -H "Content-Type: application/json" -d '{"id": 10001,"name":"TomJones"}' http://localhost:8200/template/foos/2
    @RequestMapping(method = RequestMethod.PUT, value="/rest/{id}")
    @ResponseBody
    public void updateFoo(@RequestBody RestEmp emp, @PathVariable long id ){
        log.info("--- received rest object ---");
        log.info(" Id: " + emp.getId() + ", Name: " + emp.getName() + "  PathId: " + id);
        log.info("----------------------------");
        // fooService.update(emp);
    }

    // 3. 'RestTemplate' & 'Jackson' for consuming Json REST Service
    //    -> mapping java object should have 'getter/setter' methods
    //   -> curl 'http://localhost:8200/template/foos/resttemplate01'
    @RequestMapping(method=RequestMethod.GET, value="/rest/resttemplate01")
    @ResponseBody
    public Object restTemplate01(){
        String url = "http://localhost:8200/template/rest/jsonHolder/emps";
        RestTemplate restTemplate = new RestTemplate();
        //return restTemplate.getForObject(url, RestEmp[].class);
        RestEmp [] rtnList = restTemplate.getForObject(url, RestEmp[].class);
        //List<RestEmp> rtnArrayList = Arrays.asList(rtnList);

        // String rtn = restTemplate.getForObject(url, String.class);
        // -> retrieve response with parsing to Object

        return rtnList;
    }

    // 4. 'RestTemplate' with custom 'Jackson Message Converter'
    //    -> mapping java object should have 'getter/setter' methods
    //    -> curl 'http://localhost:8200/template/foos/resttemplate01'
    @RequestMapping(method=RequestMethod.GET, value="/rest/resttemplate02")
    @ResponseBody
    public Object restTemplate02(){
        String url = "http://localhost:8200/template/rest/jsonHolder/emps";
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> msgConverters = new ArrayList<>();
        msgConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(msgConverters);
        //return restTemplate.getForObject(url, RestEmp[].class);
        RestEmp [] rtnList = restTemplate.getForObject(url, RestEmp[].class);
        //List<RestEmp> rtnArrayList = Arrays.asList(rtnList);
        return rtnList;
    }


    // 5. Jackson ObjectMapper samples
    @RequestMapping(value="/rest/jackson/conv/samples")
    @ResponseBody
    public String jacksonConvertSamples() throws IOException {
        String rtnString = "";

        String restEmpJson = "{\"id\":10001, \"name\":\"JohnDoe\"}";
        String restEmpListJson = " [{\"id\":10001, \"name\":\"JohnDoe\"}, {\"id\":10002, \"name\":\"JaneSmith\"} ]";

        // a. convert Json String to java object
        RestEmp emp01 = jacksonObjectMapper.readValue(restEmpJson, RestEmp.class);
        log.warn( " case a. name: " + emp01.getName());
        rtnString = rtnString + " case a. name: " + emp01.getName() +"<br />";

        // b. convert Json String to java array object / java object list(Arrays.asList(obj))
        RestEmp [] eList01 = jacksonObjectMapper.readValue(restEmpListJson, RestEmp[].class);
        for(RestEmp e : Arrays.asList(eList01)) {
            log.warn( " case b. name: " + e.getName());
            rtnString = rtnString + " case b. name: " + e.getName() + "<br />";
        }

        // c. convert Json String to java object list (using TypeReference)
        List<RestEmp> eList02 = jacksonObjectMapper.readValue(restEmpListJson, new TypeReference<ArrayList<RestEmp>>() {});
        for(RestEmp e : eList02) {
            log.warn( " case c. name: " + e.getName());
            rtnString = rtnString + " case c. name: " + e.getName() + "<br />";
        }


        // d. convert Json String to array Map object
        HashMap<String, String> hMap [] = jacksonObjectMapper.readValue(restEmpListJson, new TypeReference<HashMap<String, String>[]>(){});
        for(HashMap hmap : hMap){
            log.warn( " case d. name: " + hmap.get("name") + "(" + hmap.get("id") + ")");
            rtnString = rtnString + " case d. name: " + hmap.get("name") + "(" + hmap.get("id") + ")";
        }

        // e. complex object type - using "JsonNode" & "Tree"
        //  ex.
        //	{
        //		"msgList": {
        //			"msg1": "Welcome",
        //			"msg2": "Hello"
        //		},
        //		"newEmps": [
        //			{"id": 10001, "name": "BlackSmith"},
        //			{"id": 10002, "name": "JaneDoe"}
        //		]
        //	}
        String complexJsonType = "{\"msgList\":{\"msg1\":\"Welcome\",\"msg2\":\"Hello\"},\"newEmps\":"
                                    + "[{\"id\":10001,\"name\":\"BlackSmith\"},{\"id\":10002,\"name\":\"JaneDoe\"}]}";
        JsonNode node = jacksonObjectMapper.readTree(complexJsonType);
        log.warn(" case e.1 :" + node.get("newEmps"));
        rtnString = rtnString + " case e.1 :" + node.get("newEmps") + "<br />";
        ArrayList<RestEmp> eList03 = jacksonObjectMapper.readValue(node.get("newEmps").toString(), new TypeReference<ArrayList<RestEmp>>() {});
        for(RestEmp e : eList03) {
            log.warn(" case e.2 :" + e.getName());
            rtnString = rtnString + " case e.2 :" + e.getName() + "<br />";
        }
        ObjectMapper mapper02 = new ObjectMapper();
        HashMap<String, String> msgMapList = mapper02.readValue(node.get("msgList").toString(), new TypeReference<HashMap<String, String>>() {});
        log.warn(" case e.3 : " + msgMapList.get("msg1"));
        rtnString = rtnString + " case e.3 : " + msgMapList.get("msg1") + "<br />";
        log.warn(" case e.3 : " + msgMapList.get("msg2"));
        rtnString = rtnString + " case e.3 : " + msgMapList.get("msg2") + "<br />";
        return rtnString;
    }


    // 6. XML/HTML consuming with JSoup
    //   -> curl 'http://localhost:8200/template/rest/xml/order/2'
    @RequestMapping(method=RequestMethod.GET, value="/rest/xml/order/{orderNo}")
    @ResponseBody
    public String getXmlOrder(@PathVariable int orderNo) throws IOException {
        String rtnString = "";

        // a. HTML consumer - retrieve html elements with css selector
        Document doc01 = Jsoup.connect("http://localhost:8200/template/wel").get();
        //Document doc01 = Jsoup.connect("http://localhost:8200/template/wel").userAgent("Mozilla").get();
        Elements elements01 = doc01.select("sub");
        //Elements elements01 = doc01.select("#mp-itn b a");
        for(Element e : elements01) {
            log.info(" jsoup case a. " + e.text());
            rtnString = rtnString + " jsoup case a. " + e.text() + "<br />";
        }

        // b. HTML consumer - retrieve all image attributes
        Elements images = doc01.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        for(Element image : images) {
            log.info(" jsoup case b. " + image.attr("src") + " : " + image.attr("width") + " * " + image.attr("height"));
            rtnString = rtnString + " jsoup case b. " + image.attr("src") + " : " + image.attr("width") + " * " + image.attr("height") + "<br />";
        }

        // c. XML consumer
        //   -> For XML type, '.parser(Parser.xmlParser())' should be defined unless only 'empty' body can be returned without exception
        //Document doc02 = Jsoup.connect("http://localhost:8200/template/rest/xmlHolder/order").parser(Parser.xmlParser()).get();
        Document doc02 = Jsoup.connect("http://localhost:8200/template/rest/xmlHolder/order")
                            .data("key01", "username")
                            .data("key02", "password")
                            .userAgent("Mozilla")
                            .parser(Parser.xmlParser())
                            .timeout(3000)
                            .get();
        Elements elements02 = doc02.select("item");
        for(Element e : elements02) {
            log.info(" jsoup case c. " + e.getElementsByTag("price").text() + " <- " + e.getElementsByTag("title").text());

            rtnString = rtnString + " jsoup case c. " + e.getElementsByTag("price").text() + " <- " + e.getElementsByTag("title").text() + "<br />";
        }

        return rtnString;
    }

    @RequestMapping(value="/rest/jsonHolder/emps", produces={MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String jsonHolderEmps(){
        return " [{\"id\":10001, \"name\":\"JohnDoe\"}, {\"id\":10002, \"name\":\"JaneSmith\"} ]";

    }

    @RequestMapping(value="/rest/xmlHolder/order", produces={MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public String xmlHolderOrder(){
        return   " <?xml version=\"1.0\" encoding=\"UTF-8\"?>                 "
                + " <shiporder orderid=\"889923\"                             "
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   "
                + " xsi:noNamespaceSchemaLocation=\"shiporder.xsd\">          "
                + "   <orderperson>John Smith</orderperson>                   "
                + "   <item>                                                  "
                + "     <title>Hide your heart</title>                        "
                + "     <quantity>1</quantity>                                "
                + "     <price>9.90</price>                                   "
                + "   </item>                                                 "
                + " </shiporder>                                              ";

    }


}
