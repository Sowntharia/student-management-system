package net.javaguides.sms.integration;

import net.javaguides.sms.BaseTestContainer;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.exception.StudentNotFoundException;
import net.javaguides.sms.repository.StudentRepository;
import net.javaguides.sms.service.StudentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("testcontainers")
@SpringBootTest
class StudentServiceImplIntegrationTest extends BaseTestContainer{
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();

        testStudent = new Student("Alice", "Walker", "alice.walker@example.com");
        testStudent = studentService.saveStudent(testStudent);
    }

    @Test
    void shouldSaveStudentToDatabase() {
        Student newStudent = new Student(null, "Bob", "Builder", "bob@example.com");

        Student saved = studentService.saveStudent(newStudent);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEmail()).isEqualTo("bob@example.com");
    }

    @Test
    void shouldRetrieveStudentById() {
        Student found = studentService.getStudentById(testStudent.getId());

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("alice.walker@example.com");
    }

    @Test
    void shouldRetrieveAllStudents() {
        List<Student> students = studentService.getAllStudents();

        assertThat(students).isNotEmpty();
        assertThat(students.get(0).getEmail()).isEqualTo("alice.walker@example.com");
    }

    @Test
    void shouldUpdateStudentDetails() {
        Student updatedInfo = new Student(null, "Alice", "Johnson", "updated@example.com");

        Student updated = studentService.updateStudent(testStudent.getId(), updatedInfo);

        assertThat(updated.getLastName()).isEqualTo("Johnson");
        assertThat(updated.getEmail()).isEqualTo("updated@example.com");
    }

    @Test
    void shouldDeleteStudentById() {
        Long id = testStudent.getId();
        studentService.deleteStudentById(id);

        assertThatThrownBy(() -> studentService.getStudentById(id))
                .isInstanceOf(StudentNotFoundException.class);
    }

    @Test
    void shouldThrowWhenStudentNotFoundForUpdate() {
        Student updateData = new Student(999L, "X", "Y", "z@example.com");

        assertThatThrownBy(() -> studentService.updateStudent(999L, updateData))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student not found with ID: 999");
    }

    @Test
    void shouldThrowWhenStudentNotFoundForDelete() {
        assertThatThrownBy(() -> studentService.deleteStudentById(888L))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student not found with ID: 888");
    }
}
