package shared.exceptions;

public class EmailException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmailException(String message) {
        super(message);
    }
	
	public EmailException(Exception exception) {
        super(exception);
    }
}
