package shared.exceptions;

public class ConfigPropertiesException extends Exception{

	private static final long serialVersionUID = 1L;

	public ConfigPropertiesException(String message) {
        super(message);
    }
	
	public ConfigPropertiesException(Exception exception) {
        super(exception);
    }
}
