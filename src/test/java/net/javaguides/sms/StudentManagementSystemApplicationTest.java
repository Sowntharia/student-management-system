package net.javaguides.sms; 
import java.util.List; 
import org.junit.jupiter.api.Assertions; 
import org.junit.jupiter.api.Test; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.test.context.ActiveProfiles; 
import static org.junit.jupiter.api.Assertions.assertFalse; 
import net.javaguides.sms.entity.Student; 
import net.javaguides.sms.repository.StudentRepository; 
@ActiveProfiles("test") 
@SpringBootTest 
class StudentManagementSystemApplicationTest 
{     
	@Autowired     
	private StudentRepository studentRepository;     
	@Test     
	void testMain() 
	{                  
		System.setProperty("spring.profiles.active", "test");         
		StudentManagementSystemApplication.main(new String[]{});                 
		Assertions.assertTrue(true);     
	}     
	@Test     
	void contextLoadsAndInitializesStudents() {  
		
		List<Student> students = studentRepository.findAll();         
		assertFalse(students.isEmpty());     
		}
	}
