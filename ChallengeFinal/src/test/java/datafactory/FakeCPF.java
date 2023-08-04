package datafactory;

import java.util.Random;


//https://atitudereflexiva.wordpress.com/2021/05/05/entenda-como-e-gerado-o-numero-do-cpf/
//https://www.4devs.com.br/gerador_de_cpf
//https://consulta.guru/validar-cpf



//
public class FakeCPF {
	
	


	 public static String gerarCPFInvalido() {
		 String[] cpfRestritos  = {
		            "97093236014",
		            "60094146012",
		            "84809766080",
		            "62648716050",
		            "26276298085",
		            "01317496094",
		            "55856777050",
		            "19626829001",
		            "24094592008",
		            "58063164083"
		        };
		 	Random random = new Random();
	        int indiceAleatorio = random.nextInt(cpfRestritos.length);

	        String cpfRestrito = cpfRestritos[indiceAleatorio];
	       return cpfRestrito;
	 }
	
	 
	    public static String gerarCPFValido() { 
	    	
	    	String cpf;
			do {cpf =gerarCPF() ;
	    	
	    	}while (cpf == "97093236014" || cpf == "60094146012" || cpf == "84809766080" || 
	    			cpf == "62648716050" || cpf == "26276298085" || cpf == "01317496094" || 
	    			cpf == "55856777050" || cpf == "19626829001" || cpf == "24094592008" || 
	    			cpf == "58063164083");
			
			return cpf;	
	    }
	 
	    
	    
	    
	    //GERA CPFS
	    //O NONO NMERO REPRESENTA O ESTADO ONDE O PORTADOR DO CPF NASCEU, 
	    //CONSULTAR TABELA https://atitudereflexiva.wordpress.com/2021/05/05/entenda-como-e-gerado-o-numero-do-cpf/
	    //IMPLEMENTAR CASO NECESSÁRIO
	    
	    public static String gerarCPF() {
	        StringBuilder sb = new StringBuilder();
	        Random random = new Random();

	        // Gera os 9 primeiros dígitos
	        for (int i = 0; i < 9; i++) {
	            int digito = random.nextInt(10); // Gera um dígito aleatório de 0 a 9
	            sb.append(digito);
	        }

	        String cpfParcial = sb.toString();
	        String digitosVerificadores = calcularDigitosVerificadores(cpfParcial);
	        return cpfParcial + digitosVerificadores;
	    }

	    public static String calcularDigitosVerificadores(String cpfParcial) {
	        int primeiroDigito = calcularDigitoVerificador(cpfParcial, 10);
	        int segundoDigito = calcularDigitoVerificador(cpfParcial + primeiroDigito, 11);
	        return String.format("%d%d", primeiroDigito, segundoDigito);
	    }

	    public static int calcularDigitoVerificador(String cpfParcial, int pesoMaximo) {
	        int total = 0;
	        for (int i = 0; i < cpfParcial.length(); i++) {
	            int digito = Character.getNumericValue(cpfParcial.charAt(i));
	            total += digito * (pesoMaximo - i);
	        }
	        int resto = total % 11;
	        return (resto < 2) ? 0 : (11 - resto);
	    }
	    
	    //Formata CPF
	    public static String formatarCPF(String cpf) {
	        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	    }
}
