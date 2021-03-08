package org.geekhub.studentsregistry.exceptions.checked;

public class GradeNotSupportedException extends InvalidInputException {

    public GradeNotSupportedException(String message) {
        super(message);
    }
}
