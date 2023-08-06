package stories.US001;

import static constants.Endpoints.RESTRICOES;
import static helper.ServiceHelper.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Random;
import org.junit.jupiter.api.Test;
import datafactory.FakeCPF;
import helper.BaseTest;
import helper.VariaveisUteis;
import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

public class GETRestricoesTests extends BaseTest {
	
	Random random = new Random();
	VariaveisUteis v = new VariaveisUteis();
	
	@Test
	public void deveDarGETEmCPFComRestricao() { //f
		// CTU1R1_001 Teste consultando um CPF com restrição e esperar uma mensagem e o CPF consultado afirmando que ele está com restrição
		// CTU1R1_003 Teste consultando um CPF com restrição e resposta deve conter um Json com apenas strings
		// CTU1R3_001 Teste consultando um CPF com restrição e esperar um status code 200
		// CTU1R4_002 Teste consultando um CPF com restrição e esperar que o status code não seja 204
		
		String cpfInvalido = FakeCPF.gerarCPFInvalido();
		
		Response response = rest.get(RESTRICOES, cpfInvalido);
		assertThat(response.statusCode(), is(200));
		assertThat(response.statusCode(), is(not(204)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.asString(),matchesJsonSchema("restricoes","get", 200));
		assertThat(response.path("mensagem"), equalTo("O CPF "+ cpfInvalido +" possui restrição"));
	}
	
	@Test
	public void deveDarGETEmCPFSemRestricao() {
		// CTU1R1_001 Teste consultando um CPF com restrição e esperar uma mensagem e o CPF consultado afirmando que ele está com restrição
		// CTU1R1_004 Teste consultando um CPF no padrão /99999999999
		// CTU1R3_002 Teste consultando um CPF sem restrição e esperar que o status code não seja 200
		// CTU1R4_001 Teste consultando um CPF sem restrição e esperar um status code 204
		
		String cpf = FakeCPF.gerarCPFValido();
		
		Response response = rest.get(RESTRICOES, cpf);
		assertThat(response.statusCode(), is(204));
		assertThat(response.statusCode(), is(not(200)));
		assertThat(response,is(not(nullValue())));
		}
	
	@Test
	public void deveDarGETEmCPFFormatadoSemRestricao() {
		// CTU1R1_005 Teste consultando um CPF no padrão /999.999.999-99
		
		String cpf = v.getCpfValidoFormatado() ;
		
		Response response = rest.get(RESTRICOES, cpf);
		assertThat(response.statusCode(), is(204));
		assertThat(response,is(not(nullValue())));
		assertThat(cpf, equalTo(v.getCpfValidoFormatado()));
		
		}
	
	@Test
	public void deveDarGETEmCPFFormatadoComRestricao() { //f
		// CTU1R1_005 Teste consultando um CPF no padrão /999.999.999-99
		
		String cpf = v.getCpfInvalidoFormatado() ;
		
		Response response = rest.get(RESTRICOES, cpf);
		assertThat(response.statusCode(), is(200));
		assertThat(response.statusCode(), is(not(204)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.path("mensagem"), equalTo("O CPF "+ cpf +" possui restrição"));
		assertThat(cpf, equalTo(v.getCpfInvalidoFormatado()));
		assertThat(response.asString(),matchesJsonSchema("restricoes","get", 200));
		
		}
		
	@Test
	public void deveDarGETSemPassarCPFCorreto() { //f
		// CTU1R1_006 Teste consultando um CPF em padrões fora do esperado (letras, caracteres inválidos, query entre outros)
		
		
		int randomNumber = random.nextInt(6);
		String[] listaTest4 = v.getListaTest4();
		
		String cpf = listaTest4[randomNumber];
		
		Response response = rest.get(RESTRICOES, listaTest4[randomNumber]);
		assertThat(response.statusCode(), is(404));
		assertThat(response.statusCode(), is(not(204)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.path("mensagem"), is(not(nullValue())));
		assertThat(listaTest4[randomNumber], equalTo(cpf));
		assertThat(response.asString(),matchesJsonSchema("restricoes","get", 404));
		}
	
	@Test
	public void deveDarGETEEsperarErro() { //f
		// CTU1R2_001 Teste consultando a rota /restricoes sem informar um cpf e esperar um erro
		
		Response response = rest.get(RESTRICOES, v.getNada());
		assertThat(response.statusCode(), is(404));
		assertThat(response.statusCode(), is(not(200)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.path("mensagem"), is(nullValue()));
		assertThat(response.path("message"), equalTo("No message available"));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.asString(),matchesJsonSchema("restricoes","get", 404));

		}
	
	@Test
	public void deveTestarQuaisVerbosSãoPossiveis() {
		
		// CTU1R5_001 Teste consultando um CPF com restrição via POST
		// CTU1R5_002 Teste consultando um CPF sem restrição via POST
		// CTU1R5_003 Teste consultando um CPF com restrição via PUT
		// CTU1R5_004 Teste consultando um CPF sem restrição via PUT
		// CTU1R5_005 Teste consultando um CPF com restrição via DELETE
		// CTU1R5_006 Teste consultando um CPF sem restrição via PATCH
		// CTU1R5_007 Teste consultando um CPF com restrição via PATCH
	
		
		int randomNumber = random.nextInt(2);
		String[] listaTest5 = v.getListaTest5();
		
		String cpf = listaTest5[randomNumber];
		
		
		Response responseOpitions = rest.options(RESTRICOES +"/"+cpf);
		assertThat(responseOpitions.statusCode(), is(200));
		assertThat(responseOpitions,is(not(nullValue())));
		assertThat(responseOpitions.header("Allow"), equalTo("GET,HEAD,OPTIONS"));
		
		Response responseHeadV = rest.head(RESTRICOES +"/"+v.getCpfValido());
		assertThat(responseHeadV.statusCode(), is(204));
		assertThat(responseHeadV,is(not(nullValue())));
		assertThat(responseHeadV.header("Date"),is(not(nullValue())));
		
		Response responseHeadI = rest.head(RESTRICOES +"/"+v.getCpfInvalido());
		assertThat(responseHeadI.statusCode(), is(200));
		assertThat(responseHeadI,is(not(nullValue())));
		assertThat(responseHeadI.header("Content-Type"), equalTo("application/json;charset=UTF-8"));
		assertThat(responseHeadI.header("Content-Length"),equalTo("45"));
		assertThat(responseHeadI.header("Date"),is(not(nullValue())));
		
		
		Response responseGetV = rest.get(RESTRICOES, v.getCpfValido());
		assertThat(responseGetV.statusCode(), is(204));
		assertThat(responseGetV.statusCode(), is(not(200)));
		assertThat(responseGetV,is(not(nullValue())));
		
		Response responseGetI = rest.get(RESTRICOES, v.getCpfInvalido());
		assertThat(responseGetI.statusCode(), is(200));
		assertThat(responseGetI.statusCode(), is(not(204)));
		assertThat(responseGetI,is(not(nullValue())));
		assertThat(responseGetI.asString(),matchesJsonSchema("restricoes","get", 200));
		
		
		//Não deve ser possivel
		Response responsePost = rest.post(RESTRICOES +"/"+cpf,cpf);
		assertThat(responsePost.statusCode(), is(405));
		assertThat(responsePost.statusCode(), is(not(200)));
		assertThat(responsePost,is(not(nullValue())));
		
		Response responsePut = rest.put(RESTRICOES +"/"+cpf,cpf);
		assertThat(responsePut.statusCode(), is(405));
		assertThat(responsePut.statusCode(), is(not(200)));
		assertThat(responsePut,is(not(nullValue())));
		
		Response responseDelete = rest.delete(RESTRICOES +"/"+cpf);
		assertThat(responseDelete.statusCode(), is(405));
		assertThat(responseDelete.statusCode(), is(not(200)));
		assertThat(responseDelete,is(not(nullValue())));
		
		Response responsePath = rest.path(RESTRICOES +"/"+cpf,cpf);
		assertThat(responsePath.statusCode(), is(405));
		assertThat(responsePath.statusCode(), is(not(200)));
		assertThat(responsePath,is(not(nullValue())));
			
		
		}
}