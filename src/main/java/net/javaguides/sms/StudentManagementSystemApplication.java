package net.javaguides.sms;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

/**
 * Entry point of the Student Management System application.
 * This class bootstraps the Spring Boot application and inserts
 * sample students if they do not already exist.
 */
@SpringBootApplication
public class StudentManagementSystemApplication {

    private static final Logger logger = Logger.getLogger(StudentManagementSystemApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(StudentRepository studentRepository) {
        return args -> {
            insertIfNotExists(studentRepository, "Ranesh", "Fadatare", "ranesh@gmail.com");
            insertIfNotExists(studentRepository, "Sanjay", "Jadhav", "sanjay@gmail.com");
            insertIfNotExists(studentRepository, "Tony", "Fadatare", "tony@gmail.com");

            logger.info("Sample students checked and inserted only if not present.");
        };
    }

    private void insertIfNotExists(StudentRepository studentRepository, String firstName, String lastName, String email) {
        if (!studentRepository.existsByEmail(email)) {
            Student student = new Student(firstName, lastName, email);
            studentRepository.save(student);
            logger.info("Inserted new student: {}" + email);
        } else {
            logger.warning("Skipped existing student: {}" + email);
        }
    }
}
