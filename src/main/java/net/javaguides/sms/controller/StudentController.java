package net.javaguides.sms.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.service.StudentService;

@Controller
public class StudentController {
	
	private StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	
	
	//Display list of students
	
	@GetMapping("/students")
	public String listStudents(Model model) {
		model.addAttribute("students", studentService.getAllStudents());
		return "students";
		
	}
	
	//Show form to create a new student
	
	@GetMapping("/students/new")
	public String createStudentForm(Model model){
		
		//create student object to hold student form data
		StudentDto student = new StudentDto();
		model.addAttribute("student", student);
		return "create_student";
	}
	
	//Save student after form submission
	
	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") StudentDto studentDto) {
		
		studentService.saveStudent(studentDto);
		return "redirect:/students";
		
	}
	
	//Show form to edit an existing student
	
	@GetMapping("/students/edit/{id}")
	public String editStudentForm(@PathVariable Long id, Model model) {
		StudentDto studentDto = studentService.getStudentById(id);
		model.addAttribute("student", studentDto);
		return "edit_student";
	}
	
	//Update student after form submission
	
	@PostMapping("/students/{id}" )
	public String updateStudent(@PathVariable Long id,
			                    @ModelAttribute("student") StudentDto studentDto) {
		
	   //save updated student object
		studentService.updateStudent(id, studentDto);
        return "redirect:/students";	
		
	}
	
	//Securely delete a student 
	
	@PostMapping("/students/{id}/delete")
	public String deleteStudent(@PathVariable Long id) {
		studentService.deleteStudentById(id);
		return "redirect:/students";
		
	}
	
	// REST API to get student by ID (for testing purposes)
	@GetMapping("/students/{id}")
	public @ResponseBody StudentDto getStudentById(@PathVariable Long id) {
	    return studentService.getStudentById(id);
	}

}
