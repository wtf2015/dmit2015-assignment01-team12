package northwind.exception;

@SuppressWarnings("serial")
public class NoOrderDetailsException extends Exception{

	public NoOrderDetailsException(String message) {
		super(message);
	}
}
