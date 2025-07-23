package net.javaguides.sms;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * Main class of the Spring Boot Student Management System application.
 * <p>
 * This class serves as the entry point and bootstraps the Spring application context,
 * starting the embedded Tomcat server and initializing application components.
 * </p>
 */

@SpringBootApplication
public class StudentManagementSystemApplication {
   
	private static final Logger logger = LoggerFactory.getLogger(StudentManagementSystemApplication.class);
	
	 /**
     * Entry point of the Spring Boot Student Management System application.
     * <p>
     * This method initializes and runs the Spring application using
     * {@link SpringApplication#run(Class, String...)}.
     * </p>
     *
     * @param args command-line arguments passed at startup
     */

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
    }
    
    /**
     * CommandLineRunner to insert sample student data into the database at application startup.
     *
     * @param studentRepository the student repository to save student data
     * @return the runner that executes after application context is loaded
     */
    
    @Bean
    public CommandLineRunner run(StudentRepository studentRepository) {
        return args -> {
            Student student1 = new Student("Ranesh", "Fadatare", "ranesh@gmail.com");
            Student student2 = new Student("Sanjay", "Jadhav", "sanjay@gmail.com");
            Student student3 = new Student("Tony", "Fadatare", "tony@gmail.com");

            studentRepository.saveAll(Arrays.asList(student1, student2, student3));

            logger.info("Sample students saved successfully.");
        };
    }
}
