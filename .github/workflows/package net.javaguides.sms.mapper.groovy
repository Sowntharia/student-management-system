package net.javaguides.sms.mapper;

import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.entity.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;

 class StudentMapperTest {

    @Test
    void testMapToStudentDto() {
        Student student = new Student("John", "Doe", "john@example.com");
        student.setId(1L);

        StudentDto dto = StudentMapper.mapToDto(student);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("john@example.com", dto.getEmail());
    }

    @Test
    void testMapToStudent() {
        StudentDto dto = new StudentDto(1L, "Jane", "Smith", "jane@example.com");

        Student student = StudentMapper.mapToEntity(dto);

        assertNotNull(student);
        assertEquals(1L, student.getId());
        assertEquals("Jane", student.getFirstName());
        assertEquals("Smith", student.getLastName());
        assertEquals("jane@example.com", student.getEmail());
    }
    
    @Test
    void testMapToEntity_WithNullFields() {
        StudentDto dto = new StudentDto();
        Student student = StudentMapper.mapToEntity(dto);

        assertNotNull(student);
        assertNull(student.getId());
        assertNull(student.getFirstName());
        assertNull(student.getLastName());
        assertNull(student.getEmail());
    }

    @Test
    void testMapToDto_WithNullFields() {
        Student student = new Student();
        StudentDto dto = StudentMapper.mapToDto(student);

        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getFirstName());
        assertNull(dto.getLastName());
        assertNull(dto.getEmail());
    }

    
    @Test
    void testMapToStudentDto_NullInput() {
        StudentDto result = StudentMapper.mapToDto(null);
        assertNull(result);
    }

    @Test
    void testMapToStudent_NullInput() {
        Student result = StudentMapper.mapToEntity(null);
        assertNull(result);
    }
    
    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<StudentMapper> constructor = StudentMapper.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        StudentMapper instance = constructor.newInstance();
        
        // Assertion to satisfy SonarCloud
        assertNotNull(instance);
        assertEquals(StudentMapper.class, instance.getClass());
    }

}
















///
package net.javaguides.sms.mapper;

import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.entity.Student;

public class StudentMapper {
	
	 private StudentMapper() {
	        // Utility class
	    }

    // Convert Entity to DTO
    public static StudentDto mapToDto(Student student) {
        if (student == null) {
            return null;
        }

        return new StudentDto(
            student.getId(),
            student.getFirstName(),
            student.getLastName(),
            student.getEmail()
        );
    }

    // Convert DTO to Entity
    public static Student mapToEntity(StudentDto dto) {
        if (dto == null) {
            return null;
        }

        Student student = new Student();
        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        return student;
    }
    
    
}
