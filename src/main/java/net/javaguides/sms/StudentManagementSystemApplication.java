package net.javaguides.sms;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

@SpringBootApplication
public class StudentManagementSystemApplication {

    private static final Logger logger = Logger.getLogger(StudentManagementSystemApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(StudentRepository studentRepository) {
        return args -> {
            saveIfNotExists(studentRepository, "Ranesh", "Fadatare", "ranesh@gmail.com");
            saveIfNotExists(studentRepository, "Sanjay", "Jadhav", "sanjay@gmail.com");
            saveIfNotExists(studentRepository, "Tony", "Fadatare", "tony@gmail.com");

            logger.info("Sample students checked and saved if not present.");
        };
    }

    private void saveIfNotExists(StudentRepository studentRepository, String firstName, String lastName, String email) {
        if (!studentRepository.existsByEmail(email)) {
            studentRepository.save(new Student(firstName, lastName, email));
        }
    }
}
