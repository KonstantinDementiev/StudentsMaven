package org.geekhub.studentsregistry.domain.exceptions.checked;

public class GradeNotSupportedException extends InvalidInputException {

    public GradeNotSupportedException(String message) {
        super(message);
    }
}
