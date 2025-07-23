package net.javaguides.sms;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class StudentManagementSystemApplicationTest {

    @Test
    void shouldRunCommandLineRunnerAndSaveSampleStudents() throws Exception {
        StudentRepository mockRepository = mock(StudentRepository.class);
        StudentManagementSystemApplication app = new StudentManagementSystemApplication();

        // Call the run method manually
        app.run(mockRepository).run(new String[]{});

        // Capture what was saved
        ArgumentCaptor<List<Student>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockRepository).saveAll(captor.capture());

        List<Student> saved = captor.getValue();
        assertThat(saved).hasSize(3);
        assertThat(saved).extracting(Student::getEmail)
                         .containsExactlyInAnyOrder("ranesh@gmail.com", "sanjay@gmail.com", "tony@gmail.com");
    }
}
