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
import datafactory.FakeCPF;
import helper.BaseTest;
import helper.VariaveisUteis;
import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

public class GETSimulacoesTests extends BaseTest {
	
	Random random = new Random();
	
	@Test
	public void deveDarGETEmCPFComRestricao() { //f
		
		// CTU2R1_002 Teste tentando realizar um GET informando um CPF com restrição e esperar um erro
		
		String cpfInvalido = FakeCPF.gerarCPFInvalido();
		
		Response response = rest.get(SIMULACOES, cpfInvalido);
		assertThat(response.statusCode(), is(404));
		assertThat(response.statusCode(), is(not(200)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.asString(),matchesJsonSchema("simulacoes","get", 404));
		assertThat(response.path("mensagem"), equalTo("O CPF "+ cpfInvalido +" possui restrição"));
		
	}
	
	@Test
	public void deveDarGETSemPassarCPFCorreto() { //f
		// CTU1R1_006 Teste consultando um CPF em padrões fora do esperado (letras, caracteres inválidos, query entre outros)
		
		
		int randomNumber = random.nextInt(4);
		String[] listaTest6 = v.getListaTest6();
		
		String cpf = listaTest6[randomNumber];
		
		Response response = rest.get(SIMULACOES, listaTest6[randomNumber]);
		assertThat(response.statusCode(), is(404));
		assertThat(response.statusCode(), is(not(200)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.path("mensagem"), is(not(nullValue())));
		assertThat(listaTest6[randomNumber], equalTo(cpf));
		assertThat(response.asString(),matchesJsonSchema("simulacoes","get", 404));
		assertThat(response.path("error"), equalTo("Not Found"));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		}
	
	@Test
	public void deveDarGETEmCPFSemRestricao() {
		
		
		String cpfValido = FakeCPF.gerarCPFValido();
		
		Response response = rest.get(SIMULACOES, cpfValido);
		assertThat(response.statusCode(), is(404));
		assertThat(response.statusCode(), is(not(200)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.asString(),matchesJsonSchema("simulacoes","get", 404));
		assertThat(response.path("mensagem"), equalTo("CPF "+ cpfValido +" não encontrado"));
		
	}
	
	@Test
	public void deveRetornarErroEmCPFComRestricoesEFormatado() { //f
		
		// CTU2R2_002 Teste tentando realizar um GET de CPF usando o formato 999.999.999-99 e esperar um erro
		
		String cpfInvalido = v.getCpfInvalidoFormatado();
		
		Response response = rest.get(SIMULACOES, cpfInvalido);
		assertThat(response.statusCode(), is(404));
		assertThat(response.statusCode(), is(not(200)));
		assertThat(response, is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.asString(),matchesJsonSchema("simulacoes","get", 404));
		assertThat(response.path("error"), equalTo("Not Found"));
		
	}
	
//	@Test
//	public void deveGETEmBancoDeDadosVazio() { //f
//		
//		// CTU2R11_001 Caso não existam simulações cadastradas ao realizar um GET em /simulacoes um status code 204 deve ser retornado
//		
//		String cpfInvalido = v.getCpfInvalidoFormatado();
//		
//		Response response = rest.get(SIMULACOES, cpfInvalido);
//		assertThat(response.statusCode(), is(204));
//		assertThat(response.statusCode(), is(not(200)));
//		assertThat(response.statusCode(), is(not(404)));
//		
//	}

}
