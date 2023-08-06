package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Simulacao {

	private String nome;
	private String cpf;
	private String email;
	private int valor;
	private int parcelas;
	private boolean seguro;
	
	
	
	@JsonProperty(value = "id", access = Access.WRITE_ONLY)
	private int id; // É apenas lido na desserialização
	
	
	
}


