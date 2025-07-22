package net.javaguides.sms.service.impl;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.exception.StudentNotFoundException;
import net.javaguides.sms.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student sampleStudent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleStudent = new Student();
        sampleStudent.setId(1L);
        sampleStudent.setFirstName("John");
        sampleStudent.setLastName("Doe");
        sampleStudent.setEmail("john.doe@example.com");
    }

    @Test
    void shouldSaveStudentSuccessfully() {
        when(studentRepository.save(any(Student.class))).thenReturn(sampleStudent);

        Student newStudent = new Student();
        newStudent.setFirstName("John");
        newStudent.setLastName("Doe");
        newStudent.setEmail("john.doe@example.com");

        Student saved = studentService.saveStudent(newStudent);

        assertThat(saved.getFirstName()).isEqualTo("John");
        assertThat(saved.getEmail()).isEqualTo("john.doe@example.com");
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void shouldReturnAllStudentsAsDtoList() {
        when(studentRepository.findAll()).thenReturn(List.of(sampleStudent));

        List<Student> result = studentService.getAllStudents();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
        verify(studentRepository).findAll();
    }

    @Test
    void shouldReturnStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));

        Student result = studentService.getStudentById(1L);

        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void shouldThrowExceptionIfStudentNotFoundById() {
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getStudentById(2L))
            .isInstanceOf(StudentNotFoundException.class)
            .hasMessageContaining("Student not found with ID: 2");
    }

    @Test
    void shouldUpdateStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(sampleStudent);

        Student updatedInfo = new Student();
        updatedInfo.setFirstName("Updated");
        updatedInfo.setLastName("Name");
        updatedInfo.setEmail("updated@example.com");

        Student updated = studentService.updateStudent(1L, updatedInfo);

        assertThat(updated.getFirstName()).isEqualTo("Updated");
        assertThat(updated.getEmail()).isEqualTo("updated@example.com");
    }

    @Test
    void shouldThrowWhenUpdateFailsIfStudentNotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        Student updateData = new Student();
        updateData.setFirstName("X");
        updateData.setLastName("Y");
        updateData.setEmail("z@example.com");

        assertThatThrownBy(() -> studentService.updateStudent(99L, updateData))
            .isInstanceOf(StudentNotFoundException.class)
            .hasMessageContaining("Student not found with ID: 99");
    }

    @Test
    void shouldDeleteStudentById() {
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudentById(1L);

        verify(studentRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingStudent() {
        when(studentRepository.existsById(2L)).thenReturn(false);

        assertThatThrownBy(() -> studentService.deleteStudentById(2L))
            .isInstanceOf(StudentNotFoundException.class)
            .hasMessageContaining("Student not found with ID: 2");
    }
}
