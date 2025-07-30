package net.javaguides.sms;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.logging.Level;
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
            List<Student> sampleStudents = List.of(
                new Student("Ranesh", "Fadatare", "ranesh@gmail.com"),
                new Student("Sanjay", "Jadhav", "sanjay@gmail.com"),
                new Student("Tony", "Fadatare", "tony@gmail.com")
            );

            for (Student student : sampleStudents) {
                saveIfNotExists(studentRepository, student);
            }

            logger.info("Sample students checked and saved if not present.");
        };
    }

    private void saveIfNotExists(StudentRepository studentRepository, Student student) {
        String email = student.getEmail();
        if (email != null && !email.isBlank() && !studentRepository.existsByEmail(email)) {
            studentRepository.save(student);
            if (logger.isLoggable(Level.INFO)) {
                logger.info("Student saved: " + student);
            }
        } else {
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Student with email '" + email + "' already exists or email is invalid.");
            }
        }
    }
}
