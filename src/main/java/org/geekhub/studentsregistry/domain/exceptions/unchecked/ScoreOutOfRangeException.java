package org.geekhub.studentsregistry.domain.exceptions.unchecked;

public class ScoreOutOfRangeException extends IllegalArgumentException{

    public ScoreOutOfRangeException(String message) {
        super(message);
    }

}
