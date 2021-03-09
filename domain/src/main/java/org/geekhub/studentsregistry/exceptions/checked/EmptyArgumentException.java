package org.geekhub.studentsregistry.exceptions.checked;

public class EmptyArgumentException extends InvalidStartupArgumentException{

    public EmptyArgumentException(String message) {
        super(message);
    }
}
