package net.javaguides.sms;

import net.javaguides.sms.controller.StudentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class StudentManagementSystemApplicationTest extends BaseTestContainer {

    @Autowired
    private StudentController studentController;

    @Test
    void contextLoads() {
        assertNotNull(studentController, "StudentController should not be null");
    }
}
