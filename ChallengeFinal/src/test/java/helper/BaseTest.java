package helper;

import static constants.Endpoints.RESTRICOES;
import static constants.Endpoints.SIMULACOES;
import static helper.ServiceHelper.matchesJsonSchema;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import datafactory.DynamicFactory;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import services.BaseRest;

@Getter
@Setter
public class BaseTest {
	protected BaseRest rest;
	//protected UsuariosService usuariosService;
	protected VariaveisUteis v;
	
	
	@BeforeAll
	public static void setupEnvironment (){
	}
	
	@BeforeEach
	public void instantiateServices() {
		rest = new BaseRest();
		v = new VariaveisUteis();
	}
}