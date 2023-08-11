package helper;

import java.util.*;

import datafactory.FakeCPF;
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
	private Number notacao = 2.5E+6;
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
	private String letrasString = "abcdefgH";
	private Integer numb = 11;
	private String maxCaracter = "a".repeat(200);
	private String minCaracter = "a".repeat(1);
    //Expecificos
	private String cpfInvalidoFormatado = FakeCPF.formatarCPF(FakeCPF.gerarCPFInvalido());
	private String cpfValidoFormatado = FakeCPF.formatarCPF(FakeCPF.gerarCPFValido());
	private String cpfFormatado = FakeCPF.formatarCPF(FakeCPF.gerarCPF());
	private String cpfInexistente = "12345678910";
	
	private String cpfValido = FakeCPF.gerarCPFValido();
	private String cpfInvalido = FakeCPF.gerarCPFInvalido();
	
	private String[] listaTest = {nada, espacoBranco, caracterInvalido, maxCaracter };
	private String[] listaTest2 = {minCaracter, maxCaracter };
	private String[] listaTest3 = {espacoBranco, caracterInvalido, maxCaracter, minCaracter};
	private String[] listaTest4 = {espacoBranco, caracterInvalido, maxCaracter, minCaracter, cpfInexistente, letrasString};
	private String[] listaTest5 = {cpfValido, cpfInvalido};
	private String[] listaTest6 = { caracterInvalido, maxCaracter, minCaracter, cpfInexistente, letrasString};
	private String[] listaTestEmail = { caracterInvalido, maxCaracter, nada, espacoBranco};
			}