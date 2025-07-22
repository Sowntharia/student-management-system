package net.javaguides.sms.controller;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    private final StudentService studentService;

    private static final String REDIRECT_STUDENTS = "redirect:/students";

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Display list of students
    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    // Show form to create a new student
    @GetMapping("/students/new")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "create_student";
    }

    // Save new student
    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return REDIRECT_STUDENTS;
    }

    // Show edit form
    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "edit_student";
    }

    // Update student
    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id,
                                 @ModelAttribute("student") Student student) {
        studentService.updateStudent(id, student);
        return REDIRECT_STUDENTS;
    }

    // Delete student
    @PostMapping("/students/{id}/delete")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return REDIRECT_STUDENTS;
    }

    // REST API for testing
    @GetMapping("/students/{id}")
    public @ResponseBody Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }
}
