package net.javaguides.sms.controller;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.service.StudentService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class StudentController {

    private final StudentService studentService;

    private static final String REDIRECT_STUDENTS = "redirect:/students";

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "create_student";
    }

    
    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") @Valid Student student,
            BindingResult result) {
                if (result.hasErrors()) {
                    return "create_student";
                }
        studentService.saveStudent(student);
        return REDIRECT_STUDENTS;
    }

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "edit_student";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id,
                                 @ModelAttribute("student") Student student) {
        studentService.updateStudent(id, student);
        return REDIRECT_STUDENTS;
    }

    @PostMapping("/students/{id}/delete")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return REDIRECT_STUDENTS;
    }

    @GetMapping("/students/{id}")
    public @ResponseBody Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }
    
    @PutMapping(value = "/students/{id}")
    public @ResponseBody ResponseEntity<Student> updateStudentApi(@PathVariable Long id,
                                                                  @RequestBody @Valid Student student) {
        Student updated = studentService.updateStudent(id, student);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/students/{id}")
    public @ResponseBody ResponseEntity<Void> deleteStudentApi(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }
}
