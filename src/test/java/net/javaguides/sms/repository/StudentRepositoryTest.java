package net.javaguides.sms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.javaguides.sms.entity.Student;

@SpringBootTest
public class StudentRepositoryTest {
	@Autowired
	private StudentRepository studentRepository;
	
	@Test
	public void testSaveStudent() {
		//Arrange
		Student student = new Student("John", "Doe", "john@example.com");
		
		//Act
		Student savedStudent = studentRepository.save(student);
		
		//Assert
		assertThat(savedStudent).isNotNull();
		assertThat(savedStudent.getId()).isNotNull();
		assertThat(savedStudent.getFirstName()).isEqualTo("John");
	}
	
	@Test
	public void testFindByFirstName() {
		//Arrange
		Student student1 = new Student("Alex", "Brown", "alex1@example.com");
		Student student2 = new Student("Alex", "Smith", "alex2@example.com");
		studentRepository.save(student1);
		studentRepository.save(student2);
		
		//Act
		List<Student> result = studentRepository.findByFirstName("Alex");
		
		//Assert
		assertThat(result).hasSize(2);
		assertThat(result.get(0).getFirstName()).isEqualTo("Alex");
	}

}
