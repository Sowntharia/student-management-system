package net.javaguides.sms.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private StudentRepository studentRepository;
	
	private Student student;
	
	@BeforeEach
	void setUp() {
		studentRepository.deleteAll();
		student = new Student("Alice", "Wonder", "alice@example.com");
		student = studentRepository.save(student);
	}
	
	@Test
	void shouldReturnListOfStudents() throws Exception{
		mockMvc.perform(get("/students"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("students"))
		       .andExpect(model().attributeExists("students"));
	}
	
	@Test
	void shouldShowCreateForm() throws Exception{
		mockMvc.perform(get("/students/new"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("create_student"))
		       .andExpect(model().attributeExists("student"));
	}
	
	@Test
	void shouldCreateNewStudent() throws Exception{
		mockMvc.perform(post("/students")
		       .contentType(MediaType.APPLICATION_FORM_URLENCODED)
		       .param("firstName", "John")
		       .param("lastName", "Doe")
		       .param("email", "john@example.com"))
		      .andExpect(status().is3xxRedirection())
		      .andExpect(redirectedUrl("/students"));
	}
	
	@Test
	void shouldShowEditForm() throws Exception{
		mockMvc.perform(get("/students/edit/" + student.getId()))
		       .andExpect(status().isOk())
		       .andExpect(view().name("edit_student"))
		       .andExpect(model().attributeExists("student"))
		       .andExpect(model().attribute("student", hasProperty("firstName", is("Alice"))));
	}
	
	@Test
	void shouldUpdateStudent() throws Exception{
		mockMvc.perform(post("/students/" + student.getId())
			   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
			   .param("firstName", "Updated")
			   .param("lastName", "Name")
			   .param("email", "updated@example.com"))
			  .andExpect(status().is3xxRedirection())
			  .andExpect(redirectedUrl("/students"));
	}
	
	@Test
	void shouldDeletStudent() throws Exception {
		mockMvc.perform(post("/students/" + student.getId() + "/delete"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/students"));
	}
}
