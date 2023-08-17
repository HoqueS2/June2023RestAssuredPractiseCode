package specificationconcept;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
// below this 2 static method will help to 
import static io.restassured.RestAssured.given; // no need to write RestAssured. given.then. again again in the @test directly write .given
import static org.hamcrest.Matchers.*;  // for  response body assertion


public class RequestSpecBuilderTest {
	
public static RequestSpecification user_req_spec() {
		
		RequestSpecification requestSpec = new RequestSpecBuilder()// create obj from RequestSpecBuilder class
				.setBaseUri("https://gorest.co.in")
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", "Bearer c6642e6bace147c8a44d820831819adaa659559efc868f47a67f0bbd94cc0b11")
				.build(); // In selenium we use [ Action.movetoElement.click.build.perform ] for builder patern by the help of action class. here we r doing the same thing. here we have 3 action then we r saying build this 3 actions.		
		return requestSpec; // build() will return RequestSpecification. RequestSpecification (Interface), RequestSpecBuilder (class)
		}
	
	
	@Test
	public void getUser_With_Request_Spec() {
		given().log().all()
					.spec(user_req_spec())
						.get("/public/v2/users")
							.then().log().all()
								.statusCode(200);
	}
	
	@Test
	public void getUser_With_Param_Request_Spec() {
		given().log().all()
			.queryParam("name", "naveen")
			.queryParam("status", "active")
					.spec(user_req_spec())
						.get("/public/v2/users")
							.then().log().all()
								.statusCode(200);
	}
	

}
