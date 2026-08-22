package apiSteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utility.APIPayloadConstants;

import static io.restassured.RestAssured.given;

// https://restful-booker.herokuapp.com/apidoc/index.html
public class booking_lifecycle_steps {
   static final String BASE_URL ="https://restful-booker.herokuapp.com";
   RequestSpecification request;
   Response response;
   static String token ="";
   static int bookingId;

    @Given("a request is prepared to authenticate")
    public void a_request_is_prepared_to_authenticate() {
      request=given()
              .header("Content-Type", "application/json")
              .body("{\n" +
                      "    \"username\" : \"admin\",\n" +
                      "    \"password\" : \"password123\"\n" +
                      "}");
    }


    @When("a post request is made for {string}")
    public void a_post_request_is_made_for(String urlType) {
        String url = BASE_URL+"/";
        switch (urlType) {
            case "auth": url+="auth";break;
            case "create": url+="booking";break;
            default:break;
        }
        response = request.when().post(url);
    }

    @Then("the token is stored as a global variable")
    public void the_token_is_stored_as_a_global_variable() {
        token = response.jsonPath().getString("token");
    }

    @When("a get request is made for {string}")
    public void a_get_request_is_made_for(String urlType) {
        String url = BASE_URL+"/";
        switch (urlType) {
            case "a booking": url+="booking/"+bookingId;break;
            case "all booking": url+="booking";break;
            case "invalid booking": url+="booking/999999999";break;
            default:break;
        }

        response = request.when().get(url);
    }

    @Then("a statuscode of {int} is received")
    public void a_statuscode_of_is_received(Integer statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    @Given("a request is prepared to create a booking")
    public void a_request_is_prepared_to_create_a_booking() {
        request =  given()
                .header("Content-Type", "application/json")
                .body(APIPayloadConstants.getCreateUpdatePayload("Jim","Brown",111,true,
                        "2018-01-01","2019-01-01","Breakfast"));
    }

    @Then("the bookingid is stored as a global variable")
    public void the_bookingid_is_stored_as_a_global_variable() {
     bookingId=response.jsonPath().getInt("bookingid");
    }

    @Given("a request is prepared to get a booking using the stored bookingid")
    public void a_request_is_prepared_to_get_a_booking_using_the_stored_bookingid() {

        request=given()
                .header("Content-Type", "application/json");
    }


    @Given("a request is prepared to update a booking using the stored token")
    public void a_request_is_prepared_to_update_a_booking_using_the_stored_token() {
        request=given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token="+token)
                .body(APIPayloadConstants.getCreateUpdatePayload("James updated","Brown",111,true,
                                "2018-01-01","2019-01-01","Breakfast")
                      );
        /*  "{\n" +
                        "    \"firstname\" : \"James updated\",\n" +
                        "    \"lastname\" : \"Brown\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}"*/
    }

    @When("a put request is made")
    public void a_put_request_is_made() {
        response = request
                .when()
                .put(BASE_URL +"/booking/"+bookingId);
    }

    @Given("a request is prepared to delete a booking using the stored token")
    public void a_request_is_prepared_to_delete_a_booking_using_the_stored_token() {
        request=given()
                .header("Cookie", "token="+token);
    }

    @When("a delete request is made")
    public void a_delete_request_is_made() {
      response = request.when().delete(BASE_URL +"/booking/"+bookingId);
    }

    @Given("a request is prepared to get all booking")
    public void a_request_is_prepared_to_get_all_booking() {
        request = given();
    }

    @Then("the firstname in the response is {string} for {string}")
    public void the_firstname_in_the_response_is(String expectedFirstname, String type) {
        String actualFirstname = "";
        switch (type) {
            case "create":
                actualFirstname = response.jsonPath().getString("booking.firstname");
                break;
           case "update":
               actualFirstname = response.jsonPath().getString("firstname");
                break;
        }
        Assert.assertEquals(expectedFirstname, actualFirstname);
    }

    @Given("a request is prepared to update a booking without a token")
    public void a_request_is_prepared_to_update_a_booking_without_a_token() {
        request = given()
                .header("Content-Type", "application/json")
                .body(APIPayloadConstants.getCreateUpdatePayload("Not to be updated","Brown",111,true,
                        "2018-01-01","2019-01-01","Breakfast"));

    }

    @Given("a request is prepared to get a booking with an invalid id")
    public void a_request_is_prepared_to_get_a_booking_with_an_invalid_id() {
        request = given()
                .header("Content-Type", "application/json");
    }

}
