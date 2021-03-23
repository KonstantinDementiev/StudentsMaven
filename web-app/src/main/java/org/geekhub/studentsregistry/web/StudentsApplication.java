package org.geekhub.studentsregistry.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EntityScan("org.geekhub.studentsregistry")
@SpringBootApplication(scanBasePackages = "org.geekhub.studentsregistry")
public class StudentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentsApplication.class, args);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

}
