package com.api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrderTests extends MockServer {

    @Test
    public void testGetAllOrders() {
        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .when()
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .extract().response();

        // Validate that the response body contains order data
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertTrue(response.jsonPath().getList("data").size() > 0, "Orders list should not be empty");
    }

    @Test
    public void testGetOrderById() {
        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .when()
                .get("/api/v1/orders/1")  // Using order ID 1
                .then()
                .statusCode(200)
                .extract().response();

        // Validate that the response body contains the correct order data
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertEquals(response.jsonPath().getString("data.id"), "1");
        Assert.assertEquals(response.jsonPath().getString("data.order"), "Order 1");
    }

    @Test
    public void testCreateOrder() {
        String requestBody = "{ \"order\": \"New order with details\" }";

        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/orders")
                .then()
                .statusCode(201)
                .extract().response();

        // Validate that the order was created successfully
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertEquals(response.jsonPath().getString("message"), "Order created successfully.");
    }

    @Test
    public void testUpdateOrder() {
        String requestBody = "{ \"order\": \"Updated order details\" }";

        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/v1/orders/1")  // Using order ID 1
                .then()
                .statusCode(200)
                .extract().response();

        // Validate that the order was updated successfully
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertEquals(response.jsonPath().getString("message"), "Order updated successfully.");
    }

    @Test
    public void testDeleteOrder() {
        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .when()
                .delete("/api/v1/orders/1")  // Using order ID 1
                .then()
                .statusCode(200)
                .extract().response();

        // Validate that the order was deleted successfully
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertEquals(response.jsonPath().getString("message"), "Order deleted successfully.");
    }
    
    @Test
    public void testCreateOrderWithMissingData() {
        String requestBody = "{ }"; // Missing order field

        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/orders")
                .then()
                .statusCode(201)  // Bad Request
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertEquals(response.jsonPath().getString("message"), "Missing order details.");
    }
}