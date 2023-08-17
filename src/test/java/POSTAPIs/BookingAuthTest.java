package POSTAPIs;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.io.File;


public class BookingAuthTest {
	
	@Test // sending hard coded body
	public void getBookingAuthTokenTest_With_Json_String() {
		
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";   // base url
		
		String tokenId = given()
			.contentType(ContentType.JSON)
			.body("{\n"                                      //   \n means newline
					+ "    \"username\" : \"admin\",\n"
					+ "    \"password\" : \"password123\"\n"
					+ "}")
			.when()
				.post("/auth")         // service url with What call? POST Call
					.then().log().all()
						.assertThat()
							.statusCode(200)
								.extract()   // fetching response data
									.path("token");
					
			
		System.out.println(tokenId);	
		Assert.assertNotNull(tokenId);
		
		
	}

	// Sending body through File 
	@Test
	public void getBookingAuthTokenTest_With_JSON_File() {
		
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		
		String tokenId = given()
			.contentType(ContentType.JSON)
			.body(new File("./src/test/resources/data/basicAuth.json")) // create new file obj. (. means go to your project and under project go to /src/test/resources/data/basicAuth.json)
			.when()
				.post("/auth")
					.then()
						.assertThat()
							.statusCode(200)
								.extract()
									.path("token");
					
			
		System.out.println(tokenId);	
		Assert.assertNotNull(tokenId);
		
		
	}
	
	
	
	//post -- add a user -- user id = 123 -- assert(201, body)
		//get --> get a user --> /users/123 -- 200 -- userid=123
	// change the email in the file manually
		
		@Test
		public void addUserTest() {
			RestAssured.baseURI = "https://gorest.co.in";
			
			//1. add user - POST
			int userId = given().log().all()
				.contentType(ContentType.JSON)
				.body(new File("./src/test/resources/data/adduser.json"))
				.header("Authorization", "Bearer c6642e6bace147c8a44d820831819adaa659559efc868f47a67f0bbd94cc0b11").
			when()
				.post("/public/v2/users/").
			then().log().all()
				.assertThat()
					.statusCode(201)
					.and()
					.body("name", equalTo("Aiyan"))
					.extract()
						.path("id"); // explecitly saying my .path("id") will return int
					
			System.out.println("user id -->" + userId);
			
			//2. get the same user and verify it: GET
			given()
			.header("Authorization", "Bearer c6642e6bace147c8a44d820831819adaa659559efc868f47a67f0bbd94cc0b11")
				.when().log().all()
					.get("/public/v2/users/"+ userId)
						.then().log().all()
							.assertThat()
								.statusCode(200)
									.and()
										.body("id", equalTo(userId));
		}
			
	
}
