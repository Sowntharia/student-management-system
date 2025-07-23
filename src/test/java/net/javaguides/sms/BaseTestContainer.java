package net.javaguides.sms;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SuppressWarnings("resource")
@SpringBootTest
@Testcontainers
public abstract class BaseTestContainer {

    // Define container with specific version
    protected static final MySQLContainer<?> MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer<>("mysql:8.0.36")
                .withDatabaseName("test_db")
                .withUsername("testuser")
                .withPassword("testpass");
        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        String jdbcUrlWithParams = MY_SQL_CONTAINER.getJdbcUrl() + "?allowPublicKeyRetrieval=true&useSSL=false";

        registry.add("spring.datasource.url", () -> jdbcUrlWithParams);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driver-class-name", MY_SQL_CONTAINER::getDriverClassName);

        // Optional Hibernate / JPA settings
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.MySQLDialect");
    }
}
