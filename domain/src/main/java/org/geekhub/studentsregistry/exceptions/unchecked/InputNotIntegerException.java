package org.geekhub.studentsregistry.exceptions.unchecked;

public class InputNotIntegerException extends NumberFormatException{

    public InputNotIntegerException(String message) {
        super(message);
    }

}
