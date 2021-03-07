package org.geekhub.studentsregistry.domain.students.exceptions;

import java.io.IOException;

public class InvalidStartupArgumentException extends IOException {

    public InvalidStartupArgumentException(String message) {
        super(message);
    }
}
