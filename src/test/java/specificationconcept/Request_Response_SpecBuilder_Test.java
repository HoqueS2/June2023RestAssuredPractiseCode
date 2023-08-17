package specificationconcept;
// assignment
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Request_Response_SpecBuilder_Test {
	
	public static RequestSpecification user_req_spec() {
		
		RequestSpecification requestSpec = new RequestSpecBuilder()// create obj from RequestSpecBuilder class
				.setBaseUri("https://gorest.co.in")
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", "Bearer c6642e6bace147c8a44d820831819adaa659559efc868f47a67f0bbd94cc0b11")
				.build(); // In selenium we use [ Action.movetoElement.click.build.perform ] for builder patern by the help of action class. here we r doing the same thing. here we have 3 action then we r saying build this 3 actions.		
		return requestSpec; // build() will return RequestSpecification. RequestSpecification (Interface), RequestSpecBuilder (class)
		}

	//This method will check 200 ok status
	public static ResponseSpecification get_res_spec_200_OK() {
		ResponseSpecification res_spec_200_ok = new ResponseSpecBuilder() // ResponseSpecBuilder(C), ResponseSpecification(I)
			.expectContentType(ContentType.JSON) // here we write what r we expecting from response
			.expectStatusCode(200)
			.expectHeader("Server", "cloudflare")
			.build();
		
		return res_spec_200_ok;
	}
	
	public static ResponseSpecification get_res_spec_200_OK_With_Body() {
		ResponseSpecification res_spec_200_ok = new ResponseSpecBuilder()
			.expectContentType(ContentType.JSON)
			.expectStatusCode(200)
			.expectHeader("Server", "cloudflare")
			.expectBody("$.size()", equalTo(10))  // here we write what we r expect from the response body
			.expectBody("id", hasSize(10))
			.build();
		
		return res_spec_200_ok;
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
	public void get_user_res_200_spec_Test_with_Request_And_Response_Specification() {
		given().log().all()
			.spec(user_req_spec())
					.get("/public/v2/users")
						.then().log().all()
							.assertThat()
								.spec(get_res_spec_200_OK_With_Body());
	}



}
