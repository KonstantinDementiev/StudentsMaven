package org.geekhub.studentsregistry.domain.exceptions.checked;

import java.io.IOException;

public class InvalidStudentNameException extends IOException {

    public InvalidStudentNameException(String message) {
        super(message);
    }

}
