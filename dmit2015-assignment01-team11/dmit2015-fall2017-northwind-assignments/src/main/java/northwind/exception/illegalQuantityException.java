package northwind.exception;

@SuppressWarnings("serial")
public class illegalQuantityException extends Exception{

	public illegalQuantityException(String message) {
		super(message);
	}
}
