package net.javaguides.sms.exception;

public class StudentNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StudentNotFoundException(Long id) {
	super("Student not found with ID: " + id);
	}
	
	public StudentNotFoundException(String message) {
		super(message);
	}

}
