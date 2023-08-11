package stories.fluxos;

import static constants.Endpoints.RESTRICOES;
import static helper.ServiceHelper.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import datafactory.FakeCPF;
import helper.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;

public class FluxoRestricoesTests extends BaseTest{
	
	@Test
	@Epic("Restricoes")
	@Epic("Fluxos")
	@Tag("Fluxo")
	@Description("Fluxo passando por todos os verbos da rota Restricoes")
	public void deveRealizarFluxoNaRotaRestricoes() { //f
		
		
		//Consultando CPF Sem Restricao
		String cpfValido = FakeCPF.gerarCPFValido();
		Response responseGetRestricaoCPFValido = rest.get(RESTRICOES, cpfValido);
		
		//Consultando CPF Com Restricao
		String cpfInvalido = FakeCPF.gerarCPFInvalido();
		Response responseComRestricao = rest.get(RESTRICOES, cpfInvalido);
		
		
		//Asserts sem restricao
		assertThat(responseGetRestricaoCPFValido.statusCode(), is(204)); // Status code
		assertThat(responseGetRestricaoCPFValido.statusCode(), is(not(200)));
		assertThat(responseGetRestricaoCPFValido,is(not(nullValue()))); //Body
		//Asserts com restricao
		assertThat(responseComRestricao.statusCode(), is(200)); // Status code
		assertThat(responseComRestricao.statusCode(), is(not(204)));
		assertThat(responseComRestricao.header("Transfer-Encoding"),equalTo("chunked")); //Header
		assertThat(responseComRestricao,is(not(nullValue()))); //Body
		assertThat(responseComRestricao.body(),is(not(nullValue())));
		//assertThat(responseComRestricao.path("mensagem"), equalTo("O CPF "+ cpfInvalido +" possui restrição"));
		assertThat(responseComRestricao.asString(),matchesJsonSchema("restricoes","get", 200)); //Schema
	}
}