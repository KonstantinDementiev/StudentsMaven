package org.geekhub.studentsregistry.exceptions.checked;
public class ZeroArgumentException extends InvalidStartupArgumentException{

    public ZeroArgumentException(String message) {
        super(message);
    }
}
