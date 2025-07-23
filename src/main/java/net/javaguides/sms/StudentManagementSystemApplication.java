package net.javaguides.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StudentManagementSystemApplication {

    public static void main(String[] args) {
        @SuppressWarnings("unused")
		ConfigurableApplicationContext context = SpringApplication.run(StudentManagementSystemApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
