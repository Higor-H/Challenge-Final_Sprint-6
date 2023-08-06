package services;

import helper.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static constants.Endpoints.SIMULACOES;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerbosService  extends BaseTest{

	private BaseRest rest = new BaseRest();
	private VariaveisUteis v;
	
	public Response deleteSimulacao(String endpoint, int id) {
		  String idS = String.valueOf(id);
		return rest.delete(SIMULACOES +"/"+ idS);
	}
	
	
}