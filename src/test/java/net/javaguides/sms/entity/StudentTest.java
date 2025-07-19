package net.javaguides.sms.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StudentTest {
	
	@Test
	void testGetterAndSetters() {
		Student student = new Student();
		student.setId(1L);
		student.setFirstName("Alice");
		student.setLastName("Wonderland");
		student.setEmail("alice@example.com");
		
		assertEquals(1L, student.getId());
		assertEquals("Alice", student.getFirstName());
		assertEquals("Wonderland", student.getLastName());
		assertEquals("alice@example.com", student.getEmail());
	}
	
	@Test
	void testToString() {
		Student student = new Student();
		student.setFirstName("Bod");
		assertTrue(student.toString().contains("Bod"));
	}

}
