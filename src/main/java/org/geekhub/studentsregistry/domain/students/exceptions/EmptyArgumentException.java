package org.geekhub.studentsregistry.domain.students.exceptions;

public class EmptyArgumentException extends InvalidStartupArgumentException{

    public EmptyArgumentException(String message) {
        super(message);
    }
}
