package net.javaguides.sms.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.exception.StudentNotFoundException;
import net.javaguides.sms.repository.StudentRepository;

public class StudentServiceImplTest {

	@Mock
	private StudentRepository studentRepository;
	
	@InjectMocks
	private StudentServiceImpl studentService;
	
	private Student student;
	
	@BeforeEach
	void setUp() {
		
		MockitoAnnotations.openMocks(this);
		student = new Student("John", "Doe","john.doe@example.com");
		student.setId(1L);
		
	}
	
	@Test
	void shouldSaveStudentSuccessfully() {
		
		when(studentRepository.save(any(Student.class))).thenReturn(student);
		
		StudentDto dto = new StudentDto("John", "Doe", "john.doe@example.com");
		dto.setId(1L);
		StudentDto saved = studentService.saveStudent(dto);
		
		assertThat(saved.getId()).isEqualTo(1L);
		assertThat(saved.getFirstName()).isEqualTo("John");
		assertThat(saved.getLastName()).isEqualTo("Doe");
		assertThat(saved.getEmail()).isEqualTo("john.doe@example.com");
		
		ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
		verify(studentRepository).save(captor.capture());
		
        Student captured = captor.getValue();
		
		assertThat(captured.getId()).isEqualTo(1L);
		assertThat(captured.getFirstName()).isEqualTo("John");
		assertThat(captured.getLastName()).isEqualTo("Doe");
		assertThat(captured.getEmail()).isEqualTo("john.doe@example.com");
		
		
	}
	
	@Test
	void shouldReturnAllStudentsAsDtoList() {
		when(studentRepository.findAll()).thenReturn(Arrays.asList(student));
		
		List<StudentDto> studentDtos = studentService.getAllStudents();
		
		assertThat(studentDtos).hasSize(1);
		StudentDto result = studentDtos.get(0);
		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getFirstName()).isEqualTo("John");
		assertThat(result.getLastName()).isEqualTo("Doe");
		assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
		
		verify(studentRepository).findAll();
	}
	
	@Test
	void shouldReturnStudentById() {
		
		when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
		
		StudentDto result = studentService.getStudentById(1L);
		
		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getFirstName()).isEqualTo("John");
		assertThat(result.getLastName()).isEqualTo("Doe");
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
		
		when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
		when(studentRepository.save(any(Student.class))).thenReturn(student);
		
		StudentDto dto = new StudentDto("Updated", "Name", "updated@example.com");
		dto.setId(1L);
		StudentDto updated = studentService.updateStudent(1L, dto);
		
		assertThat(updated.getFirstName()).isEqualTo("Updated");
		assertThat(updated.getLastName()).isEqualTo("Name");
		assertThat(updated.getEmail()).isEqualTo("updated@example.com");
		
		ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
		verify(studentRepository).save(captor.capture());
		
		Student captured = captor.getValue();
		
		assertThat(captured.getId()).isEqualTo(1L);
		assertThat(captured.getFirstName()).isEqualTo("Updated");
		assertThat(captured.getLastName()).isEqualTo("Name");
		assertThat(captured.getEmail()).isEqualTo("updated@example.com");
	}
	
	@Test
	void shouldThrowWhenUpdateFailsIfStudentNotFound() {
		when(studentRepository.findById(99L)).thenReturn(Optional.empty());
		
		StudentDto dto = new StudentDto("X", "Y", "z@example.com");
		
		assertThatThrownBy(() -> studentService.updateStudent(99L, dto))
		      .isInstanceOf(StudentNotFoundException.class)
		      .hasMessageContaining("Student not found with ID: 99");
	}
	
	
	
	@Test
	void shouldDeleteStudentById() {
        when(studentRepository.existsById(1L)).thenReturn(true);
		doNothing().when(studentRepository).deleteById(1L);
		
		studentService.deleteStudentById(1L);
		
		verify(studentRepository).deleteById(1L);
		verify(studentRepository).existsById(1L);

	}
	
	@Test
	void shouldThrowWhenDeletingNonExistingsStudent() {
		when(studentRepository.existsById(2L)).thenReturn(false);
		
		assertThatThrownBy(() ->studentService.deleteStudentById(2L))
		     .isInstanceOf(StudentNotFoundException.class)
		     .hasMessageContaining("Student not found with ID: 2");
	}
	
}
