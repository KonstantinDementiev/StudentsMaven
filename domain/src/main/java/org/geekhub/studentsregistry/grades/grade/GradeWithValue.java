package org.geekhub.studentsregistry.grades.grade;


import org.geekhub.studentsregistry.interfaces.Grade;

import java.io.Serializable;

public abstract class GradeWithValue implements Grade {

    private final int value;

    public GradeWithValue(int value) {
        this.value = value;
    }

    public Class<?> getGrade() {
        return this.getClass();
    }

    public Integer getValue() {
        return value;
    }
}
