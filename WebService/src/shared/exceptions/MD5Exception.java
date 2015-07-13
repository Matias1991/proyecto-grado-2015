package shared.exceptions;

public class MD5Exception extends Exception{
	
	private static final long serialVersionUID = 1L;

	public MD5Exception(String message) {
        super(message);
    }
	
	public MD5Exception(Exception exception) {
        super(exception);
    }
}
