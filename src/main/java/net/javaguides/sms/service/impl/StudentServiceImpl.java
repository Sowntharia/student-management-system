package net.javaguides.sms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.exception.StudentNotFoundException;
import net.javaguides.sms.repository.StudentRepository;
import net.javaguides.sms.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));

        existing.setFirstName(student.getFirstName());
        existing.setLastName(student.getLastName());
        existing.setEmail(student.getEmail());

        return studentRepository.save(existing);
    }

    @Override
    public void deleteStudentById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }
}
