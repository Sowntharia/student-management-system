package net.javaguides.sms.service.impl;

import net.javaguides.sms.dto.StudentDto;
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

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john@example.com");
    }

    @Test
    void shouldSaveStudentSuccessfully() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDto dto = new StudentDto("John", "Doe", "john@example.com");
        StudentDto result = studentService.saveStudent(dto);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("john@example.com");
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void shouldGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<StudentDto> result = studentService.getAllStudents();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void shouldGetStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentDto dto = studentService.getStudentById(1L);

        assertThat(dto.getFirstName()).isEqualTo("John");
    }

    @Test
    void shouldThrowWhenStudentNotFound() {
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getStudentById(2L))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student not found with ID: 2");
    }

    @Test
    void shouldUpdateStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDto update = new StudentDto("Updated", "Name", "updated@example.com");
        StudentDto updated = studentService.updateStudent(1L, update);

        assertThat(updated.getEmail()).isEqualTo("updated@example.com");
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
                .isInstanceOf(StudentNotFoundException.class);
    }
}
