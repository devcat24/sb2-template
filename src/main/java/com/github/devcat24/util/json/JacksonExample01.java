package com.github.devcat24.util.json;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class JacksonExample01 {
    public void jsonWithJackson() throws IOException {
        String jsonString = "{\"group1\":[{\"name\":\"kim\",\"age\":20,\"item\":{\"book\":\"math\",\"chapter\":9},\"address\":null,\"notification\":false},{\"name\":\"park\",\"age\":30}],\"status\":\"okay\"}";
        /*
            {
              "group1": [
                {
                  "name": "kim",
                  "age": 20,
                  "item": {
                    "book": "math",
                    "chapter": 9
                  },
                  "address": null,
                  "notification": false
                },
                {
                  "name": "park",
                  "age": 30
                }
              ],
              "status": "okay"
            }
         */
        ObjectMapper objMapper = new ObjectMapper();
        JsonNode jsonObj = objMapper.readTree(jsonString);
        System.out.println("is group1 Array?: " + jsonObj.get("group1").isArray());
        System.out.println("is group1 Object?: " + jsonObj.get("group1").isObject());
        System.out.println("is group1 Null?: " + jsonObj.get("group1").isNull());

        System.out.println("is status Array?: " + jsonObj.get("status").isArray());
        System.out.println("is status Object?: " + jsonObj.get("status").isObject());
        System.out.println("is status Null?: " + jsonObj.get("status").isNull());


        JsonNode group1Node = jsonObj.get("group1");
        JsonNode ageObj = null;
        if( group1Node != null && group1Node.isArray() && group1Node.size()>0) {
            System.out.println("JsonArray size: " + group1Node.size());
            System.out.println( group1Node.get(0).get("name") + " from JsonNode : " + group1Node.get(0));
            ageObj = group1Node.get(0).get("age");

            System.out.println("-----------------------");
            // Java8 Consumer interface
            Consumer<JsonNode> group1NodeConsumer = (JsonNode node) -> System.out.println("Consumer: " + node.toString());
            group1Node.forEach(group1NodeConsumer);
            System.out.println("-----------------------");


            System.out.println(" using getJsonNodeWithType : "
                    + getJsonNodeWithType(group1Node.get(0), "age").getKey() + " - "
                    + getJsonNodeWithType(group1Node.get(0), "age").getValue().asInt());



            // --> prevent NullPointerException while traversing Json Object when null -> returns null instead of exception
            JsonNode finalJsonValue = null;
            try {
                finalJsonValue = jsonObj.get("group1").get(0).get("item").get("book");
            } catch(NullPointerException ne){ }
            // --> prevent NullPointerException while traversing Json Object when null -> returns null instead of exception




        }
        assert ageObj != null;
        System.out.println("is age Integer?: " + ageObj.isInt());
        System.out.println("is age Long?: " + ageObj.isLong());
        System.out.println("is age Float?: " + ageObj.isFloat());
        System.out.println("is age Double?: " + ageObj.isDouble());
        System.out.println("is age Number?: " + ageObj.isNumber());
        System.out.println("age: " + ageObj.toString());



        System.out.println(jsonObj.get("group1").toString());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        System.out.println(" default value test: " + ageObj.asLong(1000L));
        // default value of 'asInt/asLong' only works for casting exception, not for null -> makes NullPointerException



        System.out.println("path to item: " + jsonObj.findPath("item").toString());
        System.out.println("path to item: " + jsonObj.findPath("name").toString());  // doesn't work properly in duplicated key


        // ---> !!!!!    http://www.baeldung.com/jackson-json-to-jsonnode
        //   -->  String -> null check -> convert JsonNode type -> check null -> jsonObject.get () --> check type for result -> type cast & get !!! repeat !!!
        //


        // Json Stream API
        // a. creating Json Object with Stream
        //    > target json object
        //      {
        //          "name":"Tom",
        //          "age":25,
        //          "address":[
        //            "Poland",
        //            "5th avenue"
        //          ]
        //      }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);

        jGenerator.writeStartObject();
        jGenerator.writeStringField("name", "Tom");
        jGenerator.writeNumberField("age", 25);
        jGenerator.writeFieldName("address");
        jGenerator.writeStartArray();
        jGenerator.writeString("Poland");
        jGenerator.writeString("5th avenue");
        jGenerator.writeEndArray();
        jGenerator.writeEndObject();
        jGenerator.close();
        @SuppressWarnings("CharsetObjectCanBeUsed")
        String generatedString = new String(stream.toByteArray(), "UTF-8");
        System.out.println("jGenerator Result: "+generatedString);


    }

    @SuppressWarnings("unused")
    private JsonNode getJsonNode(JsonNode jsonNode, String key){
        JsonNode rtn = null;
        if( (jsonNode != null) && (jsonNode.get(key) != null) && (!jsonNode.get(key).isNull()) ){
            rtn = jsonNode.get(key);
        }
        return rtn;
    }

    @SuppressWarnings("SameParameterValue")
    private Pair<String, JsonNode> getJsonNodeWithType(JsonNode jsonNode, String key){
        JsonNode rtnObj = null;
        String jsonType = "null";
        if( (jsonNode != null) && (jsonNode.get(key) != null) && (!jsonNode.get(key).isNull()) ){
            rtnObj = jsonNode.get(key);

            if (rtnObj.isBoolean()){
                jsonType = "boolean";
            } else if (rtnObj.isArray()){
                jsonType = "array";

            } else if (rtnObj.isInt()){
                jsonType = "integer";

            } else if (rtnObj.isLong()) {
                jsonType = "long";

            } else if (rtnObj.isFloat()) {
                jsonType = "float";

            } else if (rtnObj.isDouble()){
                jsonType = "double";

            } else if (rtnObj.isBigInteger()){
                jsonType = "bigInteger";

            } else if (rtnObj.isBigDecimal()){
                jsonType = "bigDecimal";

            } else if (rtnObj.isNumber()){
                jsonType = "number";

            } else {
                jsonType = "string";

            }
            // else if (rtnObj.isTextual()  --> Base64 encoded string can be 'false' by conditions
            //        > https://fasterxml.github.io/jackson-databind/javadoc/2.2.0/com/fasterxml/jackson/databind/JsonNode.html
            // else if (rtnObj.isNull()){}  --> checked by upper condition
        }
        @SuppressWarnings("UnnecessaryLocalVariable")
        Pair<String, JsonNode> rtnPair = new Pair<>(jsonType, rtnObj);
        return rtnPair;
    }

}
