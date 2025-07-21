package net.javaguides.sms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.mapper.StudentMapper;
import net.javaguides.sms.repository.StudentRepository;

@SpringBootApplication
public class StudentManagementSystemApplication implements CommandLineRunner {

    private final StudentRepository studentRepository;

    
    public StudentManagementSystemApplication(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            StudentDto student1 = new StudentDto("Ramesh", "Fadatare", "ramesh@gmail.com");
            StudentDto student2 = new StudentDto("Sanjay", "Jadhav", "sanjay@gmail.com");
            StudentDto student3 = new StudentDto("Tony", "Fadatare", "tony@gmail.com");

            studentRepository.save(StudentMapper.mapToEntity(student1));
            studentRepository.save(StudentMapper.mapToEntity(student2));
            studentRepository.save(StudentMapper.mapToEntity(student3));
        } catch (Exception e) {
            System.err.println("Error initializing student data: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
