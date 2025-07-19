package net.javaguides.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;

@SuppressWarnings("unused")
@SpringBootApplication
public class StudentManagementSystemApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementSystemApplication.class, args);
	}
    
	
	@Autowired
	private StudentRepository studentRepository;


	@Override
	public void run(String... args) throws Exception {
		/* StudentDto student1 = new StudentDto("Ramesh", "Fadatare", "ramesh@gmail.com");
		 studentRepository.save(student1);
		 
		 StudentDto student2 = new StudentDto("Sanjay", "Jadhav", "sanjay@gmail.com");
		 studentRepository.save(student2); 
		 
		 StudentDto student3 = new StudentDto("tony", "Fadatare", "tony@gmail.com");
		 studentRepository.save(student3); 
		*/
	}

}
