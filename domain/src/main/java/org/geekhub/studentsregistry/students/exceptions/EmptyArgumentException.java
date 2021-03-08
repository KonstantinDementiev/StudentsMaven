package org.geekhub.studentsregistry.students.exceptions;

public class EmptyArgumentException extends InvalidStartupArgumentException{

    public EmptyArgumentException(String message) {
        super(message);
    }
}
