package net.javaguides.sms;

import net.javaguides.sms.controller.StudentController;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test") // Ensures application-test.properties is used
@SpringBootTest
class StudentManagementSystemApplicationTest {

    @Autowired
    private StudentController studentController;

    @MockBean
    private StudentService studentService;

    @Test
    void contextLoads() {
        // Assert that controller bean is loaded
        assertNotNull(studentController, "StudentController should not be null");

        // Provide mock student data for service
        given(studentService.getAllStudents()).willReturn(Arrays.asList(
                new Student("John", "Doe", "john.doe@example.com"),
                new Student("Jane", "Smith", "jane.smith@example.com")
        ));
    }
}
