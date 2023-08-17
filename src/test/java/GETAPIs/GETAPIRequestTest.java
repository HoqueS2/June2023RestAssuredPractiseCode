package GETAPIs;

//import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GETAPIRequestTest {
	
	@Test
	public void getAllUsersAPITest() {
		
		// Request part
		RestAssured.baseURI = "https://gorest.co.in";
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer c6642e6bace147c8a44d820831819adaa659559efc868f47a67f0bbd94cc0b11");
		// return type of GET method give you response
		// for all the HTTP method will return the Response
		Response response = request.get("/public/v2/users/"); // store all the response in the variable response
		
		//-----------------------------------------------------------
		
		// Now start fetching information from response
		
		int statusCode= response.statusCode();
		System.out.println("Status code: " + statusCode);
		
		//Assertion part actual vs expetation
		
		Assert.assertEquals(statusCode, 200);
		
		// featch status message
		
		String message = response.statusLine();
		System.out.println(message);
		
		// fetch the full body:
		response.prettyPrint();
		
		
		// fetch header:
		String contentType = response.header("Content-Type");
		System.out.println(contentType);
		
		System.out.println("-----------------");
		// fetch all headers:
		List<Header> headersList = response.headers().asList(); // asList give you list of header
		System.out.println(headersList.size()); // length of list

		for (Header h : headersList) {
			System.out.println(h.getName() + ":" + h.getValue());
		}	
		
	}
	
	
	
	@Test
	public void getAllUsersWithQueryParameterAPITest() {
		
		// Request part
		RestAssured.baseURI = "https://gorest.co.in";
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer c6642e6bace147c8a44d820831819adaa659559efc868f47a67f0bbd94cc0b11");
		// use Query parem here before get method
		request.queryParam("name", "naveen");
		request.queryParam("status", "active");
		Response response = request.get("/public/v2/users/"); // store all the response in the variable response
		
		//=====================================================
		
		// Now start fetching information from response
		
		int statusCode= response.statusCode();
		System.out.println("Status code: " + statusCode);
		
		//Assertion part actual vs expetation
		
		Assert.assertEquals(statusCode, 200);
		
		// featch status message
		
		String message = response.statusLine();
		System.out.println(message);
		
		// fetch the full body:
		response.prettyPrint();	
		
	}
	
	
	@Test
	public void getAllUsersWithQueryParameter_WithHashMap_APITest() {
		
		// Request part
		RestAssured.baseURI = "https://gorest.co.in";
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer c6642e6bace147c8a44d820831819adaa659559efc868f47a67f0bbd94cc0b11");
		// If you have more then one query use MAP  
		// use Query parem here before get method
		Map<String, String> queryParamsMap = new HashMap<String, String>();
		queryParamsMap.put("name", "naveen");
		queryParamsMap.put("gender", "male");
		request.queryParams(queryParamsMap); // add the map to the request by using queryParams method 
		Response response = request.get("/public/v2/users/"); // store all the response in the variable response
		
		//=====================================================
		
		// Now start fetching information from response
		
		int statusCode= response.statusCode();
		System.out.println("Status code: " + statusCode);
		
		//Assertion part actual vs expetation
		
		Assert.assertEquals(statusCode, 200);
		
		// featch status message
		
		String message = response.statusLine();
		System.out.println(message);
		
		// fetch the full body:
		response.prettyPrint();	
		
	}
	
	
	

}
