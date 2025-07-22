package net.javaguides.sms;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentManagementSystemApplicationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void contextLoads() {
        assertNotNull(studentRepository);
    }

    @Test
    void testSaveStudent() {
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Student");
        student.setEmail("test@student.com");

        Student saved = studentRepository.save(student);
        assertNotNull(saved.getId());
        assertEquals("Test", saved.getFirstName());
    }

    @Test
    void testDeleteStudent() {
        Student student = new Student();
        student.setFirstName("Delete");
        student.setLastName("Me");
        student.setEmail("deleteme@student.com");

        Student saved = studentRepository.save(student);
        Long id = saved.getId();
        studentRepository.deleteById(id);

        assertFalse(studentRepository.findById(id).isPresent());
    }
}
