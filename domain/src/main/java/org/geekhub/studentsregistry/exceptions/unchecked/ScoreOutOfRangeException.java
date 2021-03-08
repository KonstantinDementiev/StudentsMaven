package org.geekhub.studentsregistry.exceptions.unchecked;

public class ScoreOutOfRangeException extends IllegalArgumentException{

    public ScoreOutOfRangeException(String message) {
        super(message);
    }

}
