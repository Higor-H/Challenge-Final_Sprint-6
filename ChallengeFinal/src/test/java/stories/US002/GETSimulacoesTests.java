package stories.US002;

import static constants.Endpoints.SIMULACOES;
import static helper.ServiceHelper.matchesJsonSchema;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Random;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import datafactory.DynamicFactory;
import datafactory.FakeCPF;
import helper.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.Simulacao;
import services.VerbosService;

public class GETSimulacoesTests extends BaseTest {
	
	Random random = new Random();
	private VerbosService verbosService = new VerbosService();
	
	@Test
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("GET")
	@Tag("SimulacoesGET")
	@Description("CTU2R1_002 Teste tentando realizar um GET informando um CPF com restrição e esperar um erro")
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
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("GET")
	@Tag("SimulacoesGET")
	@Description("CTU1R1_006 Teste consultando um CPF em padrões fora do esperado (letras, caracteres inválidos, query entre outros)")
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
	@Epic("Simulacoes")
	@Tag("GET")
	@Tag("SimulacoesGET")
	@Description("CTU2R11_001 Caso não existam simulações cadastradas ao realizar um GET em /simulacoes um status code 204 deve ser retornado")
	public void deveDarGETSemPassarUmCPF() {
		// CTU2R11_001 Caso não existam simulações cadastradas ao realizar um GET em /simulacoes um status code 204 deve ser retornado
		
		Response response = rest.get(SIMULACOES, v.getNada());
		
		if (response.body() == nullValue()) {
		
			assertThat(response.statusCode(), is(204));
			assertThat(response.statusCode(), is(not(400)));
			assertThat(response,is(not(nullValue())));
			assertThat(response.body(),is(nullValue()));
			assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		} else {
			
			assertThat(response.statusCode(), is(200));
			assertThat(response.statusCode(), is(not(400)));
			assertThat(response,is(not(nullValue())));
			assertThat(response.body(),is(not(nullValue())));
			assertThat(response.asString(),matchesJsonSchema("simulacoes","getVariacoes", 200));
			assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
			}
	}
	
	@Test
	@Epic("Simulacoes")
	@Tag("GET")
	@Tag("SimulacoesGET")
	public void deveDarGETEmSimulacaoCadastrada() {

		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		Response responsePost = rest.post(SIMULACOES, simulacao );
		
		
		Response responseGet = rest.get(SIMULACOES, simulacao.getCpf());
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(responsePost, simulacao);
		
		float valorPutReq = Float.valueOf(simulacao.getValor());
		float valorPutRes = responseGet.path("valor");
		
		assertThat(responseGet.statusCode(), is(200));
		assertThat(responseGet.statusCode(), is(not(400)));
		assertThat(responseGet,is(not(nullValue())));
		assertThat(responseGet.body(),is(not(nullValue())));
		assertThat(responseGet.asString(),matchesJsonSchema("simulacoes","get", 200));
		assertThat(responseGet.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responseGet.path("nome"), equalTo(simulacao.getNome()));
		assertThat(responseGet.path("cpf"), equalTo(cpf));
		assertThat(responseGet.path("email"), equalTo(simulacao.getEmail()));
		assertThat(valorPutRes,allOf( equalTo(valorPutReq),is(not(nullValue()))));
		assertThat(responseGet.path("parcelas"), equalTo(simulacao.getParcelas()));
		assertThat(responseGet.path("seguro"), equalTo(simulacao.isSeguro()));	
		
	}
	

	
	@Test
	@Epic("Simulacoes")
	@Tag("GET")
	@Tag("SimulacoesGET")
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
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("GET")
	@Tag("SimulacoesGET")
	@Description("CTU2R2_002 Teste tentando realizar um GET de CPF usando o formato 999.999.999-99 e esperar um erro")
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
}
