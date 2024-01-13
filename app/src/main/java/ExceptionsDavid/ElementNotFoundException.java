package ExceptionsDavid;

public class ElementNotFoundException extends RuntimeException {
	public ElementNotFoundException() {
		super("Element not found");
	}
	
	public ElementNotFoundException(String collection) {
		super("Element not found in " + collection);
	}
	
}
