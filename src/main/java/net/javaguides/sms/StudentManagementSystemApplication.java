package net.javaguides.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.mapper.StudentMapper;
import net.javaguides.sms.repository.StudentRepository;

@SuppressWarnings("unused")
@SpringBootApplication
public class StudentManagementSystemApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementSystemApplication.class, args);
	}
    
	
	@Autowired
	private StudentRepository studentRepository;
	
	public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

	@Override
	public void run(String... args) throws Exception {
		StudentDto student1 = new StudentDto("Ramesh", "Fadatare", "ramesh@gmail.com");
        StudentDto student2 = new StudentDto("Sanjay", "Jadhav", "sanjay@gmail.com");
        StudentDto student3 = new StudentDto("Tony", "Fadatare", "tony@gmail.com");

        studentRepository.save(StudentMapper.mapToEntity(student1));
        studentRepository.save(StudentMapper.mapToEntity(student2));
        studentRepository.save(StudentMapper.mapToEntity(student3));
		
	}

}
