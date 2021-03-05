package org.geekhub.studentsregistry.grades.grade;

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
