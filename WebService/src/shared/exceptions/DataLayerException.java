package shared.exceptions;

public class DataLayerException extends Exception{

	private static final long serialVersionUID = 1L;

	public DataLayerException(String message) {
        super(message);
    }
	
	public DataLayerException(Exception exception) {
        super(exception);
    }
}
