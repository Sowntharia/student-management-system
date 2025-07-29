package net.javaguides.sms.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
        student = new Student("Alice", "Wonder", "alice@example.com");
        student = studentRepository.save(student);
    }

    @Test
    void shouldReturnStudentsListViewWithModel() throws Exception {
        mockMvc.perform(get("/students"))
               .andExpect(status().isOk())
               .andExpect(view().name("students"))
               .andExpect(model().attributeExists("students"));
    }

    @Test
    void shouldDisplayCreateStudentForm() throws Exception {
        mockMvc.perform(get("/students/new"))
               .andExpect(status().isOk())
               .andExpect(view().name("create_student"))
               .andExpect(model().attributeExists("student"));
    }

    @Test
    void shouldCreateStudentAndRedirect() throws Exception {
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Tom")
                .param("lastName", "Holland")
                .param("email", "tom@example.com"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/students"));
    }

    @Test
    void shouldDisplayEditFormWithStudentData() throws Exception {
        mockMvc.perform(get("/students/edit/" + student.getId()))
               .andExpect(status().isOk())
               .andExpect(view().name("edit_student"))
               .andExpect(model().attributeExists("student"))
               .andExpect(model().attribute("student", hasProperty("firstName", is("Alice"))));
    }

    @Test
    void shouldUpdateStudentAndRedirect() throws Exception {
        mockMvc.perform(post("/students/" + student.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "AliceUpdated")
                .param("lastName", "Wonder")
                .param("email", "alice.updated@example.com"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/students"));
    }

    @Test
    void shouldDeleteStudentSuccessfully() throws Exception {
        mockMvc.perform(post("/students/" + student.getId() + "/delete"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/students"));
    }

    @Test
    void shouldReturn404ForInvalidStudentIdInEdit() throws Exception {
        mockMvc.perform(get("/students/edit/99999"))
               .andExpect(status().isNotFound());
    }

    @Test
    void shouldRejectInvalidStudentFormSubmission() throws Exception {
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "")
                .param("lastName", "")
                .param("email", "invalid"))
               .andExpect(status().isOk())
               .andExpect(view().name("create_student"))
               .andExpect(model().attributeHasFieldErrors("student", "firstName", "lastName", "email"));
    }
}
