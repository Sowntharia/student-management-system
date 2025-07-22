package net.javaguides.sms.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentDtoTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        StudentDto dto = new StudentDto();

        dto.setId(1L);
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john@example.com");

        assertEquals(1L, dto.getId());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("john@example.com", dto.getEmail());
    }

    @Test
    void testAllArgsConstructor() {
        StudentDto dto = new StudentDto(1L, "Alice", "Smith", "alice@example.com");

        assertEquals(1L, dto.getId());
        assertEquals("Alice", dto.getFirstName());
        assertEquals("Smith", dto.getLastName());
        assertEquals("alice@example.com", dto.getEmail());
    }

    @Test
    void testConstructorWithoutId() {
        StudentDto dto = new StudentDto("Bob", "Brown", "bob@example.com");

        assertNull(dto.getId());
        assertEquals("Bob", dto.getFirstName());
        assertEquals("Brown", dto.getLastName());
        assertEquals("bob@example.com", dto.getEmail());
    }

    @Test
    void testSettersOverrideValues() {
        StudentDto dto = new StudentDto("Temp", "Temp", "temp@example.com");

        dto.setId(2L);
        dto.setFirstName("Final");
        dto.setLastName("Value");
        dto.setEmail("final@example.com");

        assertEquals(2L, dto.getId());
        assertEquals("Final", dto.getFirstName());
        assertEquals("Value", dto.getLastName());
        assertEquals("final@example.com", dto.getEmail());
    }
}
