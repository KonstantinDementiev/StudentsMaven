package org.geekhub.studentsregistry.domain.exceptions.unchecked;

public class InputNotIntegerException extends NumberFormatException{

    public InputNotIntegerException(String message) {
        super(message);
    }

}
