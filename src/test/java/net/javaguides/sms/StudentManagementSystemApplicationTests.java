package net.javaguides.sms;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StudentManagementSystemApplicationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void contextLoads() {
        assertThat(studentRepository).isNotNull();
    }

    @Test
    void testSaveStudent() {
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("User");
        student.setEmail("testuser@example.com");

        Student savedStudent = studentRepository.save(student);
        assertThat(savedStudent.getId()).isNotNull();
        assertThat(savedStudent.getFirstName()).isEqualTo("Test");
    }

    @Test
    void testDeleteStudent() {
        Student student = new Student();
        student.setFirstName("ToDelete");
        student.setLastName("User");
        student.setEmail("deleteuser@example.com");

        Student savedStudent = studentRepository.save(student);
        Long id = savedStudent.getId();

        studentRepository.deleteById(id);
        assertThat(studentRepository.findById(id)).isEmpty();
    }
}
