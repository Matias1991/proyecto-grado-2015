package shared;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationProperties {
	
	public static String GetConfigValue(String key) throws IOException
	{
		Properties properties = new Properties();
		String fileName = "config.properties";
		
		InputStream inputStream = ConfigurationProperties.class.getClassLoader().getResourceAsStream(fileName);
		
		if(inputStream != null)
		{
			properties.load(inputStream);
		}
		else	
		{
			throw new FileNotFoundException("property file not found");
		}
	
		return properties.getProperty(key);
	}
}
