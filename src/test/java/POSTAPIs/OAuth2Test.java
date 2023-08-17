package POSTAPIs;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class OAuth2Test {
	
// Amedeus postman collection
	
static String accessToken;  // TestNG annotation method does not return anythig that's why creating static variable
	
	// This method will fetch the token first then we will use this token for all APIs call 
	// this method will run before every @test
	 
	@BeforeMethod
	public void getAccessToken() {
		//1. POST - get the access token
				RestAssured.baseURI = "https://test.api.amadeus.com"; // base url
				
				 accessToken = given()
					.header("Content-Type", "application/x-www-form-urlencoded") // here content type is not json it's a (form urlencoded) type 
					.formParam("grant_type", "client_credentials")               // passing 3 formParam grant_type, client_id, client_secret 
					.formParam("client_id", "TAnRnsU5lASXZ8mPGdwRQZMoQzhu6Gwv")
					.formParam("client_secret", "VjjgfcJilNAzcSJw")
				.when()
					.post("/v1/security/oauth2/token") // service url post call
				.then().log().all() // assert start here
					.assertThat()
						.statusCode(200)
						.extract()
							.path("access_token");
					
				System.out.println(accessToken);
				
	}
	
	// this method will get all flight info with the help of access token
	@Test
	public void getFlightInfoTest() {

		//2. get flight info: GET
	Response flightDataResponse = 	given().log().all()
		.header("Authorization", "Bearer "+accessToken)  // Authorization = "Bearer"+accessToken
		.queryParam("origin", "PAR")  // destination city nsme paris
		.queryParam("maxPrice", 200)  // max price should be 200
			.when().log().all()
				.get("/v1/shopping/flight-destinations") // service url
			.then().log().all()   // assert here
				.assertThat()
					.statusCode(200)
						.and()         // .body("data[0].type") ===> or use extract()
							.extract()
								.response();  // geting huge array so i store in a response then convert to jsonPath
	
	JsonPath js = flightDataResponse.jsonPath(); // convert response to jsonPath
	String type = js.get("data[0].type");
	System.out.println(type); //flight-destination
	
	//Assignment : fetch [data.price.total] all the total info . we have to create List of float then print .
	
		
	}

}
