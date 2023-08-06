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
import model.Simulacao;

@Getter
@Setter
public class VerbosService  extends BaseTest{

	private BaseRest rest = new BaseRest();
	private VariaveisUteis v;
	
	public Response deleteSimulacao(String endpoint, long id) {
		  String idS = String.valueOf(id);
		return rest.delete(SIMULACOES +"/"+ idS);
	}
	
	
	public String deleteMassa (Response response, Simulacao simulacao) {
		if (response.statusCode() == 201) { // DELETANDO MASSA
			simulacao.setId(response.path("id"));
			Response responseDelete = deleteSimulacao(SIMULACOES, simulacao.getId());
			
			return "Ok";} 
		
		return "Não excluido";
		
	}
	
}