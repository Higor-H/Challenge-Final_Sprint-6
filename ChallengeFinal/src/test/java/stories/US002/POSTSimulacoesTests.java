package stories.US002;


import static constants.Endpoints.SIMULACOES;
import static helper.ServiceHelper.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.CoreMatchers.*;

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

public class POSTSimulacoesTests  extends BaseTest{
	
	private VerbosService verbosService = new VerbosService();

	@Test
	@Epic("Simulacoes")
	@Tag("POST")
	@Description("CTU2R2_006 Teste tentando realizar um POST de usando o formato 99999999999 e esperar um sucesso\r\n"
			+ "		CTU2R4_001 Teste tentando realizar um POST com um email válido (xx.xx@xx.xx)\r\n"
			+ "		CTU2R5_003 Teste tentando realizar um POST com o campo valor sendo maior que 999 e menor que 40001\r\n"
			+ "		CTU2R6_003 Teste tentando realizar um POST com o campo parcelas sendo maior que 1 e menor que 49")
	public void deveCadastrarSimulacao() {
		
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
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
		
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("POST")
	@Description("CTU2R1_001 Teste tentando realizar um POST informando um CPF com restrição e esperar um erro")
	public void deveReceberErroAoRealizarPOSTComCPFComRestricao() { //f
		// CTU2R1_001 Teste tentando realizar um POST informando um CPF com restrição e esperar um erro
		
		
		String cpf = FakeCPF.gerarCPFInvalido();
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaosStr(cpf);
		
		
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(response.statusCode(), is(409));
		assertThat(response.statusCode(), is(not(201)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.path("mensagem"), equalTo("O CPF "+cpf+" possui restrição"));
	
		assertThat(response.asString(),matchesJsonSchema("simulacoes","post", 409));
		
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("POST")
	@Description("CTU2R2_002 Teste tentando realizar um GET de CPF usando o formato 999.999.999-99 e esperar um erro")
	public void deveReceberErroAoRealizarPOSTComCPFFormatadoComRestricao() { //f
		// CTU2R2_002 Teste tentando realizar um GET de CPF usando o formato 999.999.999-99 e esperar um erro
		
		
		String cpf = v.getCpfInvalidoFormatado();
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaosStr(cpf);
		
		Response response = rest.post(SIMULACOES, simulacao );
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
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
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("POST")
	@Description("CTU2R2_009 Teste tentando realizar um POST de usando o passando letras e outros caracteres esperar um erro\r\n"
			+ "	CTU2R9_001 Teste tentando realizar um POST e PUT com erros no body e esperar uma mensagem de erro")
	public void deveReceberErroAoRealizarPOSTSemPassarUmCPF() { //f
		// CTU2R2_009 Teste tentando realizar um POST de usando o passando letras e outros caracteres esperar um erro
		//CTU2R9_001 Teste tentando realizar um POST e PUT com erros no body e esperar uma mensagem de erro
		
		
		String cpf = v.getLetrasString();
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaosStr(cpf);
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		
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
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("POST")
	@Description("CTU2R3_001 Teste tentando realizar um POST com o campo nome sendo um caractere em branco \r\n"
			+ "		CTU2R3_003 Teste tentando realizar um POST com o campo nome sendo um caracteres invalido\r\n"
			+ "		CTU2R3_004 Teste tentando realizar um POST com o campo nome sendo um apenas números\r\n"
			+ "		CTU2R3_006 Teste tentando realizar um POST com um nome muito longo\r\n"
			+ "		CTU2R3_007 Teste tentando realizar um POST com um nome sendo apenas 1 caractere")
	public void deveReceberErroAoRealizarPOSTComdadosErradosNome() { //f
		
		Random random = new Random();
		
		int randomNumber = random.nextInt(4);
		String[] listaTest6 = v.getListaTest6();
		
		String cpf = listaTest6[randomNumber];
		
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaosDadosErrados(cpf);
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
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
	@Epic("Simulacoes")
	@Tag("POST")
	@Description("CTU2R4_002 Teste tentando realizar um POST com o campo email sendo um caractere em branco\r\n"
			+ "		CTU2R4_004 Teste tentando realizar um POST com o campo email sendo um caracteres invalido\r\n"
			+ "		CTU2R4_006 Teste tentando realizar um POST com email com excesso de caracteres aceitos\r\n"
			+ "		CTU2R4_008 Teste tentando realizar um PUT com email invalido")
	public void deveReceberErroAoRealizarPOSTComdadosErradosEmail() { 
		
		String cpf = datafactory.FakeCPF.gerarCPFValido();
		
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaosDadosErradosEmail(cpf);
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
				
		
		assertThat(response.statusCode(), is(400));
		assertThat(response.statusCode(), is(not(201)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.path("erros"), is(not(nullValue())));
		assertThat(response.path("erros.email"), is(not(nullValue())));
		assertThat(response.path("erros.email"), anyOf(equalTo("não é um endereço de e-mail"), equalTo("E-mail deve ser um e-mail válido")));
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("POST")
	@Description("CTU2R5_001 Teste tentando realizar um POST com o campo valor sendo menor que 1000")
	public void deveReceberErroAoRealizarPOSTComdadosErradosValorMin1000() { //f
		
		Random random = new Random();
		
		String cpf = datafactory.FakeCPF.gerarCPFValido();
		
		int valor = random.nextInt(999);
		
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaosDadosErradosValor(cpf, valor);
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(response.statusCode(), is(400));
		assertThat(response.statusCode(), is(not(201)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.path("erros"), is(not(nullValue())));
		assertThat(response.path("erros.valor"), is(not(nullValue())));
	
		assertThat(response.asString(),matchesJsonSchema("simulacoes","post", 400));
		
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("POST")
	@Description("CTU2R5_002 Teste tentando realizar um POST com o campo valor sendo maior que 40000")
	public void deveReceberErroAoRealizarPOSTComdadosErradosValorMax40000() { 
		
		Random random = new Random();
		
		String cpf = datafactory.FakeCPF.gerarCPFValido();
		
		int valor = random.nextInt(40001, 99999);
		
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaosDadosErradosValor(cpf, valor);
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(response.statusCode(), is(400));
		assertThat(response.statusCode(), is(not(201)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.path("erros"), is(not(nullValue())));
		assertThat(response.path("erros.valor"), is(not(nullValue())));
		assertThat(response.path("erros.valor"), equalTo("Valor deve ser menor ou igual a R$ 40.000"));
	
		assertThat(response.asString(),matchesJsonSchema("simulacoes","post", 400));
		
		}
	@Test
	@Epic("Simulacoes")
	@Tag("POST")
	@Description(" CTU2R6_001 Teste tentando realizar um POST com o campo parcelas sendo menor que 2\r\n"
			+ "	 CTU2R6_002 Teste tentando realizar um POST com o campo parcelas sendo maior que 48")
	public void deveReceberErroAoRealizarPOSTComdadosErradosParcelasMin2() { 
		
		
		Random random = new Random();
		
		String cpf = datafactory.FakeCPF.gerarCPFValido();
		
		int parcelas = random.nextInt(1);
		
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaosDadosErradosParcelas(cpf, parcelas);
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(response.statusCode(), is(400));
		assertThat(response.statusCode(), is(not(201)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.path("erros"), is(not(nullValue())));
		assertThat(response.path("erros.parcelas"), is(not(nullValue())));
		assertThat(response.path("erros.parcelas"), equalTo("Parcelas deve ser igual ou maior que 2"));
	
		assertThat(response.asString(),matchesJsonSchema("simulacoes","post", 400));
		
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("POST")
	@Description("CTU2R6_002 Teste tentando realizar um POST com o campo parcelas sendo maior que 48")
	public void deveReceberErroAoRealizarPOSTComdadosErradosParcelasMax48() { //f
		
		
		Random random = new Random();
		
		String cpf = datafactory.FakeCPF.gerarCPFValido();
		
		int parcelas = random.nextInt(49,1000);
		
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaosDadosErradosParcelas(cpf, parcelas);
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(response.statusCode(), is(400));
		assertThat(response.statusCode(), is(not(201)));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
		assertThat(response.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(response.path("erros"), is(not(nullValue())));
		assertThat(response.path("erros.parcelas"), is(not(nullValue())));
	
		assertThat(response.asString(),matchesJsonSchema("simulacoes","post", 400));
		
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("POST")
	@Description("CTU2R8_001 Teste tentando realizar um POST passando um CPF já utilizado")
	public void deveNaoCadastrarSimulacaoRepetida() {
		// CTU2R8_001 Teste tentando realizar um POST passando um CPF já utilizado
		
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		
		Response response = rest.post(SIMULACOES, simulacao );
		int id = response.path("id");
		
		
		Simulacao simulacaoRepetido = DynamicFactory.generateRandomSimulacaos();
		simulacaoRepetido.setCpf(cpf);
		Response responseRepetido = rest.post(SIMULACOES, simulacaoRepetido );
		
		
		
		assertThat(responseRepetido.statusCode(), is(400));
		assertThat(responseRepetido.statusCode(), is(not(201)));
		assertThat(responseRepetido,is(not(nullValue())));
		assertThat(responseRepetido.body(),is(not(nullValue())));
		assertThat(responseRepetido.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responseRepetido.path("mensagem"), equalTo("CPF duplicado"));
		assertThat(responseRepetido.asString(),matchesJsonSchema("simulacoes","postVariacoes", 400));
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(responseRepetido, simulacaoRepetido);
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("POST")
	@Description("CTU2R8_002 Teste realizando um POST de uma simulação, tentando realizar outro POST no mesmo CPF, realizar um DELETE, e então realizar outro POST")
	public void deveCadastrarSimulacaoRepetidaECadastrarDenovo1() {
		// CTU2R8_002 Teste realizando um POST de uma simulação, tentando realizar outro POST no mesmo CPF, realizar um DELETE, e então realizar outro POST
		
		//criando simulação
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		
		Response response = rest.post(SIMULACOES, simulacao );
		int id = response.path("id");
		
		//criando simulação Repetida
		Simulacao simulacaoRepetido = DynamicFactory.generateRandomSimulacaos();
		simulacaoRepetido.setCpf(cpf);
		Response responseRepetido = rest.post(SIMULACOES, simulacaoRepetido );
		assertThat(responseRepetido.statusCode(), is(400));
		
		//excluindo simulação 
		Response responseDelete = service.deleteSimulacao(SIMULACOES, id );
		assertThat(responseDelete.statusCode(), is(200));
		
		//criando simulação 
		response = rest.post(SIMULACOES, simulacao );
		id = response.path("id");
		
		//criando simulação Repetida
		responseRepetido = rest.post(SIMULACOES, simulacaoRepetido );
		assertThat(responseRepetido.statusCode(), is(400));
		assertThat(responseRepetido.statusCode(), is(not(201)));
		assertThat(responseRepetido,is(not(nullValue())));
		assertThat(responseRepetido.body(),is(not(nullValue())));
		assertThat(responseRepetido.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responseRepetido.path("mensagem"), equalTo("CPF duplicado"));
		assertThat(responseRepetido.asString(),matchesJsonSchema("simulacoes","postVariacoes", 400));
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(responseRepetido, simulacaoRepetido);
		
		}
}