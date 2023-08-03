package services;


import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

import helper.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BaseRest extends BaseTest{
	private RequestSpecification requestSpec;
	//RequestSpecification
	public RequestSpecBuilder getSpecBuilder() {
		

		return new RequestSpecBuilder().
				
				setBaseUri(EnvConfig.getProperty("url", "")).
//				setPort(Integer.parseInt(EnvConfig.getProperty("port", "" + RestAssured.port))).
				setAccept(JSON).setBasePath("/api/v1");
			
	}
	
	
	public BaseRest() {
		requestSpec = getSpecBuilder().build();
	}
	
	
	//VERBOS
	 
	public Response get(String endpoint, String cpf) {
		return
				
		given().
			spec(requestSpec).
			log().all().
		when().
			get(endpoint + "/" + cpf).
		then().
			log().all().
			extract().
			response();
	}
	
	public Response post(String endpoint, Object payload) {
		return
				
		given().
			spec(requestSpec).
			contentType(JSON).
			body(payload).
			log().all().
		when().
			post(endpoint).
		then().
			log().all()
			.extract()
			.response();
	}
	
	public Response put(String endpoint, Object payload) {
		return
		given().
		spec(requestSpec).
		contentType(JSON).
		body(payload).
		log().all().
	when().
		put(endpoint).
	then().
		log().all()
		.extract()
		.response();
	}
	
	public Response delete(String endpoint) {
		return
				
		given().
			spec(requestSpec).
			log().all().
		when().
			delete(endpoint).
		then().
			log().all().
			extract().
			response();
	}
	
	public Response path(String endpoint, Object payload) {
		return
				
		given().
			spec(requestSpec).
			log().all().
			body(payload).
		when().
			patch(endpoint).
		then().
			log().all().
			extract().
			response();
	}
	
	public Response head(String endpoint) {
		return
				
		given().
			spec(requestSpec).
			log().all().
		when().
			head(endpoint).
		then().
			log().all().
			extract().
			response();
	}
	
	public Response options(String endpoint) {
		return
				
		given().
			spec(requestSpec).
			log().all().
		when().
			options(endpoint).
		then().
			log().all().
			extract().
			response();
	}
}