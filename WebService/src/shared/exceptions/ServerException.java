package shared.exceptions;

public class ServerException extends Exception{

	private static final long serialVersionUID = 1L;

	public ServerException(String message) {
        super(message);
    }
	
	public ServerException(Exception exception) {
        super(exception);
    }
}
