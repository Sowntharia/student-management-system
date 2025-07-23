package net.javaguides.sms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StudentManagementSystemApplicationTest extends BaseTestContainer {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        // Valid assertion to satisfy SonarCloud and verify Spring Boot starts
        assertThat(context).isNotNull();
    }
}
