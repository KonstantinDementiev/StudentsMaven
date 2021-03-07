package org.geekhub.studentsregistry.domain.grades.grade;

import org.geekhub.studentsregistry.domain.exceptions.unchecked.ScoreOutOfRangeException;

import java.util.Objects;

public class GradeLetter extends GradeWithValue {

    private final int value;

    public GradeLetter(int value) {
        super(value);
        this.value = value;
    }

    @Override
    public String asPrintVersion() {
        if (value < 0 || value > 100) {
            throw new ScoreOutOfRangeException("Grade score out of range. Please, enter a value between 0 and 100 inclusive");
        }
        if (value < 60) {
            return "F";
        } else if (value < 70) {
            return "D";
        } else if (value < 80) {
            return "C";
        } else if (value < 90) {
            return "B";
        } else {
            return "A";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradeLetter that = (GradeLetter) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

