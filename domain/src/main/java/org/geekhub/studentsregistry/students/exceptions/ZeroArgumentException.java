package org.geekhub.studentsregistry.students.exceptions;
public class ZeroArgumentException extends InvalidStartupArgumentException{

    public ZeroArgumentException(String message) {
        super(message);
    }
}
