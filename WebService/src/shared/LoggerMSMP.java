package shared;

import org.apache.log4j.Logger;

public class LoggerMSMP {
	
	public static Logger log;
	
	public static void setLog(String title, String message)
	{
		log = Logger.getLogger(title);
		log.error(message);
	}
}
