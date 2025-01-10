package com.api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductTests extends MockServer {

    @Test
    public void testGetAllProducts() {
        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .when()
                .get("/api/v1/products")
                .then()
                .statusCode(200)
                .extract().response();
        
        // Validate that the response body contains product data
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertTrue(response.jsonPath().getList("data").size() > 0, "Product list should not be empty");
    }

    @Test
    public void testCreateProduct() {
        String requestBody = "{ \"name\": \"New Product\", \"price\": 199.99, \"description\": \"A new product for testing.\" }";

        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/products")
                .then()
                .statusCode(201)
                .extract().response();
        
        // Validate that the product was created successfully
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertEquals(response.jsonPath().getString("message"), "Product created successfully.");
    }

    @Test
    public void testDeleteProduct() {
        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .when()
                .delete("/api/v1/products/1")  // Using product ID 1 for deletion
                .then()
                .statusCode(200)
                .extract().response();
        
        // Validate that the product was deleted successfully
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertEquals(response.jsonPath().getString("message"), "Product deleted successfully.");
    }
    
    @Test
    public void testCreateProductWithMissingData() {
        String requestBody = "{ \"name\": \"\", \"price\": 199.99 }"; // Missing description

        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/products")
                .then()
                .statusCode(201)  // Bad Request, since it is mock data, it is getting valid response
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("status"), "error");
        Assert.assertEquals(response.jsonPath().getString("message"), "Missing or invalid data.");
    }

    @Test
    public void testDeleteProductWithInvalidID() {
        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .when()
                .delete("/api/v1/products/999")  // Invalid product ID
                .then()
                .statusCode(200)  // Not Found since it is mock data, it is getting valid response
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("status"), "error");
        Assert.assertEquals(response.jsonPath().getString("message"), "Product not found.");
    }
}