package com.api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthenticationTests extends MockServer{

    @Test
    public void testRegisterUser() {
        String requestBody = "{ \"username\": \"mockUser\", \"email\": \"mockuser@example.com\", \"password\": \"password123\" }";
        
        Response response = RestAssured.given()
                .baseUri("http://localhost:8181")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/auth/register")
                .then()
                .statusCode(201)
                .extract().response();
        
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertEquals(response.jsonPath().getString("message"), "User registered successfully.");
    }

    @Test
    public void testLoginUser() {
        String requestBody = "{ \"email\": \"mockuser@example.com\", \"password\": \"password123\" }";

        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(200)
                .extract().response();
        
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertNotNull(response.jsonPath().getString("token"));
    }

    @Test
    public void testLogoutUser() {
        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .when()
                .get("/api/v1/auth/logout")
                .then()
                .statusCode(200)
                .extract().response();
        
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertEquals(response.jsonPath().getString("message"), "Logged out successfully.");
    }
}