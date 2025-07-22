package net.javaguides.sms.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentDtoTest {

    @Test
    void testNoArgsConstructor() {
        StudentDto dto = new StudentDto();
        assertNotNull(dto);
    }

    @Test
    void testAllArgsConstructor() {
        StudentDto dto = new StudentDto(1L, "John", "Doe", "john.doe@example.com");

        assertEquals(1L, dto.getId());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("john.doe@example.com", dto.getEmail());
    }

    @Test
    void testPartialConstructor() {
        StudentDto dto = new StudentDto("Alice", "Smith", "alice@example.com");

        assertNull(dto.getId());
        assertEquals("Alice", dto.getFirstName());
        assertEquals("Smith", dto.getLastName());
        assertEquals("alice@example.com", dto.getEmail());
    }

    @Test
    void testGettersAndSetters() {
        StudentDto dto = new StudentDto();
        dto.setId(2L);
        dto.setFirstName("Jane");
        dto.setLastName("Brown");
        dto.setEmail("jane.brown@example.com");

        assertEquals(2L, dto.getId());
        assertEquals("Jane", dto.getFirstName());
        assertEquals("Brown", dto.getLastName());
        assertEquals("jane.brown@example.com", dto.getEmail());
    }
}
