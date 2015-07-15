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
				ThrowConfigPropertiesExceptionAndLogError(e, null);
			}
		}
		else	
		{
			ThrowConfigPropertiesExceptionAndLogError(null, String.format("El archivo %s no fue encontrado", fileName));
		}
	
		if(!properties.containsKey(key))
		{
			ThrowConfigPropertiesExceptionAndLogError(null, String.format("No se encontro la key %s en el archivo %s", key, fileName));
		}
		
		return properties.getProperty(key);
	}
	
	static void ThrowConfigPropertiesExceptionAndLogError(Exception ex, String customError) throws ConfigPropertiesException
	{
		String messageError = (ex != null)? ex.getMessage() : customError;
		String numberError = LoggerMSMP.setLog(messageError);
		throw new ConfigPropertiesException(String.format("Ocurrio un error al acceder a configuracion del sistema, consulte con el Administrador con el codigo de error: %s", numberError));
	}
}
