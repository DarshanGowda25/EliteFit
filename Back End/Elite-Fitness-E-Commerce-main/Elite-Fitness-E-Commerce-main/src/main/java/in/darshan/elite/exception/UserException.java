package in.darshan.elite.exception;

public class UserException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	//serialVersionUID is a unique ID that helps Java determine if a loaded class is compatible with the serialized version
	//❓Why is it here in your UserException class?
	//Because:
	//RuntimeException implements Serializable,So your custom UserException does too (indirectly)

		


	public UserException(String message) {
		super(message);
	}
	
	

}
