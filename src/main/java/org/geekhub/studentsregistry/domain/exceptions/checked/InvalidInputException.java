package org.geekhub.studentsregistry.domain.exceptions.checked;

import java.io.IOException;

public class InvalidInputException extends IOException {

    public InvalidInputException(String message) {
        super(message);
    }
}
