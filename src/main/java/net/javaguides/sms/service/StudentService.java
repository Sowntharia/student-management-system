package net.javaguides.sms.service;

import java.util.List;

import net.javaguides.sms.dto.StudentDto;

public interface StudentService {
    List<StudentDto> getAllStudents();
    
    StudentDto saveStudent(StudentDto studentDto);
    
    StudentDto getStudentById(Long id);
    
    StudentDto updateStudent(Long id,StudentDto studentDto);
    
    void deleteStudentById(Long id);
    
}
