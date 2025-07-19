package net.javaguides.sms.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StudentNotFoundExceptionTest {

	 @Test
	    void testExceptionMessageWithId() {
	        StudentNotFoundException ex = new StudentNotFoundException(1L);
	        assertEquals("Student not found with ID: 1", ex.getMessage());
	    }

	    @Test
	    void testExceptionMessageWithString() {
	        StudentNotFoundException ex = new StudentNotFoundException("Student not found");
	        assertEquals("Student not found", ex.getMessage());
	    }
}
