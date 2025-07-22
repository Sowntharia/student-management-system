package net.javaguides.sms;

import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.mapper.StudentMapper;
import net.javaguides.sms.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudentManagementSystemApplication {

    private static final Logger logger = LoggerFactory.getLogger(StudentManagementSystemApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner run(StudentRepository studentRepository) {
        return args -> {
            try {
                StudentDto student1 = new StudentDto();
                student1.setFirstName("Ramesh");
                student1.setLastName("Fadatare");
                student1.setEmail("ramesh@gmail.com");

                StudentDto student2 = new StudentDto();
                student2.setFirstName("Sanjay");
                student2.setLastName("Jadhav");
                student2.setEmail("sanjay@gmail.com");

                StudentDto student3 = new StudentDto();
                student3.setFirstName("Tony");
                student3.setLastName("Fadatare");
                student3.setEmail("tony@gmail.com");

                studentRepository.save(StudentMapper.mapToEntity(student1));
                studentRepository.save(StudentMapper.mapToEntity(student2));
                studentRepository.save(StudentMapper.mapToEntity(student3));

                logger.info("Sample student data initialized successfully.");
            } catch (Exception e) {
                logger.error("Error initializing student data", e);
            }
        };
    }
}
