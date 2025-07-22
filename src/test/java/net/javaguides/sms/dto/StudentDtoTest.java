package net.javaguides.sms.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentDtoTest {

    @Test
    void testAllArgsConstructor() {
        StudentDto dto = new StudentDto(1L, "Ramesh", "Fadatare", "ramesh@gmail.com");

        assertEquals(1L, dto.getId());
        assertEquals("Ramesh", dto.getFirstName());
        assertEquals("Fadatare", dto.getLastName());
        assertEquals("ramesh@gmail.com", dto.getEmail());
    }

    @Test
    void testPartialConstructorAndSetters() {
        StudentDto dto = new StudentDto("Sanjay", "Jadhav", "sanjay@gmail.com");
        dto.setId(2L);

        assertEquals(2L, dto.getId());
        assertEquals("Sanjay", dto.getFirstName());
        assertEquals("Jadhav", dto.getLastName());
        assertEquals("sanjay@gmail.com", dto.getEmail());
    }

    @Test
    void testDefaultConstructorAndSetters() {
        StudentDto dto = new StudentDto();
        dto.setId(3L);
        dto.setFirstName("Tony");
        dto.setLastName("Stark");
        dto.setEmail("tony@gmail.com");

        assertEquals(3L, dto.getId());
        assertEquals("Tony", dto.getFirstName());
        assertEquals("Stark", dto.getLastName());
        assertEquals("tony@gmail.com", dto.getEmail());
    }
}
