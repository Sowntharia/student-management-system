package net.javaguides.sms.integration;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import net.javaguides.sms.service.StudentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class StudentServiceImplIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
        student = new Student();
        student.setFirstName("Alice");
        student.setLastName("Wonder");
        student.setEmail("alice@example.com");
        studentService.saveStudent(student);
    }

    @Test
    void shouldSaveStudent() {
        Student student = new Student();
        student.setFirstName("Bob");
        student.setLastName("Builder");
        student.setEmail("bob@example.com");

        Student saved = studentService.saveStudent(student);

        assertThat(saved).isNotNull();
        assertThat(saved.getEmail()).isEqualTo("bob@example.com");

        List<Student> all = studentRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    void shouldGetAllStudents() {
        List<Student> students = studentService.getAllStudents();
        assertThat(students).hasSize(1);
        assertThat(students.get(0).getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void shouldDeleteStudentById() {
        Long idToDelete = studentRepository.findAll().get(0).getId();
        studentService.deleteStudentById(idToDelete);

        Optional<Student> deleted = studentRepository.findById(idToDelete);
        assertThat(deleted).isEmpty();
    }

    @Test
    void shouldUpdateStudentById() {
        Long id = studentRepository.findAll().get(0).getId();

        Student update = new Student();
        update.setFirstName("Updated");
        update.setLastName("Name");
        update.setEmail("updated@example.com");

        Student result = studentService.updateStudent(id, update);

        assertThat(result.getFirstName()).isEqualTo("Updated");
        assertThat(result.getEmail()).isEqualTo("updated@example.com");
    }
}
