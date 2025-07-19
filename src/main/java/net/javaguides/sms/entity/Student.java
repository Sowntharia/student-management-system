package net.javaguides.sms.entity;


import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;



@Entity
@Table(name = "students")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank(message = "First name is required")
	@Column(name ="first_name", nullable = false)
    private String firstName;
	
	@NotBlank(message = "Last name is required")
	@Column(name ="last_name")
    private String lastName;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Email shoulld be valid")
	@Column(name ="email")
    private String email;
    
    public Student() {
    	
    }
    
	public Student(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
	//Debugging & logging
	@Override
	public String toString() {
		return"Student{" +
	           "id=" + id +
	           ", firstName='" + firstName + '\'' +
	           ", lastName='" + lastName + '\'' +
	           ", email='" + email + '\'' +
	           '}';
	}
	
	//Entity equality
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Student)) return false;
		Student student = (Student) o;
		return Objects.equals(id, student.id);
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	
	}
	
    
}
