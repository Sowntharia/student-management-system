package net.javaguides.sms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.exception.StudentNotFoundException;
import net.javaguides.sms.repository.StudentRepository;
import net.javaguides.sms.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	
	public StudentServiceImpl(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}

	@Override
	public List<StudentDto> getAllStudents() {
		return studentRepository.findAll()
				.stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public StudentDto saveStudent(StudentDto dto) {
		Student student = mapToEntity(dto);
		Student saved = studentRepository.save(student);
		return mapToDto(saved);
	}
	
	@Override
	public StudentDto getStudentById(Long id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException(id));
		return mapToDto(student);
		
	}

	@Override
	public StudentDto updateStudent(Long id, StudentDto dto) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException(id));
		
		student.setFirstName(dto.getFirstName());
		student.setLastName(dto.getLastName());
		student.setEmail(dto.getEmail());
		
		Student updated = studentRepository.save(student);
		return mapToDto(updated);
	}

	@Override
	public void deleteStudentById(Long id) {
		if (!studentRepository.existsById(id)) {
		    throw new StudentNotFoundException(id);
		}
		studentRepository.deleteById(id);
	}

	
	private StudentDto mapToDto(Student student) {
		return new StudentDto(
				student.getId(),
				student.getFirstName(),
				student.getLastName(),
				student.getEmail()
				);
	}
	
	private Student mapToEntity(StudentDto dto) {
		Student student = new Student();
		student.setId(dto.getId());
		student.setFirstName(dto.getFirstName());
		student.setLastName(dto.getLastName());
		student.setEmail(dto.getEmail());
		return student;
		
	}




}
