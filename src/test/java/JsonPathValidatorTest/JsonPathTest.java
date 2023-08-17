package JsonPathValidatorTest;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class JsonPathTest {
	
	
	
	
	
	 @Test
	    public void getCountryValue() {
	        // Specify the base URL and path parameter (year) for the GET call
	        String baseUrl = "http://ergast.com/api/f1";
	        String year = "2017";
	        String url = baseUrl + "/" + year + "/circuits.json";

	        // Send the GET request and extract the response
	        Response response = given().get(url);

	        // Extract the response body as a string
	        String jsonResponse = response.getBody().asString();

	        // Use JsonPath to extract the country value using the given JSONPath expression
	        List<String> countryList = JsonPath.read(jsonResponse, "$.MRData.CircuitTable.Circuits[?(@.circuitId == 'shanghai')].Location.country");

	        // Print the country value
	        System.out.println("Country: " + countryList);
	       
	    }
	
	
	

	@Test
	public void getCircuitDataAPIWith_YearTest() {
		RestAssured.baseURI = "http://ergast.com";

		Response response = given().log().all().when().log().all().get("/api/f1/2017/circuits.json");

		String jsonResponse = response.asString();
		System.out.println(jsonResponse);

// here jsonPath is from JwayApi and using read method where we passing the string jsonResponse from respons body with our query
// read methodreturn the obj so you deceide which kind of obj you want to store. here we store in int 
		int totalCircuits = JsonPath.read(jsonResponse, "$.MRData.CircuitTable.Circuits.length()");
		System.out.println(totalCircuits);

// read methodreturn the obj so you deceide which kind of obj you want to store. here we store in list of string		
		List<String> countryList = JsonPath.read(jsonResponse, "$..Circuits..country");
		System.out.println(countryList.size());
		System.out.println(countryList);

	}

	@Test
	public void getProductTest() {

		RestAssured.baseURI = "https://fakestoreapi.com";

		Response response = given().when().get("/products"); // return type of GET method is response

		String jsonResponse = response.asString();
		System.out.println(jsonResponse);

		List<Float> rateLessThanThree = JsonPath.read(jsonResponse, "$[?(@.rating.rate < 3)].rating.rate");
		System.out.println(rateLessThanThree);

		System.out.println("---------------");

		// with two attributes
		// string will store value
		// and obj will be store different data like... int , string , double 
		
		/* Key  ==> Value
		 * title : White Gold Plated Princess
		   price : 9.99*/
		List<Map<String, Object>> jewellryList = JsonPath.read(jsonResponse,
				"$[?(@.category == 'jewelery')].[\"title\",\"price\"]"); // we use one / here so that java will ignore the double quote in the query section
		System.out.println(jewellryList);

		for (Map<String, Object> product : jewellryList) {
			String title = (String) product.get("title");
			Object price = (Object) product.get("price");

			System.out.println("title : " + title);
			System.out.println("price : " + price);
			System.out.println("--------");

		}
		
		System.out.println("---------------");

     /*  Key ==> Value
        title : Pierced Owl Rose Gold Plated Stainless Steel Double
		price : 10.99
		id    : 8
      * */
		// with three attributes
		
		// List is order base collection
		List<Map<String, Object>> jewellryList2 = JsonPath.read(jsonResponse,
				"$[?(@.category == 'jewelery')].[\"title\",\"price\", \"id\"]");
		System.out.println(jewellryList);

		for (Map<String, Object> product : jewellryList2) {
			String title = (String) product.get("title");
			Object price = (Object) product.get("price");
			Integer id = (Integer) product.get("id");


			System.out.println("title : " + title);
			System.out.println("price : " + price);
			System.out.println("id : " + id);
			System.out.println("--------");

		}

	}
	

}
