package POSTAPIs;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pojo.Credentials;


public class BookingAuthWithPojoTest {
	
	
	
	//POJO - Plain Old Java Object --
		//can not extend any other class, its an independent class
		//oop - encapsulation  : data hiding means private veritable. pojo class is classic example of encapsulation
		//private class vars -- json body
		//public getter/setter
		
		//serialization --> java object to other format: file, media, json/xml/text/pdf
		//pojo to json -- serialization
		//json to pojo - De-serialization
		//add jckson dependency for the serialization
		
		@Test
		public void getBookingAuthTokenTest_With_Json_String() {
			
			RestAssured.baseURI = "https://restful-booker.herokuapp.com";
			Credentials creds = new Credentials("admin", "password123");   // from pojo pakage
			
			String tokenId = given().log().all()
				.contentType(ContentType.JSON)
				
				.body(creds)  // ==========> sending pojo obj serelijation 
				.when().log().all()
					.post("/auth")
						.then()
							.assertThat()
								.statusCode(200)
									.extract()
										.path("token");
						
				
			System.out.println(tokenId);	
			Assert.assertNotNull(tokenId);
			
			//res json -- username --- compare with getter(getusername())
			
		}
		
		

}
