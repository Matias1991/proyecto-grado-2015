package datalayer.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ManageConnection {

	private static Connection connection = null;
	
	public Connection GetConnection() throws ClassNotFoundException, SQLException, IOException
	{
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(GetValueProperty("DB_URL"), GetValueProperty("USER"), GetValueProperty("PASSWORD"));
		connection.setAutoCommit(Boolean.parseBoolean(GetValueProperty("AUTO_COMMIT")));
		
		return connection;
	}
	
	public String GetValueProperty(String property) throws IOException
	{
		Properties properties = new Properties();
		String fileName = "config.properties";
		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
		
		if(inputStream != null)
		{
			properties.load(inputStream);
		}
		else	
		{
			throw new FileNotFoundException("property file not found");
		}
	
		return properties.getProperty(property);
	}
}
