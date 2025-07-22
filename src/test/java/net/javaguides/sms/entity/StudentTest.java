package net.javaguides.sms.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StudentTest {
	
	@Test
    void testConstructorAndGetters() {
        Student student = new Student("John", "Doe", "john@example.com");

        assertEquals("John", student.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals("john@example.com", student.getEmail());
    }

    @Test
    void testSetters() {
        Student student = new Student();
        student.setId(101L);
        student.setFirstName("Jane");
        student.setLastName("Smith");
        student.setEmail("jane@example.com");

        assertEquals(101L, student.getId());
        assertEquals("Jane", student.getFirstName());
        assertEquals("Smith", student.getLastName());
        assertEquals("jane@example.com", student.getEmail());
    }

    @Test
    void testEqualsAndHashCode() {
        Student s1 = new Student("A", "B", "a@b.com");
        s1.setId(1L);

        Student s2 = new Student("X", "Y", "x@y.com");
        s2.setId(1L);

        Student s3 = new Student("Z", "W", "z@w.com");
        s3.setId(2L);

        assertEquals(s1, s2);  // same id
        assertNotEquals(s1, s3); // different id
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    void testToString() {
        Student student = new Student("John", "Doe", "john@example.com");
        student.setId(100L);
        String toString = student.toString();

        assertTrue(toString.contains("100"));
        assertTrue(toString.contains("John"));
        assertTrue(toString.contains("Doe"));
        assertTrue(toString.contains("john@example.com"));
    }

    @Test
    void testEqualsWithSameReference() {
        Student student = new Student("A", "B", "a@b.com");
        assertEquals(student, student); 
    }

    @Test
    void testEqualsWithDifferentType() {
        Student student = new Student("A", "B", "a@b.com");
        assertNotEquals("not a student", student); 
    }

    @Test
    void testEqualsWithNull() {
        Student student = new Student("A", "B", "a@b.com");
        assertNotEquals(null, student);       
    }

}
