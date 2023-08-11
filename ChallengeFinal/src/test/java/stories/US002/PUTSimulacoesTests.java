package stories.US002;

import static constants.Endpoints.SIMULACOES;
import static helper.ServiceHelper.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.CoreMatchers.*;

import java.lang.Float;
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

public class PUTSimulacoesTests extends BaseTest{
	
	private VerbosService verbosService = new VerbosService();
	
	@Test
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("PUT")
	@Description("CTU2R8_002 Teste realizando um POST de uma simulação, tentando realizar outro POST no mesmo CPF, realizar um DELETE, e então realizar outro POST")
	public void deveAlterarOsValoresComPUT() { // f
		// CTU2R8_002 Teste realizando um POST de uma simulação, tentando realizar outro POST no mesmo CPF, realizar um DELETE, e então realizar outro POST
		
		//criando simulação
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		Simulacao simulacaoPUT = DynamicFactory.generateRandomSimulacaos();
		Response responsePUT = service.putSimulacao(cpf, simulacaoPUT );

		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
			 
		float valorPutReq = Float.valueOf(simulacaoPUT.getValor());
		float valorPutRes = responsePUT.path("valor");
		
		assertThat(responsePUT.statusCode(), is(200)); 
		assertThat(responsePUT,is(not(nullValue())));
		assertThat(responsePUT.body(),is(not(nullValue())));
		assertThat(responsePUT.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responsePUT.path("nome"), allOf(equalTo(simulacaoPUT.getNome()), is(not(simulacao.getNome()))));
		assertThat(responsePUT.path("cpf"), allOf(equalTo(simulacaoPUT.getCpf())), is(not(simulacao.getCpf())));
		assertThat(responsePUT.path("email"), allOf(equalTo(simulacaoPUT.getEmail())), is(not(simulacao.getEmail())));
		assertThat(valorPutRes,allOf( equalTo(valorPutReq),is(not(nullValue()))));
		assertThat(responsePUT.path("parcelas"),allOf(equalTo(simulacaoPUT.getParcelas()),is(not(nullValue()))));
		assertThat(responsePUT.path("seguro"),allOf(equalTo(simulacaoPUT.isSeguro()),is(not(nullValue()))));
		assertThat(responsePUT.asString(),matchesJsonSchema("simulacoes","put", 200));
		
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("PUT")
	@Description("CTU2R1_004 Teste tentando realizar um PUT em uma simulação que tem um CPF válido e tentar alterar ele para um CPF com restrição")
	public void deveNaoMudarCPFParaCPFComRestricaoComPUT() { //f
		// CTU2R1_004 Teste tentando realizar um PUT em uma simulação que tem um CPF válido e tentar alterar ele para um CPF com restrição
		
		//criando simulação
		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		String cpfPUT = FakeCPF.gerarCPFInvalido();
		Simulacao simulacaoPUT = DynamicFactory.generateRandomSimulacaosStr(cpfPUT);
		Response responsePUT = service.putSimulacao(cpf, simulacaoPUT );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(responsePUT.statusCode(), is(404)); 
		assertThat(responsePUT.statusCode(),is(not(200)));
		assertThat(responsePUT,is(not(nullValue())));
		assertThat(responsePUT.body(),is(not(nullValue())));
		assertThat(responsePUT.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responsePUT.path("mensagem"), equalTo("O CPF "+ cpfPUT +" possui restrição"));
		assertThat(responsePUT.asString(),matchesJsonSchema("simulacoes","put", 404));
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("PUT")
	@Description("CTU2R2_005 Teste tentando realizar um PUT de CPF válido para o formato 999.999.999-99 e esperar um erro\r\n"
			+ "		 CTU2R9_001 Teste tentando realizar um POST e PUT com erros no body e esperar uma mensagem de erro")
	public void deveNaoMudarCPFParaCPFIFormatado() { //f
		// CTU2R2_005 Teste tentando realizar um PUT de CPF válido para o formato 999.999.999-99 e esperar um erro
		// CTU2R9_001 Teste tentando realizar um POST e PUT com erros no body e esperar uma mensagem de erro

		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		String cpfPUT = v.getCpfValidoFormatado();
		Simulacao simulacaoPUT = DynamicFactory.generateRandomSimulacaosStr(cpfPUT);
		Response responsePUT = service.putSimulacao(cpf, simulacaoPUT );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(responsePUT.statusCode(), is(400)); 
		assertThat(responsePUT.statusCode(),is(not(200)));
		assertThat(responsePUT,is(not(nullValue())));
		assertThat(responsePUT.body(),is(not(nullValue())));
		assertThat(responsePUT.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responsePUT.path("erros.cpf"), is(not(nullValue())));
		assertThat(responsePUT.asString(),matchesJsonSchema("simulacoes","put", 400));
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("PUT")
	@Description("CTU2R2_010 Teste tentando realizar um PUT de CPF valido o passando letras e outros caracteres esperar um erro")
	public void deveNaoMudarCPFParaCPFInvalido() { //f
		// CTU2R2_010 Teste tentando realizar um PUT de CPF valido o passando letras e outros caracteres esperar um erro

		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		String cpfPUT = v.getLetrasString();
		Simulacao simulacaoPUT = DynamicFactory.generateRandomSimulacaosStr(cpfPUT);
		Response responsePUT = service.putSimulacao(cpf, simulacaoPUT );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(responsePUT.statusCode(), is(400)); 
		assertThat(responsePUT.statusCode(),is(not(200)));
		assertThat(responsePUT,is(not(nullValue())));
		assertThat(responsePUT.body(),is(not(nullValue())));
		assertThat(responsePUT.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responsePUT.path("erros.cpf"), is(not(nullValue())));
		assertThat(responsePUT.asString(),matchesJsonSchema("simulacoes","put", 400));
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("PUT")
	@Description("CTU2R3_008 Teste tentando realizar um PUT com nome invalido")
	public void deveNaoMudarNomeParaNomeInvalido() { //f
		// CTU2R3_008 Teste tentando realizar um PUT com nome invalido

		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		String cpfPUT = FakeCPF.gerarCPFValido();
		Simulacao simulacaoPUT = DynamicFactory.generateRandomSimulacaosDadosErrados(cpfPUT);
		Response responsePUT = service.putSimulacao(cpf, simulacaoPUT );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(responsePUT.statusCode(), is(400)); 
		assertThat(responsePUT.statusCode(),is(not(200)));
		assertThat(responsePUT,is(not(nullValue())));
		assertThat(responsePUT.body(),is(not(nullValue())));
		assertThat(responsePUT.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responsePUT.path("erros.nome"), is(not(nullValue())));
		assertThat(responsePUT.asString(),matchesJsonSchema("simulacoes","put", 400));
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("PUT")
	@Description("CTU2R4_008 Teste tentando realizar um PUT com email invalido ")
	public void deveNaoMudarEmailParaEmailInvalido() { 
		// CTU2R4_008 Teste tentando realizar um PUT com email invalido 

		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		String cpfPUT = FakeCPF.gerarCPFValido();
		Simulacao simulacaoPUT = DynamicFactory.generateRandomSimulacaosDadosErradosEmail(cpfPUT);
		Response responsePUT = service.putSimulacao(cpf, simulacaoPUT );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(responsePUT.statusCode(), is(400)); 
		assertThat(responsePUT.statusCode(),is(not(200)));
		assertThat(responsePUT,is(not(nullValue())));
		assertThat(responsePUT.body(),is(not(nullValue())));
		assertThat(responsePUT.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responsePUT.path("erros"), is(not(nullValue())));
		assertThat(responsePUT.path("erros.email"), is(not(nullValue())));
		assertThat(responsePUT.path("erros.email"), anyOf(equalTo("não é um endereço de e-mail"), equalTo("E-mail deve ser um e-mail válido")));
		assertThat(responsePUT.asString(),matchesJsonSchema("simulacoes","put", 400));
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("PUT")
	@Description("CTU2R6_005 Teste tentando realizar um PUT com o campo valor sendo menor 1000 ou maior 40000")
	public void deveNaoMudarValorParaValorInvalidoMenorQue1000() { //f
		// CTU2R6_005 Teste tentando realizar um PUT com o campo valor sendo menor 1000 ou maior 40000  
		
		Random random = new Random();

		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		int valor = random.nextInt(-999,999);
		
		String cpfPUT = FakeCPF.gerarCPFValido();
		Simulacao simulacaoPUT = DynamicFactory.generateRandomSimulacaosDadosErradosValor(cpfPUT, valor);
		Response responsePUT = service.putSimulacao(cpf, simulacaoPUT );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(responsePUT.statusCode(), is(400)); 
		assertThat(responsePUT.statusCode(),is(not(200)));
		assertThat(responsePUT,is(not(nullValue())));
		assertThat(responsePUT.body(),is(not(nullValue())));
		assertThat(responsePUT.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responsePUT.path("erros"), is(not(nullValue())));
		assertThat(responsePUT.path("erros.valor"), is(not(nullValue())));
		assertThat(responsePUT.asString(),matchesJsonSchema("simulacoes","put", 400));
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("PUT")
	@Description("CTU2R6_005 Teste tentando realizar um PUT com o campo valor sendo menor 1000 ou maior 40000 ")
	public void deveNaoMudarValorParaValorInvalidoMaiorQue40000() { //f
		// CTU2R6_005 Teste tentando realizar um PUT com o campo valor sendo menor 1000 ou maior 40000  
		
		Random random = new Random();

		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		int valor = random.nextInt(40000,99999);
		
		String cpfPUT = FakeCPF.gerarCPFValido();
		Simulacao simulacaoPUT = DynamicFactory.generateRandomSimulacaosDadosErradosValor(cpfPUT, valor);
		Response responsePUT = service.putSimulacao(cpf, simulacaoPUT );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(responsePUT.statusCode(), is(400)); 
		assertThat(responsePUT.statusCode(),is(not(200)));
		assertThat(responsePUT,is(not(nullValue())));
		assertThat(responsePUT.body(),is(not(nullValue())));
		assertThat(responsePUT.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responsePUT.path("erros"), is(not(nullValue())));
		assertThat(responsePUT.path("erros.valor"), is(not(nullValue())));
		assertThat(responsePUT.asString(),matchesJsonSchema("simulacoes","put", 400));
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("PUT")
	@Description("CTU2R6_005 Teste tentando realizar um PUT com o campo parcelas sendo menor 2 ou maior 48")
	public void deveNaoMudarParcelasParaParcelasInvalidoMenorQue2() { 
		// CTU2R6_005 Teste tentando realizar um PUT com o campo parcelas sendo menor 2 ou maior 48  
		
		Random random = new Random();

		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		int parcelas = random.nextInt(1);
		
		String cpfPUT = FakeCPF.gerarCPFValido();
		Simulacao simulacaoPUT = DynamicFactory.generateRandomSimulacaosDadosErradosParcelas(cpfPUT, parcelas);
		Response responsePUT = service.putSimulacao(cpf, simulacaoPUT );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(responsePUT.statusCode(), is(400)); 
		assertThat(responsePUT.statusCode(),is(not(200)));
		assertThat(responsePUT,is(not(nullValue())));
		assertThat(responsePUT.body(),is(not(nullValue())));
		assertThat(responsePUT.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responsePUT.path("erros"), is(not(nullValue())));
		assertThat(responsePUT.path("erros.parcelas"), is(not(nullValue())));
		assertThat(responsePUT.path("erros.parcelas"), equalTo("Parcelas deve ser igual ou maior que 2"));
		assertThat(responsePUT.asString(),matchesJsonSchema("simulacoes","put", 400));
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("Bug")
	@Tag("PUT")
	@Description("CTU2R6_005 Teste tentando realizar um PUT com o campo parcelas sendo menor 2 ou maior 48 ")
	public void deveNaoMudarParcelasParaParcelasInvalidoMaiorQue48() { //f
		// CTU2R6_005 Teste tentando realizar um PUT com o campo parcelas sendo menor 2 ou maior 48  
		
		Random random = new Random();

		Simulacao simulacao = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacao.getCpf();
		
		Response response = rest.post(SIMULACOES, simulacao );
		
		int parcelas = random.nextInt(49,999);
		
		String cpfPUT = FakeCPF.gerarCPFValido();
		Simulacao simulacaoPUT = DynamicFactory.generateRandomSimulacaosDadosErradosParcelas(cpfPUT, parcelas);
		Response responsePUT = service.putSimulacao(cpf, simulacaoPUT );
		
		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(response, simulacao);
		
		assertThat(responsePUT.statusCode(), is(400)); 
		assertThat(responsePUT.statusCode(),is(not(200)));
		assertThat(responsePUT,is(not(nullValue())));
		assertThat(responsePUT.body(),is(not(nullValue())));
		assertThat(responsePUT.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responsePUT.path("erros"), is(not(nullValue())));
		assertThat(responsePUT.path("erros.parcelas"), is(not(nullValue())));
		assertThat(responsePUT.asString(),matchesJsonSchema("simulacoes","put", 400));
		}
	
	@Test
	@Epic("Simulacoes")
	@Tag("PUT")
	@Description("CTU2R10_001 Teste tentando realizar um PUT em um CPF que não tem simulações cadastradas e esperar um erro")
	public void deveNaoCadastrarUsuarioComPUT() { 
		// CTU2R10_001 Teste tentando realizar um PUT em um CPF que não tem simulações cadastradas e esperar um erro
		
		//criando simulação
		
		Simulacao simulacaoPUT = DynamicFactory.generateRandomSimulacaos();
		String cpf = simulacaoPUT.getCpf();
		Response responsePUT = service.putSimulacao(cpf, simulacaoPUT );

		//DELETA MASSA DO BANCO DE DADOS
		verbosService.deleteMassa(responsePUT, simulacaoPUT);
			 
		
		
		assertThat(responsePUT.statusCode(), is(404)); 
		assertThat(responsePUT.statusCode(), is(not(201))); 
		assertThat(responsePUT,is(not(nullValue())));
		assertThat(responsePUT.body(),is(not(nullValue())));
		assertThat(responsePUT.header("Transfer-Encoding"),equalTo("chunked"));
		assertThat(responsePUT.path("mensagem"),  equalTo("CPF "+ cpf +" não encontrado"));	
		assertThat(responsePUT.asString(),matchesJsonSchema("simulacoes","put", 404));
		}
}