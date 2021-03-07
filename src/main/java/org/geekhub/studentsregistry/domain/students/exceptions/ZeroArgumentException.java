package org.geekhub.studentsregistry.domain.students.exceptions;
public class ZeroArgumentException extends InvalidStartupArgumentException{

    public ZeroArgumentException(String message) {
        super(message);
    }
}
