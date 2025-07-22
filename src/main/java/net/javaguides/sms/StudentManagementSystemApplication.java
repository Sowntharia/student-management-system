package net.javaguides.sms;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class StudentManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(StudentRepository studentRepository) {
        return args -> {
            Student student1 = new Student("Ranesh", "Fadatare", "ranesh@gmail.com");
            Student student2 = new Student("Sanjay", "Jadhav", "sanjay@gmail.com");
            Student student3 = new Student("Tony", "Fadatare", "tony@gmail.com");

            studentRepository.saveAll(Arrays.asList(student1, student2, student3));

            System.out.println("Sample students saved successfully.");
        };
    }
}
