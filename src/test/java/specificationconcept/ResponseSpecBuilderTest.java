package specificationconcept;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;



import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecBuilderTest {
	
	// This method will check 200 ok status
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
	
	// creating negetive TC by sending wrong Token
	public static ResponseSpecification get_res_spec_401_Auth_Fail() {
		ResponseSpecification res_spec_401_AUTH_FAIL = new ResponseSpecBuilder()
			.expectStatusCode(401)
			.expectHeader("Server", "cloudflare")
			.build();
		
		return res_spec_401_AUTH_FAIL;
	}
	
	@Test
	public void get_user_res_200_spec_Test() {
		RestAssured.baseURI = "https://gorest.co.in";
		given()
			.header("Authorization", "Bearer c6642e6bace147c8a44d820831819adaa659559efc868f47a67f0bbd94cc0b11")
				.when()
					.get("/public/v2/users")
						.then().log().all()
							.assertThat()
								.spec(get_res_spec_200_OK_With_Body());
	}
	
	@Test
	public void get_user_res_401_Auth_Fail_spec_Test() {
		RestAssured.baseURI = "https://gorest.co.in";
		given()
			.header("Authorization", "Bearer c6642e6bace147c8a44d820831819adaa659559efc868f47a67f0bbd94cc0b11")
				.when()
					.get("/public/v2/users")
						.then()
							.assertThat()
								.spec(get_res_spec_401_Auth_Fail());
	}
	
	
	
	

}
