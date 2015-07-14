package shared;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import shared.exceptions.ConfigPropertiesException;

public class ConfigurationProperties {
	
	public static String GetConfigValue(String key) throws ConfigPropertiesException
	{
		Properties properties = new Properties();
		String fileName = "config.properties";
		
		InputStream inputStream = ConfigurationProperties.class.getClassLoader().getResourceAsStream(fileName);
		
		if(inputStream != null)
		{
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				String numberError = LoggerMSMP.setLog(e.getMessage());
				throw new ConfigPropertiesException("Ocurrio un problema al intentar acceder a configuracion del sistema, consulte con el Administrador con el codigo de error: " + numberError);
			}
		}
		else	
		{
			String notFoundFile = "El archivo " + fileName + " no fue encontrado";
			String numberError = LoggerMSMP.setLog(notFoundFile);
			throw new ConfigPropertiesException("Ocurrio un problema al intentar acceder a configuracion del sistema, consulte con el Administrador con el codigo de error: " + numberError);
		}
	
		if(!properties.containsKey(key))
		{
			String notFoudKey = "No se encontro la key " + key + " en el archivo " + fileName;
			String numberError = LoggerMSMP.setLog(notFoudKey);
			throw new ConfigPropertiesException("Ocurrio un problema al intentar acceder a configuracion del sistema, consulte con el Administrador con el codigo de error: " + numberError);
		}
		
		return properties.getProperty(key);
	}
}
