package net.javaguides.sms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.service.StudentService;

public class StudentControllerTest {
    
	@Mock
	private StudentService studentService;
	
	@InjectMocks
	private StudentController studentController;
	
	private MockMvc mockMvc;
	
	private StudentDto studentDto;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		
		//Add ThymeleafViewResolver mock
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".html");
		
		mockMvc = MockMvcBuilders.standaloneSetup(studentController).setViewResolvers(viewResolver).build();
		studentDto = new StudentDto(1L, "John", "Doe", "john@example.com");
	}
	
	@Test
	void shouldListAllStudents()throws Exception {
		given(studentService.getAllStudents()).willReturn(Arrays.asList(studentDto));
		
		mockMvc.perform(get("/students"))
		.andExpect(status().isOk())
		.andExpect(view().name("students"))
		.andExpect(model().attributeExists("students"));
	}
	
	@Test
	void shouldShowCreateStudentForm() throws Exception{
		mockMvc.perform(get("/students/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("create_student"))
		.andExpect(model().attributeExists("student"));
	}
	
	@Test
	void shouldSaveStudent() throws Exception {
		mockMvc.perform(post("/students")
		.param("firstName", "John")
		.param("lastName", "Doe")
		.param("email", "john@example.com"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/students"));
		
		verify(studentService).saveStudent(any(StudentDto.class));
		
	}
	
	@Test
	void shouldShowEditForm() throws Exception{
		given(studentService.getStudentById(1L)).willReturn(studentDto);
		
		mockMvc.perform(get("/students/edit/1"))
		.andExpect(status().isOk())
		.andExpect(view().name("edit_student"))
		.andExpect(model().attributeExists("student"));
	}
	
	@Test
	void shouldUpdateStudent() throws Exception {
		given(studentService.getStudentById(1L)).willReturn(studentDto);
		
		mockMvc.perform(post("/students/1")
				.param("firstName", "Updated")
				.param("lastName", "Name")
				.param("email", "updated@example.com"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/students"));
		
		verify(studentService).updateStudent(any(Long.class),any(StudentDto.class));
	}
	
	@Test
	void shouldDeleteStudent() throws Exception {
		doNothing().when(studentService).deleteStudentById(1L);
		
		mockMvc.perform(post("/students/1/delete"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/students"));
		
		verify(studentService).deleteStudentById(1L);
	}
	
	
	
}
