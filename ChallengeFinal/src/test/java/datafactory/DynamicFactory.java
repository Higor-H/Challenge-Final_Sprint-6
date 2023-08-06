package datafactory;

import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;

import helper.VariaveisUteis;
import model.Simulacao;

public class DynamicFactory {
	
	private static Faker faker = new Faker(Locale.ENGLISH);
	private static VariaveisUteis v = new VariaveisUteis();
	private static Random random = new Random();

	
	public static Simulacao generateRandomSimulacaos() {
		
		Random random = new Random();
		Simulacao simulacao = new Simulacao();
		
		int valor = random.nextInt(1000,40000);
		int parcelas = random.nextInt(2,48);
		String nome = faker.name().firstName();
		String cpf =  FakeCPF.gerarCPFValido();
		
		simulacao.setNome(nome + v.getEspacoBranco() + faker.name().lastName());
		simulacao.setCpf(cpf);
		simulacao.setEmail(nome.toLowerCase()+v.getTerminoEmail()); //"@qatest.com"
		simulacao.setValor(valor);
		simulacao.setParcelas(parcelas);
		simulacao.setSeguro(random.nextBoolean());
		
		return simulacao;
	}
	
	
	public static Simulacao generateRandomSimulacaosStr(String cpf) {
		
		Random random = new Random();
		Simulacao simulacao = new Simulacao();
		
		int valor = random.nextInt(1000,40000);
		int parcelas = random.nextInt(2,48);
		String nome = faker.name().firstName();
		
		simulacao.setNome(nome + v.getEspacoBranco() + faker.name().lastName());
		simulacao.setCpf(cpf);
		simulacao.setEmail(nome.toLowerCase()+v.getTerminoEmail()); //"@qatest.com"
		simulacao.setValor(valor);
		simulacao.setParcelas(parcelas);
		simulacao.setSeguro(random.nextBoolean());
		
		return simulacao;
	}
	
	public static Simulacao generateRandomSimulacaosDadosErrados(String cpf) {
		
		Random random = new Random();
		Simulacao simulacao = new Simulacao();
		
		
		
		int randomNumber = random.nextInt(4);
		String[] listaTest6 = v.getListaTest6();
		
		String nome = listaTest6[randomNumber];
		
		int valor = random.nextInt(1000,40000);
		int parcelas = random.nextInt(2,48);
		
		simulacao.setNome(nome);
		simulacao.setCpf(cpf);
		simulacao.setEmail(faker.internet().emailAddress()); //"@qatest.com"
		simulacao.setValor(valor);
		simulacao.setParcelas(parcelas);
		simulacao.setSeguro(random.nextBoolean());
		
		return simulacao;
	}
	
	public static Simulacao generateRandomSimulacaosDadosErradosEmail(String cpf) {
		
		Random random = new Random();
		Simulacao simulacao = new Simulacao();
		
		
		
		int randomNumber = random.nextInt(4);
		String[] listaTestEmail = v.getListaTestEmail();
		
		String email = listaTestEmail[randomNumber]+v.getTerminoEmail();
		
		int valor = random.nextInt(1000,40000);
		int parcelas = random.nextInt(2,48);
		
		simulacao.setNome(faker.name().fullName());
		simulacao.setCpf(cpf);
		simulacao.setEmail(email); //"@qatest.com"
		simulacao.setValor(valor);
		simulacao.setParcelas(parcelas);
		simulacao.setSeguro(random.nextBoolean());
		
		return simulacao;
	}

	public static Simulacao generateRandomSimulacaosDadosErradosValor(String cpf , int valor) {
	
		Random random = new Random();
		Simulacao simulacao = new Simulacao();
		
	
		int parcelas = random.nextInt(2,48);
		String nome = faker.name().firstName();
		
		simulacao.setNome(nome + v.getEspacoBranco() + faker.name().lastName());
		simulacao.setCpf(cpf);
		simulacao.setEmail(nome.toLowerCase()+v.getTerminoEmail()); //"@qatest.com"
		simulacao.setValor(valor);
		simulacao.setParcelas(parcelas);
		simulacao.setSeguro(random.nextBoolean());
	
		return simulacao;
	}
	
	public static Simulacao generateRandomSimulacaosDadosErradosParcelas(String cpf , int parcelas) {
		
		Random random = new Random();
		Simulacao simulacao = new Simulacao();
		
	
		int valor = random.nextInt(1000,40000);;
		String nome = faker.name().firstName();
		
		simulacao.setNome(nome + v.getEspacoBranco() + faker.name().lastName());
		simulacao.setCpf(cpf);
		simulacao.setEmail(nome.toLowerCase()+v.getTerminoEmail()); //"@qatest.com"
		simulacao.setValor(valor);
		simulacao.setParcelas(parcelas);
		simulacao.setSeguro(random.nextBoolean());
	
		return simulacao;
	}
	
	

	

	
}
