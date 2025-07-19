package net.javaguides.sms.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StudentNotFoundExceptionTest {

	@Test
	void testExceptionMessage() {
		StudentNotFoundException ex = new StudentNotFoundException("Student not found");
		assertEquals("Student not found", ex.getMessage());
	}
}
