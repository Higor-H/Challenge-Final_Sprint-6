package helper;

import java.text.MessageFormat;
import java.util.Properties;
import javax.management.RuntimeErrorException;

public class EnvConfig {
	
	private static Properties environmentProperties;
	
	// Retornar uma propiedade de ambiente
	public static String getProperty(String key, String defaultValue) {
		
		loadConfigurationProperties();
		return System.getProperty(key, environmentProperties.getProperty(key, defaultValue));
		
	}
	
	// Carrega o arquivo de configuração de ambiente
	private static void loadConfigurationProperties() {
		if (environmentProperties == null) {
			// env deve ser passado por linha de comando
			String env = System.getProperty("env", "local");
			
			String configFile = MessageFormat.format("/config/{0}.properties" , env);
			
			environmentProperties = new Properties();
			
			try	{
				environmentProperties.load(EnvConfig.class.getResourceAsStream(configFile));
				} catch(Exception e) {
					e.printStackTrace();
					throw new RuntimeException("Erro ao ler arquivo de configução");
			}
		}
	}
}
