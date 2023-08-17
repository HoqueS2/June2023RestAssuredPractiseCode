package GETAPIs;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class GetAPIWithPathParam {
	
	//http://ergast.com/api/f1/2016/circuits.json ===> 2016 is not a queryparem it's a path parem 
	// In postman we use (:) colen to create path peremeter
	//http://ergast.com/api/f1/:year/circuits.json
	// query parem is add at the end only 
	// but path parem is in the middle that's why we have to explicitly tell after f1 we define the path parem
	// for example =====> api/f1/{year}/circuits.json")
	
	// query vs  path
	// <k,v>    <anykey, value>
	//  ?         /
		
		@Test
		public void getCircuitDataAPIWith_YearTest() {
			RestAssured.baseURI = "http://ergast.com";
			
			given().log().all()
				.pathParam("year", "2017")
					.when().log().all()
						.get("/api/f1/{year}/circuits.json") // get the year from the pathparam we use {year}
							.then().log().all()
								.assertThat()
									.statusCode(200)
										.and()
											.body("MRData.CircuitTable.season", equalTo("2017"))// in 2017 I want to check the season
												.and()
													.body("MRData.CircuitTable.Circuits.circuitId", hasSize(20));//  here Circuits is the json array so go to circuitId of each index and count how many circuitId we have in total in the array 
		}
		
	//============================================================================	
		
		//2017 -- 20
		//2016 -- 21
		//1966 -- 9 
		
		
		@DataProvider
		public Object[][] getCircuitYearData() {
			return new Object[][] {
				
				{"2016", 21},
				{"2017", 20},
				{"1966", 9},
				{"2023", 22}
			};
		}
		
		
		@Test(dataProvider = "getCircuitYearData")
		public void getCircuitDataAPIWith_Year_DataProvider(String seasonYear, int totalCircuits) {
			RestAssured.baseURI = "http://ergast.com";
			
			given().log().all()
				.pathParam("year", seasonYear)
					.when().log().all()
						.get("/api/f1/{year}/circuits.json")
							.then()
								.assertThat()
									.statusCode(200)
										.and()
											.body("MRData.CircuitTable.season", equalTo(seasonYear))
												.and()
													.body("MRData.CircuitTable.Circuits.circuitId", hasSize(totalCircuits));// It will capture all the circutId

		}
		// If you don't want to maintain this data in the excel sheet which is not recomended so simply use data provider.
		
		// Assignment:
		// go to Circut and collect all the circut ID in the listand printed in the console with the help of the for Loop
		// List <Integer> idList = js.getList("mrdata.CircuTable.Circut.CircutID")
	


}
