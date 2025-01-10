RESTful API Automation Tests with Rest Assured, TestNG, and WireMock
This project demonstrates how to automate the testing of the Orders API using Rest Assured for HTTP requests, TestNG for test execution, and WireMock to mock backend API responses.

API Endpoints Tested
The following Orders API endpoints are tested in this project:

GET /api/v1/orders - Retrieve all orders
GET /api/v1/orders/{id} - Retrieve a single order by ID
POST /api/v1/orders - Create a new order
PUT /api/v1/orders/{id} - Update an existing order by ID
DELETE /api/v1/orders/{id} - Delete an order by ID
Mock Data
The responses for the above endpoints are mocked using WireMock to simulate backend responses for the Orders API.

Project Setup
Prerequisites
Before you start, make sure you have the following installed:

Java (Version 8 or above)
Maven (for dependency management and running the tests)
IDE (Optional) - IntelliJ IDEA or Eclipse
WireMock (for mocking API responses)
Project Structure
src/main/java: Contains the implementation for WireMock server setup.
src/test/java: Contains the test cases for the Orders API automation.
pom.xml: Contains the dependencies for Rest Assured, TestNG, WireMock, and other necessary libraries.
Dependencies in pom.xml
xml
Copy code
<dependencies>
    <!-- Rest Assured for API testing -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>4.4.0</version>
        <scope>test</scope>
    </dependency>

    <!-- TestNG for running tests -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.3.0</version>
        <scope>test</scope>
    </dependency>

    <!-- WireMock for mocking API responses -->
    <dependency>
        <groupId>com.github.tomakehurst</groupId>
        <artifactId>wiremock-jre8</artifactId>
        <version>2.31.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
Running the Tests
Clone the Repository:

If you haven't cloned the repository yet, do so by running:

bash
Copy code
git clone https://github.com/your-username/order-api-automation.git
cd order-api-automation
Start the WireMock Mock Server:

To simulate the API responses, the MockOrderServer class should be run first. This will start the WireMock server on localhost:8080 to serve mocked responses for the Orders API.

Open MockOrderServer.java and run it as a Java application.
Run the Tests:

You can run the tests using Maven. The TestNG suite is configured to run all test classes.

To run the tests:

bash
Copy code
mvn test
Alternatively, you can run the tests directly from your IDE.

TestNG Configuration:

Test execution is managed by TestNG. The configuration is located in the testng.xml file.

Example of running tests with TestNG:

bash
Copy code
mvn clean test -DsuiteXmlFile=testng.xml
Or run it directly through the IDE by running the testng.xml suite.

Project Files
MockOrderServer.java: WireMock mock server to simulate API responses.
OrderTest.java: Contains the automated tests for the Orders API endpoints (GET, POST, PUT, DELETE).
testng.xml: TestNG configuration file to execute the tests.
Test Cases
Test GET /api/v1/orders: Retrieve all orders.
Test GET /api/v1/orders/{id}: Retrieve an order by ID.
Test POST /api/v1/orders: Create a new order.
Test PUT /api/v1/orders/{id}: Update an existing order by ID.
Test DELETE /api/v1/orders/{id}: Delete an order by ID.
Mocked Responses for Orders API:
GET /api/v1/orders: Returns a list of orders.
GET /api/v1/orders/{id}: Returns a specific order by ID.
POST /api/v1/orders: Returns a success message when a new order is created.
PUT /api/v1/orders/{id}: Returns a success message when an order is updated.
DELETE /api/v1/orders/{id}: Returns a success message when an order is deleted.
Edge Case Handling
The following edge cases are handled in the tests:

GET /api/v1/orders: Validates the case where no orders exist.
POST /api/v1/orders: Validates missing or invalid order data.
DELETE /api/v1/orders/{id}: Validates the case where the order does not exist.
Results and Logs
Test results will be shown in the terminal or your IDE console. If any tests fail, you will see details about the failed assertions, including error messages and stack traces.

Contribution Guidelines
If you would like to contribute to this project:

Fork the repository.
Clone your fork.
Create a new branch for your feature.
Commit your changes.
Push your changes.
Open a pull request.
License
This project is licensed under the MIT License - see the LICENSE file for details.
