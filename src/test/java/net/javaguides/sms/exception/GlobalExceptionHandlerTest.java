package net.javaguides.sms.exception;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import net.javaguides.sms.service.StudentService;

@SuppressWarnings("removal")
@WebMvcTest
public class GlobalExceptionHandlerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StudentService studentService;
	
	@Test
	void testStudentNotFoundException() throws Exception{
		when(studentService.getStudentById(1L)).thenThrow(new StudentNotFoundException("Student not found"));
		
		mockMvc.perform(get("/students/1"))
		        .andExpect(status().isNotFound())
		        .andExpect(jsonPath("$.message").value("Student not found"));
		
	}

}
