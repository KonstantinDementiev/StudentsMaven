package org.geekhub.studentsregistry.exceptions.checked;

import java.io.IOException;

public class InvalidStartupArgumentException extends IOException {

    public InvalidStartupArgumentException(String message) {
        super(message);
    }
}
