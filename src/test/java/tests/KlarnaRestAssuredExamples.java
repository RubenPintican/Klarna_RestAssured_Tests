package tests;

import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import share.data.ShareData;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.matchesPattern;

public class KlarnaRestAssuredExamples extends ShareData {


//    String jsonPath = "src/test/resources/body.json";

    public KlarnaRestAssuredExamples() throws IOException {
    }

    @Test

    /**
     * This test should pass and return status code 401
     */

    public void testRestAssuredStatusCode401() {

        given().request().when()
                .post("https://api.playground.klarna.com/payments/v1/sessions").then()
                .assertThat().statusCode(401);

    }

    @Test

    /**
     * This test require authentication
     * When send an post request, the server will respond with status code 200
     */

    public void testRestAssuredStatusCode200() {


        File jsonRequest = new File(jsonPath);
        given().auth().basic(user, password)
                .header("Content-Type", "application/json")
                .body(jsonRequest)
                .when().post("https://api.playground.klarna.com/payments/v1/sessions")
                .then().statusCode(200);

    }

    @Test

    /**
     * This test require authentication
     * When send an post request, the server will respond with status code 200
     * Body response contains "name"
     */

    public void testRestAssuredResponseBodyPositiveTest() {

        File jsonRequest = new File(jsonPath);
        Response response = given().auth().basic(user, password)
                .header("Content-Type", "application/json")
                .body(jsonRequest)
                .when().post("https://api.playground.klarna.com/payments/v1/sessions");
        Assert.assertTrue(response.getBody().print().contains("name"));
    }

    @Test

    /**
     * This test require authentication
     * When send an post request, the server will respond with status code 200
     * Body response does`t contains "coca-cola"
     */

    public void testRestAssuredResponseBodyNegativeTest() {

        File jsonRequest = new File(jsonPath);
        Response response = given().auth().basic(user, password)
                .header("Content-Type", "application/json")
                .body(jsonRequest)
                .when().post("https://api.playground.klarna.com/payments/v1/sessions");
        Assert.assertTrue(response.getBody().print().contains("coca-cola"));
    }


    @Test

    /**
     * This test require authentication
     * When send an post request, the server will respond with status code 200
     * In response body I do some validation
     */

    public void testRestAssuredValidateParameters() {

        File jsonRequest = new File(jsonPath);
        given().auth().basic(user, password)
                .header("Content-Type", "application/json")
                .body(jsonRequest)
                .when().post("https://api.playground.klarna.com/payments/v1/sessions")
                .then().statusCode(200)
                .and().body("payment_method_categories[0].name", equalTo("Pay later."))
                .and().body("payment_method_categories[0].identifier", equalTo("pay_later"))
                .and().body("payment_method_categories[0].asset_urls.descriptive", contains("https://cdn.klarna.com/1.0/shared"));

    }


    @Test

    /**
     * This test require authentication
     * When send an post request, the server will respond with status code 200
     * Println the response
     */

    public void testRestAssuredPostExtract() {

        File jsonRequest = new File(jsonPath);
        String response = given().auth().basic(user, password)
                .header("Content-Type", "application/json")
                .body(jsonRequest)
                .when().post("https://api.playground.klarna.com/payments/v1/sessions")
                .then().assertThat().statusCode(200).and().body("payment_method_categories[0].name", equalTo("Pay later."))
                .extract().path("payment_method_categories[0].name").toString();

        System.out.println(response);
    }

    @Test

    /**
     * This test require authentication
     * When send an post request, the server will respond with status code 200
     * Assert that an element in body matches with regex
     */

    public void testRestAssuredSessionId() {
        File jsonRequest = new File(jsonPath);
        given().auth().basic(user, password)
                .header("Content-Type", "application/json")
                .body(jsonRequest)
                .when().post("https://api.playground.klarna.com/payments/v1/sessions")
                .then().assertThat().statusCode(200).and().body("session_id", matchesPattern("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"));

    }

    @Test

    /**
     * This test require authentication
     * When send an post request, the server will respond with status code 200
     * Assert that an element in body matches with regex
     */

    public void testRestAssuredClientToken() {
        File jsonRequest = new File(jsonPath);
        given().auth().basic(user, password)
                .header("Content-Type", "application/json")
                .body(jsonRequest)
                .when().post("https://api.playground.klarna.com/payments/v1/sessions")
                .then().assertThat().statusCode(200).and().body("client_token", matchesPattern("([^\\?]+)(\\?([^?]*))?"));

    }

    @Test

    /**
     * This test require authentication
     * When send an post request, the server will respond with status code 200
     * Assert that an element in body do not matches with an wrong regex
     */

    public void testRestAssuredLogAllResponsesNegativeTest() {
        File jsonRequest = new File(jsonPath);
        given().auth().basic(user, password)
                .header("Content-Type", "application/json")
                .body(jsonRequest)
                .request().log().all()
                .when().post("https://api.playground.klarna.com/payments/v1/sessions")
                .then().assertThat().statusCode(200).and().body("session_id", matchesPattern("[0-9a-fA-F]{7}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"));

    }

}

