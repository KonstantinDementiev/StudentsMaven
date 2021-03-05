package org.geekhub.studentsregistry.exceptions.unchecked;

public class InvalidGradeArgumentException extends IllegalArgumentException{

    public InvalidGradeArgumentException(String s) {
        super(s);
    }
}
