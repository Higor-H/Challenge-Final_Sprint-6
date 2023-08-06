package helper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import lombok.Getter;
import lombok.Setter;
import services.BaseRest;
import services.VerbosService;

@Getter
@Setter
public class BaseTest {
	protected BaseRest rest;
	protected VerbosService service;
	protected VariaveisUteis v;
	
	
	@BeforeAll
	public static void setupEnvironment (){
	}
	
	@BeforeEach
	public void instantiateServices() {
		rest = new BaseRest();
		service = new VerbosService();
		v = new VariaveisUteis();
	}
}