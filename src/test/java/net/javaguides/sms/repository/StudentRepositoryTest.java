package net.javaguides.sms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import net.javaguides.sms.BaseTestContainer;
import net.javaguides.sms.entity.Student;

@ActiveProfiles("testcontainers")
@SpringBootTest
class StudentRepositoryTest extends BaseTestContainer {
    @Autowired
    private StudentRepository studentRepository;
    @Test
    void testSaveStudent() {
        
        Student student = new Student("John", "Doe", "john@example.com");
        Student savedStudent = studentRepository.save(student);

        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isNotNull();
        assertThat(savedStudent.getFirstName()).isEqualTo("John");
    }

    @Test
    void testFindByFirstName() {
        Student student1 = new Student("Alex", "Brown", "alex1@example.com");
        Student student2 = new Student("Alex", "Smith", "alex2@example.com");
        studentRepository.save(student1);
        studentRepository.save(student2);

        List<Student> result = studentRepository.findByFirstName("Alex");

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFirstName()).isEqualTo("Alex");
    }
}
