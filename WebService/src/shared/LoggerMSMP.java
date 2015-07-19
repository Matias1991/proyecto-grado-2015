package shared;

import java.util.UUID;

import org.apache.log4j.Logger;

public class LoggerMSMP {

	public static Logger log;
	
	public static String setLog(String message) {
		
		String errorNumber = UUID.randomUUID().toString();
		
		log = Logger.getLogger("************ MSMP error ********");
		log.error(buildMessageError(errorNumber, message));
		
		return errorNumber;
	}

	
	static String buildMessageError(String errorNumber, String message)
	{
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(System.getProperty("line.separator"));
		strBuilder.append("Codigo de error: " + errorNumber);
		strBuilder.append(System.getProperty("line.separator"));
		strBuilder.append("Message: " + message);
		
		return strBuilder.toString();
	}
}
