package com.github.devcat24.util.testing.tdd;
// - https://medium.com/aeturnuminc/getting-started-with-rest-assured-a087d806b4ec
// - https://devqa.io/rest-assured-api-requests-examples/

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

public class RestAssuredMockTest01 {

    @BeforeAll
    public static void setup() {
       // RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void getRequest() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addCookie("my_cookie", "test_Cookies_settings");
        requestSpecBuilder.setContentType("application/json");
        requestSpecification = requestSpecBuilder.build();

        RestAssured.given().when().get("http://www.google.com").then().statusCode(200);

//        Response response = given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/posts")
//                .then()
//                .extract().response();
//
//        Assertions.assertEquals(200, response.statusCode());
//        Assertions.assertEquals("qui est esse", response.jsonPath().getString("title[1]"));
    }
}
