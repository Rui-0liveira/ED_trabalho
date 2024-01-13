package ExceptionsDavid;

public class ConcurrentModificationException extends RuntimeException {
	public ConcurrentModificationException() {
		System.out.println("The collection has been modified.");
	}
	
}
