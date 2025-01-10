package com.api.tests;
import org.testng.annotations.BeforeSuite;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import org.testng.annotations.AfterSuite;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockServer {

    private WireMockServer wireMockServer;

    @BeforeSuite
    public void setup() {
        wireMockServer = new WireMockServer(8080); // Starts server on port 8080
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);

        // Authentication Endpoints
        stubFor(post(urlEqualTo("/api/v1/auth/register"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"status\": \"success\", \"message\": \"User registered successfully.\" }")));

        stubFor(post(urlEqualTo("/api/v1/auth/login"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"status\": \"success\", \"token\": \"mock-jwt-token-12345\" }")));

        stubFor(get(urlEqualTo("/api/v1/auth/logout"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"status\": \"success\", \"message\": \"Logged out successfully.\" }")));

        // Product Endpoints
        stubFor(get(urlEqualTo("/api/v1/products"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"status\": \"success\", \"data\": [{\"id\": \"1\", \"name\": \"Product 1\"}] }")));

        stubFor(post(urlEqualTo("/api/v1/products"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"status\": \"success\", \"message\": \"Product created successfully.\" }")));

        stubFor(delete(urlMatching("/api/v1/products/\\d+"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"status\": \"success\", \"message\": \"Product deleted successfully.\" }")));

        // Reviews Endpoints
        stubFor(get(urlEqualTo("/api/v1/reviews"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"status\": \"success\", \"data\": [{\"id\": \"1\", \"review\": \"Great product!\"}] }")));

        stubFor(post(urlEqualTo("/api/v1/reviews"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"status\": \"success\", \"message\": \"Review created successfully.\" }")));

        // Orders Endpoints
        stubFor(get(urlEqualTo("/api/v1/orders"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"status\": \"success\", \"data\": [{\"id\": \"1\", \"order\": \"Order 1\"}] }")));

        stubFor(post(urlEqualTo("/api/v1/orders"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"status\": \"success\", \"message\": \"Order created successfully.\" }")));
    }

    @AfterSuite
    public void teardown() {
        wireMockServer.stop(); // Stop WireMock server after tests
    }
}
