package stories.US002;

import static constants.Endpoints.RESTRICOES;
import static constants.Endpoints.SIMULACOES;
import static helper.ServiceHelper.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Random;
import org.junit.jupiter.api.Test;

import datafactory.DynamicFactory;
import datafactory.FakeCPF;
import helper.BaseTest;
import helper.VariaveisUteis;
import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import model.Simulacao;
import services.VerbosService;

public class POSTSimulacoesTests  extends BaseTest{
	

	@Test
	public void deveCadastrarSimulacao() {
		// CTU2R2_006 Teste tentando realizar um POST de usando o formato 99999999999 e esperar um sucesso
		
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		int id = response.path("id");
		
		assertThat(response.statusCode(), is(201));
		assertThat(response.statusCode(), is(not(409)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.path("nome"), equalTo(simulacao.getNome()));
		assertThat(response.path("cpf"), equalTo(cpf));
		assertThat(response.path("email"), equalTo(simulacao.getEmail()));
		assertThat(response.path("valor"), equalTo(simulacao.getValor()));
		assertThat(response.path("parcelas"), equalTo(simulacao.getParcelas()));
		assertThat(response.path("seguro"), equalTo(simulacao.isSeguro()));
		assertThat(response.asString(),matchesJsonSchema("simulacoes","post", 201));
		
		//Deletando simulação
		Response responseDelete = service.deleteSimulacao(SIMULACOES, id );
		
		}
	
	@Test
	public void deveReceberErroAoRealizarPOSTComCPFComRestricao() { //f
		// CTU2R1_001 Teste tentando realizar um POST informando um CPF com restrição e esperar um erro
		
		
		String cpf = FakeCPF.gerarCPFInvalido();
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaosStr(cpf);
		
		
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		int id = response.path("id");
		
		assertThat(response.statusCode(), is(409));
		assertThat(response.statusCode(), is(not(201)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.path("mensagem"), equalTo("O CPF "+cpf+" possui restrição"));
	
		assertThat(response.asString(),matchesJsonSchema("simulacoes","post", 409));
		
		//Deletando simulação
		Response responseDelete = service.deleteSimulacao(SIMULACOES, id );
		
		}
	
	@Test
	public void deveReceberErroAoRealizarPOSTComCPFFormatadoComRestricao() { //f
		// CTU2R2_002 Teste tentando realizar um GET de CPF usando o formato 999.999.999-99 e esperar um erro
		
		
		String cpf = v.getCpfInvalidoFormatado();
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaosStr(cpf);
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		
		assertThat(response.statusCode(), is(400));
		assertThat(response.statusCode(), is(not(201)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.path("erros"), is(not(nullValue())));
		assertThat(response.path("erros.cpf"), is(not(nullValue())));
	
		assertThat(response.asString(),matchesJsonSchema("simulacoes","post", 400));
		
		}
	
	@Test
	public void deveReceberErroAoRealizarPOSTSemPassarUmCPF() { //f
		// CTU2R2_009 Teste tentando realizar um POST de usando o passando letras e outros caracteres esperar um erro
		
		
		String cpf = v.getLetrasString();
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaosStr(cpf);
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		
		assertThat(response.statusCode(), is(400));
		assertThat(response.statusCode(), is(not(201)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.path("erros"), is(not(nullValue())));
		assertThat(response.path("erros.cpf"), is(not(nullValue())));
	
		assertThat(response.asString(),matchesJsonSchema("simulacoes","post", 400));
		
		}
	
	@Test
	public void deveReceberErroAoRealizarPOSTComdadosErrados() { //f
		// CTU2R3_001 Teste tentando realizar um POST com o campo nome sendo um caractere em branco 
		// CTU2R3_003 Teste tentando realizar um POST com o campo nome sendo um caracteres invalido
		// CTU2R3_004 Teste tentando realizar um POST com o campo nome sendo um apenas números
		// CTU2R3_006 Teste tentando realizar um POST com um nome muito longo
		// CTU2R3_007 Teste tentando realizar um POST com um nome sendo apenas 1 caractere
		
		Random random = new Random();
		
		int randomNumber = random.nextInt(4);
		String[] listaTest6 = v.getListaTest6();
		
		String cpf = listaTest6[randomNumber];
		
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaosDadosErrados(cpf);
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		
		assertThat(response.statusCode(), is(400));
		assertThat(response.statusCode(), is(not(201)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.path("erros"), is(not(nullValue())));
		assertThat(response.path("erros.nome"), is(not(nullValue())));
		assertThat(response.path("erros.cpf"), is(not(nullValue())));
	
		assertThat(response.asString(),matchesJsonSchema("simulacoes","post", 400));
		
		}
	
	@Test
	public void deveReceberErroAoRealizarPOSTComdadosErrados() { //f
		// CTU2R3_001 Teste tentando realizar um POST com o campo nome sendo um caractere em branco 
		// CTU2R3_003 Teste tentando realizar um POST com o campo nome sendo um caracteres invalido
		// CTU2R3_004 Teste tentando realizar um POST com o campo nome sendo um apenas números
		// CTU2R3_006 Teste tentando realizar um POST com um nome muito longo
		// CTU2R3_007 Teste tentando realizar um POST com um nome sendo apenas 1 caractere
		
		Random random = new Random();
		
		int randomNumber = random.nextInt(4);
		String[] listaTest6 = v.getListaTest6();
		
		String cpf = listaTest6[randomNumber];
		
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaosDadosErrados(cpf);
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		
		assertThat(response.statusCode(), is(400));
		assertThat(response.statusCode(), is(not(201)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.path("erros"), is(not(nullValue())));
		assertThat(response.path("erros.nome"), is(not(nullValue())));
		assertThat(response.path("erros.cpf"), is(not(nullValue())));
	
		assertThat(response.asString(),matchesJsonSchema("simulacoes","post", 400));
		
		}
	
}
