package shared;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import shared.exceptions.ServerException;

public class ConfigurationProperties {
	
	public static String GetConfigValue(String key) throws ServerException
	{
		Properties properties = new Properties();
		String fileName = "config.properties";
		
		InputStream inputStream = ConfigurationProperties.class.getClassLoader().getResourceAsStream(fileName);
		
		if(inputStream != null)
		{
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				throw new ServerException(e);
			}
		}
		else	
		{
			throw new ServerException(String.format("El archivo %s no fue encontrado", fileName));
		}
	
		if(!properties.containsKey(key))
		{
			throw new ServerException(String.format("No se encontro la key %s en el archivo %s", key, fileName));
		}
		
		return properties.getProperty(key);
	}
}
