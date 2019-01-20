package TestFramework;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import TestFramework.ExcelUtil;

public class PerformaceTest {

	Properties prop=new Properties();
	public ExcelUtil reader;
	
	
	
	@BeforeClass
	public void beforeClass() throws IOException
	{
		reader = new ExcelUtil(System.getProperty("user.dir") + "/src/main/java/resources/testdata.xlsx");
	}
	
	@BeforeTest
	public void getData() throws IOException
	{
		String path = System.getProperty("user.dir") + "/src/main/java/TestFramework/";
		FileInputStream fis=new FileInputStream(path + "env.properties");
		prop.load(fis);
		
		
	}

	@Test
	public void apiPerformanceTest()
	{
		//BaseURL or Host
		RestAssured.baseURI= (String) prop.get("HOST");

		String latitude; 
		String longitute;
		String postcode;
		long startTime;
		long endTime;
		
		startTime = System.currentTimeMillis( );
		
		for(int i=2;i<12;i++)
		{
			System.out.println(reader.getCellData("coordinates", "Lat", i));
			System.out.println(reader.getCellData("coordinates", "Lon", i));
			System.out.println(reader.getCellData("postcode", "code", i));
			
			latitude = reader.getCellData("coordinates", "Lat", i);
			longitute = reader.getCellData("coordinates", "Lon", i);
			postcode = reader.getCellData("postcode", "code", i);
			
			Response resp1 = given().
					param("lat",latitude).
					param("lon",longitute).
					param("key","bc736ef72a1b44cfb9e5b35a614ad6af").
					when().
					get("/current").
					then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
					.and().
					extract().response();

			JsonPath js1= ReusableMethods.rawToJson(resp1);

			int count1=js1.get("data[0].size()");
			System.out.println(count1);
			System.out.println(js1.get("data[0].state_code"));
			
			
			Response resp2 = given().
					param("postal_code",postcode).
					param("key","bc736ef72a1b44cfb9e5b35a614ad6af").
					when().
					get("/forecast/3hourly").
					then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
					.and().
					extract().response();

			JsonPath js2= ReusableMethods.rawToJson(resp2);

			int count2=js2.get("data.size()");
			System.out.println(count2);

			for(int j=0;j<count2;j++)
			{
				System.out.println(js2.get("data["+j+"].timestamp_utc"));
				System.out.println(js2.get("data["+j+"].weather"));
			}
		}
		endTime = System.currentTimeMillis( );
        long diff = endTime - startTime;
        System.out.println("Total Time Taken for the 10 getCurrentData API and 10 getHourlyForecast API in milliseconds : " + diff);
		
	}


}
