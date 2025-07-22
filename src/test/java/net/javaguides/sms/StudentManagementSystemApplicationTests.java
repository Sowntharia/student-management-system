package net.javaguides.sms;

import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.mapper.StudentMapper;
import net.javaguides.sms.repository.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class StudentManagementSystemApplicationTests {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void clearDatabase() {
        studentRepository.deleteAll(); // Ensure a clean database before each test
    }

    @Test
    void testRunMethodManually() {
        assertDoesNotThrow(() -> {
            StudentDto s1 = new StudentDto("Ramesh", "Fadatare", "ramesh@gmail.com");
            StudentDto s2 = new StudentDto("Sanjay", "Jadhav", "sanjay@gmail.com");
            StudentDto s3 = new StudentDto("Tony", "Fadatare", "tony@gmail.com");

            studentRepository.save(StudentMapper.mapToEntity(s1));
            studentRepository.save(StudentMapper.mapToEntity(s2));
            studentRepository.save(StudentMapper.mapToEntity(s3));

            List<Student> students = studentRepository.findAll();
            assertEquals(3, students.size());
        });
    }

    @Test
    void testRunMethodFromMainApplication() {
        StudentManagementSystemApplication app = new StudentManagementSystemApplication(this.studentRepository);
        try {
            app.run();
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }

        List<Student> students = studentRepository.findAll();
        assertEquals(3, students.size(), "There should be 3 students loaded from the run() method.");
    }

    @Test
    void testConstructorCoverage() {
        StudentManagementSystemApplication app = new StudentManagementSystemApplication(studentRepository);
        assertNotNull(app);
    }

    
}
