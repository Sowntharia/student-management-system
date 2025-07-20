package net.javaguides.sms.exception;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import net.javaguides.sms.service.StudentService;

@ActiveProfiles("test")
@SuppressWarnings("removal")
@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StudentService studentService;
	
	@Test
	void testStudentNotFoundException() throws Exception{
		when(studentService.getStudentById(1L))
		.thenThrow(new StudentNotFoundException(1L));
		
		mockMvc.perform(get("/students/1").accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isNotFound())
	       .andExpect(jsonPath("$.message").value("Student not found with ID: 1"));
 
		
	}

}
