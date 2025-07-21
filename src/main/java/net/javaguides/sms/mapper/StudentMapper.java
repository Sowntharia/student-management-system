package net.javaguides.sms.mapper;

import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.entity.Student;

public class StudentMapper {

    // Convert Entity to DTO
    public static StudentDto mapToDto(Student student) {
        if (student == null) {
            return null;
        }

        return new StudentDto(
            student.getId(),
            student.getFirstName(),
            student.getLastName(),
            student.getEmail()
        );
    }

    // Convert DTO to Entity
    public static Student mapToEntity(StudentDto dto) {
        if (dto == null) {
            return null;
        }

        Student student = new Student();
        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        return student;
    }
    
    
}
