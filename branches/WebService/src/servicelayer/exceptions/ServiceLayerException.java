package servicelayer.exceptions;

public class ServiceLayerException extends Exception{

	private static final long serialVersionUID = 1L;

	public ServiceLayerException(String message) {
        super(message);
    }
	
	public ServiceLayerException(Exception exception) {
        super(exception);
    }
}
