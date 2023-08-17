package GETAPIs;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

public class GETAPIWithBDD {
	
	@Test
	public void getProductsTest() {
		
// given(), when(),then(),and(), all the methd are static so call them directly without using class name (RestAssured)
// manually import 2 Library for that so that you don't need to write [ RestAssured.given() ]
// import static io.restassured.RestAssured.*;
		
		given().log().all()
			.when() .log().all()// after when you must hit Api
				.get("https://fakestoreapi.com/products")
					.then().log().all() // assertion start from here
						.assertThat()
							.statusCode(200)
								.and()
									.contentType(ContentType.JSON)
										.and()
											.header("Connection", "keep-alive")
												.and()
													.body("$.size()", equalTo(20)) // For Body assertion you need hamcrast library $ means representing the entire response body. In this API $ represent the Array object 
														.and()
															.body("id", is(notNullValue()))
																.and()
																	.body("title", hasItem("Mens Cotton Jacket")); 
	}	
	
	
	@Test
	public void getUserAPITest() {
		RestAssured.baseURI = "https://gorest.co.in";

		given()
			.header("Authorization", "Bearer c6642e6bace147c8a44d820831819adaa659559efc868f47a67f0bbd94cc0b11")
				.when().log().all()
					.get("/public/v2/users/")
						.then().log().all()
							.assertThat()
								.statusCode(200)
								.and()
								.contentType(ContentType.JSON)
									.and()
										.body("$.size()", equalTo(10));
//For Body assertion you need hamcrast library $ means representing the entire response body. In this API $ represent the json Array object 
// For array we use .size() method
// equalTo() method coming from Hamcrast.Matcher lLibrary for assertion
		
	}
	
	@Test
	public void getProductDataAPIWithQueryParamTest() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		
		given().log().all()
			.queryParam("limit", 5) // give me only limit product. Its a queryparam where I look for 5 product.
				.when().log().all()
					.get("/products")
					.then().log().all()
					.assertThat()
					.statusCode(200)
					.and()
					.contentType(ContentType.JSON);

	}
	
	// Assignment complete
	@Test
	public void getAllUsersWithQueryParameterAPITest() {
		RestAssured.baseURI = "https://gorest.co.in";

		given()
			.header("Authorization", "Bearer c6642e6bace147c8a44d820831819adaa659559efc868f47a67f0bbd94cc0b11")
			.queryParam("name", "naveen")
			.queryParam("status", "active")
				.when().log().all()
					.get("/public/v2/users/")
						.then().log().all()
							.assertThat()
								.statusCode(200)
									.and()
										.contentType(ContentType.JSON);

	}
	
// From specific Index fetch the value.Like from 0 index give me first product ID. JSON PATH API help to do that	
	@Test
	public void getProductDataAPI_With_Extract_Body() {
		RestAssured.baseURI = "https://fakestoreapi.com";
// First hit the API then store the entire response to the variable response.
		Response response =  given().log().all()
									.queryParam("limit", 5)
											.when().log().all()
													.get("/products");
					
		JsonPath js = response.jsonPath(); // Initialize the JsonPath
		
		//get the id of the first product:
		int firstProductId = js.getInt("[0].id");
		System.out.println("firstProductId = " + firstProductId);
		
		String firstProductTitle = js.getString("[0].title");
		System.out.println("firstProductTitle = " + firstProductTitle );
		
		float price = js.getFloat("[0].price");
		System.out.println("price = " + price);
		
		int count = js.getInt("[0].rating.count");
		System.out.println("count = " + count);
		
	}
	
// From Array response body collect all the ID's.With query param
	@Test
	public void getProductDataAPI_With_Extract_Body_withJSONArray() {
		
		RestAssured.baseURI = "https://fakestoreapi.com";
		
		Response response =  given().log().all()
									.queryParam("limit", 10)
											.when().log().all()
													.get("/products");
					
		JsonPath js = response.jsonPath(); //Json Array
		
		// getList () return Obj. what type of obj? Here type of obj is List with generic Integer. Integer.class/Float.class means we specify which generic I'm using 
		List<Integer> idList = js.getList("id", Integer.class);//0-4==>5   getList() method will automatically reach to all index and return list of ID
		List<String> titleList = js.getList("title");
		//List<Object> rateList = js.getList("rating.rate");
		List<Float> rateList = js.getList("rating.rate", Float.class);// one particular value output is int that's why we specify the class ==> Float.class splecitly saying store data in Float
		List<Integer> countList = js.getList("rating.count");
		
		
		for(int i=0; i<idList.size(); i++) {
			int id = idList.get(i);
			String title = titleList.get(i);
			Object rate = rateList.get(i);
			int count = countList.get(i);
			
			System.out.println("ID: " + id + " " + "Title: " + title + " " + "Rate: " + rate + " " + "Count: "+ count);
		}	
		
	}
	
//from json response body collect specific user info. Before run change the user id
// here response is a normal json obj.
	@Test
	public void getUserAPI_With_Extract_Body_withJson() {
		
		RestAssured.baseURI = "https://gorest.co.in";

		Response response = given().log().all()
			.header("Authorization", "Bearer c6642e6bace147c8a44d820831819adaa659559efc868f47a67f0bbd94cc0b11")
				.when().log().all()
					.get("/public/v2/users/3571519"); // return type of GET() is respons so we store in a Response class 
		
		JsonPath js = response.jsonPath(); // now convert to Json response and return type of jsonPath is JsonPath
		/* now js has all the value
		 * 
{    "id": 3637037,
    "name": "Sadiah_R",
    "email": "Daron85@gmail.com",
    "gender": "female",
    "status": "active"
}*/
		
		System.out.println(js.getInt("id")); // getInt() return pure integer not list
		System.out.println(js.getString("email"));	
	}
	
// Change User id manually before run this TC
	// Instead of JsonPath we can use extract() to fetch the info from response
	@Test
	public void getUserAPI_With_Extract_Body_withJson_Extract() {
		
		RestAssured.baseURI = "https://gorest.co.in";
				
		Response response = given().log().all()
				.header("Authorization", "Bearer c6642e6bace147c8a44d820831819adaa659559efc868f47a67f0bbd94cc0b11")
					.when().log().all()
						.get("/public/v2/users/3637037") 
							.then()
								.extract()
									.response();
		/* now response has all the value
		 * 
{    "id": 3637037,
    "name": "Sadiah_R",
    "email": "Daron85@gmail.com",
    "gender": "female",
    "status": "active"
}*/
		// In general return type of path is Obj					
		int userId = response.path("id");	// return type of path here int
		String email = response.path("email"); // return type of path here string				
		
			
			System.out.println(userId);
			System.out.println(email);
	}	

}
