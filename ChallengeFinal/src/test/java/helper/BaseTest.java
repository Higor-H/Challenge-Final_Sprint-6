package helper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import lombok.Getter;
import lombok.Setter;
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
	//	usuariosService = new UsuariosService(rest);
		v = new VariaveisUteis();
	}
}