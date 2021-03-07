package org.geekhub.studentsregistry.domain.grades.grade;

import java.io.Serializable;

public abstract class GradeWithValue implements Grade, Serializable {

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
