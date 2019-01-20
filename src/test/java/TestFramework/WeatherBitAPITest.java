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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class WeatherBitAPITest {

	Properties prop=new Properties();
	
	@BeforeTest
	public void getData() throws IOException
	{
		String path = System.getProperty("user.dir") + "/src/main/java/TestFramework/";
		FileInputStream fis=new FileInputStream(path + "env.properties");
		prop.load(fis);
	}

	@Test
	public void getCurrentDataTest()
	{
		//BaseURL or Host
		RestAssured.baseURI= (String) prop.get("HOST");

		Response resp = given().
				param("lat","40.730610").
				param("lon","-73.935242").
				param("key","bc736ef72a1b44cfb9e5b35a614ad6af").
				when().
				get("/current").
				then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.and().
				body("data[0].state_code",equalTo("NY")).
				extract().response();

		JsonPath js= ReusableMethods.rawToJson(resp);

		int count=js.get("data[0].size()");
		System.out.println(count);
		System.out.println(js.get("data[0].state_code"));
		
		
	}


	@Test
	public void getHourlyForcastTest()
	{
		//BaseURL or Host
		RestAssured.baseURI= (String) prop.get("HOST");

		Response resp = given().
				param("postal_code","28546").
				param("key","bc736ef72a1b44cfb9e5b35a614ad6af").
				when().
				get("/forecast/3hourly").
				then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.and().
				extract().response();

		JsonPath js= ReusableMethods.rawToJson(resp);

		int count=js.get("data.size()");
		System.out.println(count);

		for(int i=0;i<count;i++)
		{
			System.out.println(js.get("data["+i+"].timestamp_utc"));
			System.out.println(js.get("data["+i+"].weather"));
		}


	}


}
