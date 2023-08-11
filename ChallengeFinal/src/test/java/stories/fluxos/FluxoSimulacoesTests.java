package stories.fluxos;


import static constants.Endpoints.SIMULACOES;
import static helper.ServiceHelper.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import static org.hamcrest.CoreMatchers.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import datafactory.DynamicFactory;
import datafactory.FakeCPF;
import helper.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.Simulacao;

public class FluxoSimulacoesTests extends BaseTest{
	
	@Test
	@Epic("Simulacoes")
	@Epic("Fluxos")
	@Tag("Fluxo")
	@Description("Fluxo passando por todos os verbos da rota Simulacoes")
	public void deveRealizarFluxoNaRotaSimulacoes() {
		
		// GET ALL
		Response responseGetAll = rest.get(SIMULACOES, v.getNada());
		
		//POST
		String cpfValidoPost = FakeCPF.gerarCPFValido();
		Simulacao simulacaoPost = DynamicFactory.generateRandomSimulacaosStr(cpfValidoPost);
		Response responsePost = rest.post(SIMULACOES, simulacaoPost );
		
		//PUT
		String cpfValidoPut = FakeCPF.gerarCPFValido();
		Simulacao simulacaoPUT = DynamicFactory.generateRandomSimulacaosStr(cpfValidoPut);
		Response responsePUT = service.putSimulacao(cpfValidoPost, simulacaoPUT );
		
		//GET CPF
		Response responseGet = rest.get(SIMULACOES, cpfValidoPut);
		
		//DELETE
		simulacaoPUT.setId(responseGet.path("id"));
		Response responseDELETE = service.deleteSimulacao(SIMULACOES, simulacaoPUT.getId());
				
		//ASSERTS ================================================================================
		
		// GET ALL -------------------------------------------------------------------------------
		if (responseGetAll.body() == nullValue()) {
		
			assertThat(responseGetAll.statusCode(), is(204));
			assertThat(responseGetAll.statusCode(), is(not(400)));
			assertThat(responseGetAll,is(not(nullValue())));
			assertThat(responseGetAll.body(),is(nullValue()));
			assertThat(responseGetAll.header("Transfer-Encoding"),equalTo("chunked"));
		} else {
			
			assertThat(responseGetAll.statusCode(), is(200));
			assertThat(responseGetAll.statusCode(), is(not(400)));
			assertThat(responseGetAll,is(not(nullValue())));
			assertThat(responseGetAll.body(),is(not(nullValue())));
			assertThat(responseGetAll.asString(),matchesJsonSchema("simulacoes","getVariacoes", 200));
			assertThat(responseGetAll.header("Transfer-Encoding"),equalTo("chunked"));
			}
		
		
		// POST -------------------------------------------------------------------------------
		assertThat(responsePost.statusCode(), is(201));
		assertThat(responsePost.statusCode(), is(not(409)));
		assertThat(responsePost,is(not(nullValue())));
		assertThat(responsePost.body(),is(not(nullValue())));
		assertThat(responsePost.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responsePost.path("nome"), equalTo(simulacaoPost.getNome()));
		assertThat(responsePost.path("cpf"), equalTo(cpfValidoPost));
		assertThat(responsePost.path("email"), equalTo(simulacaoPost.getEmail()));
		assertThat(responsePost.path("valor"), equalTo(simulacaoPost.getValor()));
		assertThat(responsePost.path("parcelas"), equalTo(simulacaoPost.getParcelas()));
		assertThat(responsePost.path("seguro"), equalTo(simulacaoPost.isSeguro()));
		assertThat(responsePost.asString(),matchesJsonSchema("simulacoes","post", 201));
		
		
		// PUT -------------------------------------------------------------------------------
		//float valorPutReq = Float.valueOf(simulacaoPUT.getValor());
		//float valorPutRes = responsePUT.path("valor");
		assertThat(responsePUT.statusCode(), is(200)); 
		assertThat(responsePUT,is(not(nullValue())));
		assertThat(responsePUT.body(),is(not(nullValue())));
		assertThat(responsePUT.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responsePUT.path("nome"), allOf(equalTo(simulacaoPUT.getNome()), is(not(simulacaoPost.getNome()))));
		assertThat(responsePUT.path("cpf"), allOf(equalTo(simulacaoPUT.getCpf())), is(not(simulacaoPost.getCpf())));
		assertThat(responsePUT.path("email"), allOf(equalTo(simulacaoPUT.getEmail())), is(not(simulacaoPost.getEmail())));
		//assertThat(valorPutRes,allOf( equalTo(valorPutReq),is(not(nullValue()))));
		assertThat(responsePUT.path("parcelas"),allOf(equalTo(simulacaoPUT.getParcelas()),is(not(nullValue()))));
		assertThat(responsePUT.path("seguro"),allOf(equalTo(simulacaoPUT.isSeguro()),is(not(nullValue()))));
		assertThat(responsePUT.asString(),matchesJsonSchema("simulacoes","put", 200));
		
		
		// GET CPF -------------------------------------------------------------------------------
		//float valorPutReqGet = Float.valueOf(simulacaoPUT.getValor());
		//float valorPutResGet = responseGet.path("valor");
		assertThat(responseGet.statusCode(), is(200));
		assertThat(responseGet.statusCode(), is(not(400)));
		assertThat(responseGet,is(not(nullValue())));
		assertThat(responseGet.body(),is(not(nullValue())));
		assertThat(responseGet.asString(),matchesJsonSchema("simulacoes","get", 200));
		assertThat(responseGet.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responseGet.path("nome"), equalTo(simulacaoPUT.getNome()));
		assertThat(responseGet.path("cpf"), equalTo(cpfValidoPut));
		assertThat(responseGet.path("email"), equalTo(simulacaoPUT.getEmail()));
	//	assertThat(valorPutResGet,allOf( equalTo(valorPutReqGet),is(not(nullValue()))));
		assertThat(responseGet.path("parcelas"), equalTo(simulacaoPUT.getParcelas()));
		assertThat(responseGet.path("seguro"), equalTo(simulacaoPUT.isSeguro()));
		
		
		// DELETE -------------------------------------------------------------------------------
		assertThat(responseDELETE.statusCode(), is(200)); 
		assertThat(responseDELETE.statusCode(), is(not(404))); 
		assertThat(responseDELETE,is(not(nullValue())));
		assertThat(responseDELETE.body(),is(not(nullValue())));
		assertThat(responseDELETE.header("Content-Type"),equalTo("application/json;charset=UTF-8"));
		assertThat(responseDELETE.header("Content-Length"),equalTo("2"));
		assertThat(responseDELETE.getBody().asString(),equalTo("OK"));
	}

}
