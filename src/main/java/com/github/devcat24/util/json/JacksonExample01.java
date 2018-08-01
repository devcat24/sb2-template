package com.github.devcat24.util.json;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.Consumer;

@SuppressWarnings({"unused", "WeakerAccess"})
public class JacksonExample01 {
    public void jsonWithJackson() throws IOException {
        String jsonString = "{\"group1\":[{\"name\":\"kim\",\"age\":20,\"item\":{\"book\":\"math\",\"chapter\":[9, 10]},\"address\":null,\"notification\":false},{\"name\":\"park\",\"age\":30}],\"status\":\"okay\"}";
        /*
            {
              "group1": [
                {
                  "name": "kim",
                  "age": 20,
                  "item": {
                    "book": "math",
                    "chapter": [9, 10]
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


        // 1. retrieve json value - basic
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

            // Java8 Consumer interface
            Consumer<JsonNode> group1NodeConsumer = (JsonNode node) -> System.out.println("Consumer: " + node.toString());
            group1Node.forEach(group1NodeConsumer);


            System.out.println(" using getJsonNodeWithType : "
                    + getJsonNodeWithType(group1Node.get(0), "age").getKey() + " - "
                    + getJsonNodeWithType(group1Node.get(0), "age").getValue().asInt());

            // --> prevent NullPointerException while traversing Json Object when null -> returns null instead of exception
            //     --> using 'JsonPointer' & 'isMissingNode()' method instead of following code
            //
            // JsonNode finalJsonValue = null;
            // try {
            //    finalJsonValue = jsonObj.get("group1").get(0).get("item").get("book");
            // } catch(NullPointerException ne){ }


        }
        assert ageObj != null;
        System.out.println("is age Integer?: " + ageObj.isInt());
        System.out.println("is age Long?: " + ageObj.isLong());
        System.out.println("is age Float?: " + ageObj.isFloat());
        System.out.println("is age Double?: " + ageObj.isDouble());
        System.out.println("is age Number?: " + ageObj.isNumber());
        System.out.println("age: " + ageObj.toString());

        System.out.println(jsonObj.get("group1").toString());

        System.out.println(" default value test: " + ageObj.asLong(1000L));
        // default value of 'asInt/asLong' only works for casting exception, not for null -> makes NullPointerException

        System.out.println("path to item: " + jsonObj.findPath("item").toString());
        System.out.println("path to item: " + jsonObj.findPath("name").toString());  // doesn't work properly in duplicated key


        // 2. retrieve json value using 'JsonPointer'
        //    -> do not using 'String -> null check -> convert JsonNode type -> check null -> jsonObject.get () --> check type for result -> type cast & get !!! repeat !!!'
        //       : use jackson 2.3+ with 'JsonPointer' & Java8 Optional
        //    -> Jackson-JsonPointer -> if 'key' doesn't exists -> doesn't returns null & need to check with 'isMissingNode()
        System.out.println("--- jsonPointer #1 --- " + jsonObj.at("/group1/0/name").toString());
        System.out.println("--- jsonPointer #2 --- " + jsonObj.at("/group1/0/item/chapter/1").intValue());
        System.out.println("--- jsonPointer #3 --- " + jsonObj.at("/group1/0/age").intValue());
        System.out.println("--- jsonPointer #4 --- " + jsonObj.at("/group1/0/item/book").toString());
        System.out.println("--- jsonPointer #5 isNull --- " + jsonObj.at("/group1/0/item2/book").isNull());          // <- This key doesn't exists
        //System.out.println("--- jsonPointer #5 isEmpty --- " + jsonObj.at("/group1/0/item2/book").isEmpty( ... )); // <- needs deserializer
        System.out.println("--- jsonPointer #6 isMissingNode --- " + jsonObj.at("/group1/0/item2/book").isMissingNode());



        // 3. modifying Json Data using Jackson (ObjectNode)
        //        : JsonNode -> immutable -> cannot update
        //             -> need to use ObjectNode
        //             -> need to delete & put for update target
        ObjectNode newNode01a = ((ObjectNode) jsonObj.at("/group1/0/item")).put("media", "dvd");
        System.out.println("--- update JsonObject with Jackson-Object Node - newly updated json-part #1 :  "+ newNode01a.toString());
        System.out.println("--- update JsonObject with Jackson-Object Node - original json is also added #2 :  " +jsonObj.toString());

        ((ObjectNode) jsonObj.at("/group1/0/item")).remove("chapter");
        System.out.println("--- update JsonObject with Jackson-Object Node - original json is also removed #3 :  " +jsonObj.toString());

        //JsonNode newChild01a = objMapper.readTree("{\"online\": \"streaming\"}");
        JsonNode newChild01a = objMapper.readTree("\"streaming\"");
        ((ObjectNode) jsonObj.at("/group1/0/item")).replace("media", newChild01a);
        System.out.println("--- update JsonObject with Jackson-Object Node - original json is also replace #4 :  " +jsonObj.toString());


        // 4. Json Stream API : creating Json Object with Stream
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
