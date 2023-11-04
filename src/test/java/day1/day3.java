package day1;

import static org.hamcrest.Matchers.equalTo;

import java.util.Random;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class day3 {

	String token = "Bearer cd2ac6be51d3e141feb492b0cc0ddd8d3eb8d0bc7da80d1a1ef57ecdd74bf517";

	protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
	
	@Test
	public void getUsers() {

		// curl -i -H "Accept:application/json" -H "Content-Type:application/json" -H
		// "Authorization: Bearer
		// cd2ac6be51d3e141feb492b0cc0ddd8d3eb8d0bc7da80d1a1ef57ecdd74bf517" -XGET
		// "https://gorest.co.in/public/v2/users"
		RestAssured.baseURI = "https://gorest.co.in/public/v2/users";
		Response response = RestAssured.given().header("Accept", "application/json")
				.header("Content-Type", "application/json").header("Authorization", token).when().get();
		response.then().statusCode(200);
		response.then().assertThat()
		.body("data[0].id", equalTo(23));
		

	}

	@Test
	public void createUser() {
		String email = getSaltString()+"@gmail.com";
		String requestBody = "{\"name\":\"Tenali Ramakrishna\", \"gender\":\"male\", \"email\":\"" + email + "\", \"status\":\"active\"}";
		RestAssured.baseURI = "https://gorest.co.in/public/v2/users";
		Response response = RestAssured
				.given()
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.header("Authorization", token)
				.body(requestBody).when().post();
		
		
		
		response.then().log().all();
		response.then().statusCode(201);
		
	}
	
	
	@Test
	public void updateUser() {
		String email = getSaltString()+"@gmail.com";
		String requestBody = "{\"name\":\"Tenali Ramakrishna\",\"email\":\"" + email + "\", \"status\":\"active\"}";
		RestAssured.baseURI = "https://gorest.co.in/public/v2/users";
		Response response = RestAssured
				.given()
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.header("Authorization", token)
				.body(requestBody).when().patch("/1700644");
		response.then().log().all();
		response.then().statusCode(200);
	}
	
	public void deleteUsers() {
		RestAssured.baseURI = "https://gorest.co.in/public/v2/users";
		Response response = RestAssured
				.given()
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.header("Authorization", token)
				.when().delete("/1700644");
		response.then().log().all();
		response.then().statusCode(204);
	}

	
	
	
	
	
	
	
	
	
	
	
	

}
