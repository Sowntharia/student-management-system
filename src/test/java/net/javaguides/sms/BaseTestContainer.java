package net.javaguides.sms;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;


@SuppressWarnings("resource")
@SpringBootTest
@Testcontainers
public class BaseTestContainer {

	protected static final MySQLContainer<?> MY_SQL_CONTAINER;
	
	static {
		MY_SQL_CONTAINER = new MySQLContainer<>("mysql:latest")
			  .withDatabaseName("test_db")
	          .withUsername("testuser")
	          .withPassword("testpass");
		MY_SQL_CONTAINER.start();
	}
	          
	
	@DynamicPropertySource
	static void mysqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
		registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
		registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
		registry.add("spring.datasource.driver-class-name", MY_SQL_CONTAINER::getDriverClassName);
		registry.add("spring.jpa.hibernate.ddl-auto", ()-> "update");
		registry.add("spring.jpa.database-platform", ()-> "org.hibernate.dialect.MySQLDialect");
		
	}
}
