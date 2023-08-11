package stories.US002;

import static constants.Endpoints.SIMULACOES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Random;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import datafactory.DynamicFactory;
import helper.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.Simulacao;
import services.VerbosService;

public class DELETESimulacoesTests extends BaseTest {
	
	private VerbosService verbosService = new VerbosService();
	
	@Test
	@Epic("Simulacoes")
	@Tag("DELETE")
	@Description("CTU2R12_002 Teste tentando realizar um DELETE em uma simulação pelo ID")
	public void deveDeletarSimulacaoComDELETE() {
		//CTU2R12_002 Teste tentando realizar um DELETE em uma simulação pelo ID
		
		//criando simulação
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		simulacao.setId(response.path("id"));

		//DELETA MASSA DO BANCO DE DADOS
		Response responseDELETE = service.deleteSimulacao(SIMULACOES, simulacao.getId());
		
		assertThat(responseDELETE.statusCode(), is(200)); 
		assertThat(responseDELETE.statusCode(), is(not(404))); 
		assertThat(responseDELETE,is(not(nullValue())));
		assertThat(responseDELETE.body(),is(not(nullValue())));
		assertThat(responseDELETE.header("Content-Type"),equalTo("application/json;charset=UTF-8"));
		assertThat(responseDELETE.header("Content-Length"),equalTo("2"));
		assertThat(responseDELETE.getBody().asString(),equalTo("OK"));
		
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("DELETE")
	@Description("CTU2R12_001 Teste tentando realizar um DELETE em uma simulação pelo CPF, Nome, email ou outro dado do cadastro")
	public void deveNaoDeletarSimulacaoPorNomeOuEmail() {
		//CTU2R12_001 Teste tentando realizar um DELETE em uma simulação pelo CPF, Nome, email ou outro dado do cadastro
		
		Random random = new Random();
		
		//criando simulação
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		
		
		
		int randomNumber = random.nextInt(2);
		String[] listaTestDELETE = {simulacao.getNome(), simulacao.getEmail()};
		
		String dados = listaTestDELETE[randomNumber];
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		simulacao.setId(response.path("id"));
		
		String endpoint = SIMULACOES +"/"+ dados;

		//DELETA MASSA DO BANCO DE DADOS
		Response responseDELETE = rest.delete(endpoint);
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(responseDELETE.statusCode(), is(400)); 
		assertThat(responseDELETE.statusCode(), is(not(200))); 
		assertThat(responseDELETE,is(not(nullValue())));
		assertThat(responseDELETE.body(),is(not(nullValue())));
		assertThat(responseDELETE.header("Connection"),equalTo("close"));
		assertThat(responseDELETE.header("Content-Length"),equalTo("0"));
		assertThat(responseDELETE.getBody().asString(),is(""));
		
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("DELETE")
	@Description("CTU2R12_001 Teste tentando realizar um DELETE em uma simulação pelo CPF - MELHORIA")
	public void deveNaoDeletarSimulacaoPeloCPF() { //f
		//CTU2R12_001 Teste tentando realizar um DELETE em uma simulação pelo CPF
		
		Random random = new Random();
		
		//criando simulação
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		simulacao.setId(response.path("id"));
		
		String endpoint = SIMULACOES +"/"+ simulacao.getCpf();

		//DELETA MASSA DO BANCO DE DADOS
		Response responseDELETE = rest.delete(endpoint);
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(responseDELETE.statusCode(), is(400)); 
		assertThat(responseDELETE.statusCode(), is(not(200))); 
		assertThat(responseDELETE,is(not(nullValue())));
		assertThat(responseDELETE.body(),is(not(nullValue())));
		assertThat(responseDELETE.header("Connection"),equalTo("close"));
		assertThat(responseDELETE.header("Content-Length"),equalTo("0"));
		assertThat(responseDELETE.getBody().asString(),is(""));
		
		}

	@Test
	@Epic("Simulacoes")
	@Tag("DELETE")
	@Description("CTU2R12_005 Teste tentando realizar um DELETE em uma simulação sem passar um valor e esperar que o banco de dados não seja deletado")
	public void deveNaoDeletarTodasAsSimulacaoAoNaoPassarUmId() { 
		//CTU2R12_005 Teste tentando realizar um DELETE em uma simulação sem passar um valor e esperar que o banco de dados não seja deletado
		

		//DELETA MASSA DO BANCO DE DADOS
		Response responseDELETE = rest.delete(SIMULACOES);
		
		assertThat(responseDELETE.statusCode(), is(405)); 
		assertThat(responseDELETE.statusCode(), is(not(200))); 
		assertThat(responseDELETE,is(not(nullValue())));
		assertThat(responseDELETE.body(),is(not(nullValue())));
		assertThat(responseDELETE.header("Allow"),equalTo("GET,POST"));
		assertThat(responseDELETE.header("Content-Length"),equalTo("0"));
		assertThat(responseDELETE.getBody().asString(),is(""));
		
		}
}
