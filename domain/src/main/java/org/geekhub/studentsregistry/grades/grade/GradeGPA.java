package org.geekhub.studentsregistry.grades.grade;

import org.geekhub.studentsregistry.exceptions.unchecked.ScoreOutOfRangeException;

import java.util.Objects;

public class GradeGPA extends GradeWithValue {

    private final int value;

    public GradeGPA(int value) {
        super(value);
        this.value = value;
    }

    @Override
    public String asPrintVersion() {
        if (value < 0 || value > 100) {
            throw new ScoreOutOfRangeException("Grade score out of range. Please, enter a value between 0 and 100 inclusive");
        }
        if (value < 60) {
            return "0.0";
        } else if (value < 70) {
            return "1.0";
        } else if (value < 80) {
            return "2.0";
        } else if (value < 90) {
            return "3.0";
        } else {
            return "4.0";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradeGPA that = (GradeGPA) o;
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