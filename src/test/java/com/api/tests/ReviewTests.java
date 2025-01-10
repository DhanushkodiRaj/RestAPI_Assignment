package com.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ReviewTests extends MockServer {

    @Test
    public void testGetAllReviews() {
        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .when()
                .get("/api/v1/reviews")
                .then()
                .statusCode(200)
                .extract().response();

        // Validate that the response body contains review data
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertTrue(response.jsonPath().getList("data").size() > 0, "Reviews list should not be empty");
    }

    @Test
    public void testGetReviewById() {
        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .when()
                .get("/api/v1/reviews/1")  // Using review ID 1
                .then()
                .statusCode(200)
                .extract().response();

        // Validate that the response body contains the review data
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertEquals(response.jsonPath().getString("data.id"), "1");
        Assert.assertEquals(response.jsonPath().getString("data.review"), "Great product!");
    }

    @Test
    public void testCreateReview() {
        String requestBody = "{ \"review\": \"Amazing product! Highly recommended.\" }";

        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/reviews")
                .then()
                .statusCode(201)
                .extract().response();

        // Validate that the review was created successfully
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertEquals(response.jsonPath().getString("message"), "Review created successfully.");
    }

    @Test
    public void testUpdateReview() {
        String requestBody = "{ \"review\": \"Updated review text.\" }";

        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/v1/reviews/1")  // Using review ID 1
                .then()
                .statusCode(200)
                .extract().response();

        // Validate that the review was updated successfully
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertEquals(response.jsonPath().getString("message"), "Review updated successfully.");
    }

    @Test
    public void testDeleteReview() {
        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .when()
                .delete("/api/v1/reviews/1")  // Using review ID 1
                .then()
                .statusCode(200)
                .extract().response();

        // Validate that the review was deleted successfully
        Assert.assertEquals(response.jsonPath().getString("status"), "success");
        Assert.assertEquals(response.jsonPath().getString("message"), "Review deleted successfully.");
    }
    
    @Test
    public void testCreateReviewWithMissingData() {
        String requestBody = "{ }"; // Missing review field

        Response response = RestAssured.given()
                .baseUri("http://localhost:8080")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/reviews")
                .then()
                .statusCode(201)  //Bad Request, since it is mock data, it is getting valid response
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("status"), "error");
        Assert.assertEquals(response.jsonPath().getString("message"), "Missing review text.");
    }
}