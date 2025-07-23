package net.javaguides.sms;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

/**
 * Entry point of the Student Management System application.
 * This class bootstraps the Spring Boot application.
 */
@SpringBootApplication
public class StudentManagementSystemApplication {
    
	private StudentRepository studentRepository;

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
    }

    public void run(String... args) throws Exception {
            Student student1 = new Student("Ranesh", "Fadatare", "ranesh@gmail.com");
            Student student2 = new Student("Sanjay", "Jadhav", "sanjay@gmail.com");
            Student student3 = new Student("Tony", "Fadatare", "tony@gmail.com");

            studentRepository.saveAll(Arrays.asList(student1, student2, student3));
    }
    }

