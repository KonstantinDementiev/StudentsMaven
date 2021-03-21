package org.geekhub.studentsregistry.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("org.geekhub.studentsregistry.students")
@SpringBootApplication(scanBasePackages = "org.geekhub.studentsregistry")
public class StudentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentsApplication.class, args);
    }

}
