package helper;

import java.util.*;

import lombok.Data;


@Data
public class VariaveisUteis {
	
	//Uteis
	private String barra = "/";
	private String semHeader ="noHead";
//	private Faker fake = new Faker();
//	private Map<String, Object> params = new HashMap<String, Object>();
//	private Map<String, Object> params2 = new HashMap<String, Object>();
	private Random random = new Random();
	private Boolean adm = random.nextBoolean();
	private String token = "noToken";
    //Dados de teste
	private String terminoEmail = "@qatest.com";
	private String idInvalido = "Ab1Cd2Ef4Gh5Ij6K";
	private String idInvalidoQuery = "?_id=Ab1Cd2Ef4Gh5Ij6K";
	private String idValidoQuery = "?_id=wKmsFoitXBv84Lt9";
	private String idValido;
	private String nomeValido = "NomeValido";
	private String emailValido = "email@valido.com";
	private String emailNaoCadastrado = "email@naocadastrado.com";
	private String senhaValido = "senhaValid";
	private String senhaNaoCadastrada = "senhaNaoCa";
	//Test Json
	private String nada = "";
	private String espacoBranco = " ";
	private String caracterInvalido = "※₯á◌Ⅻ⨮Δ📖..";
	private String numerosString = "5969866";
	private Integer numb = 11;
	private String maxCaracter = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	//private String maxCaracters = "a".repeat(100);
	private String minCaracter = "a";
	//private String minCaracter = "a".repeat(1);
    //Expecificos
	private String[] listaTest = {nada, espacoBranco, caracterInvalido, maxCaracter };
	private String[] listaTest2 = {minCaracter, maxCaracter };
	private String[] listaTest3 = {espacoBranco, caracterInvalido, maxCaracter, minCaracter};
}