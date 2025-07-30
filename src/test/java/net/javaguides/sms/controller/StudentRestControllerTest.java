package net.javaguides.sms.controller;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StudentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void testCreateStudent() throws Exception {
        Student mockStudent = new Student();
        mockStudent.setFirstName("API");
        mockStudent.setLastName("Test");
        mockStudent.setEmail("api@test.com");

        when(studentService.saveStudent(any(Student.class))).thenReturn(mockStudent);

        String json = """
            {
              "firstName": "API",
              "lastName": "Test",
              "email": "api@test.com"
            }
            """;

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("API"))
                .andExpect(jsonPath("$.lastName").value("Test"))
                .andExpect(jsonPath("$.email").value("api@test.com"));
    }
}
