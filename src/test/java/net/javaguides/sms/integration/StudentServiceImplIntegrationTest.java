package net.javaguides.sms.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import net.javaguides.sms.service.StudentService;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@ActiveProfiles("test")
public class StudentServiceImplIntegrationTest {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentRepository studentRepository;
	
	private StudentDto studentDto;
	
	@BeforeEach
	void setUp() {
		studentRepository.deleteAll();
		studentDto = new StudentDto("Alice", "Wonder", "alice@example.com");
		studentService.saveStudent(studentDto);
	}
	
	@Test
	void shouldSaveStudent() {
		StudentDto newStudent = new StudentDto("Bod", "Smith", "bob@example.com");
		StudentDto saved = studentService.saveStudent(newStudent);
		
		assertThat(saved).isNotNull();
		assertThat(saved.getEmail()).isEqualTo("bob@example.com");
		
		List<Student> all = studentRepository.findAll();
		assertThat(all).hasSize(2);
	}
	
	@Test
	void shouldGetAllStudents() {
		List<StudentDto> students = studentService.getAllStudents();
		assertThat(students).hasSize(1);
		assertThat(students.get(0).getEmail()).isEqualTo("alice@example.com");
	}
	
	@Test
	void shouldDeleteStudentById() {
		List<Student> students = studentRepository.findAll();
		assertThat(students).hasSize(1);
		
		Long idToDelete = students.get(0).getId();
		studentService.deleteStudentById(idToDelete);
		
		Optional<Student> result = studentRepository.findById(idToDelete);
		assertThat(result).isEmpty();
	}
	
	@Test
	void shouldGetStudentById() {
		List<Student> students = studentRepository.findAll();
		Long id = students.get(0).getId();
		
		StudentDto updateDto = new StudentDto("Updated", "Name", "updated@example.com");
		StudentDto updated = studentService.updateStudent(id, updateDto);
		
		assertThat(updated.getFirstName()).isEqualTo("Updated");
		assertThat(updated.getEmail()).isEqualTo("updated@example.com");
	}
}
