package org.geekhub.studentsregistry.domain.exceptions.unchecked;

public class InvalidGradeArgumentException extends IllegalArgumentException{

    public InvalidGradeArgumentException(String s) {
        super(s);
    }
}
