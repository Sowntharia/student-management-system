package net.javaguides.sms.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Alice");
        student.setLastName("Smith");
        student.setEmail("alice@example.com");

        assertEquals(1L, student.getId());
        assertEquals("Alice", student.getFirstName());
        assertEquals("Smith", student.getLastName());
        assertEquals("alice@example.com", student.getEmail());
    }

    @Test
    void testAllArgsConstructor() {
        Student student = new Student("Bob", "Johnson", "bob@example.com");

        assertNull(student.getId());  // id is not set by constructor
        assertEquals("Bob", student.getFirstName());
        assertEquals("Johnson", student.getLastName());
        assertEquals("bob@example.com", student.getEmail());
    }

}
