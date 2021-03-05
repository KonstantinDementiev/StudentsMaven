package org.geekhub.studentsregistry.grades.grade;

import org.geekhub.studentsregistry.exceptions.unchecked.ScoreOutOfRangeException;

import java.util.Objects;

public class GradePercent extends GradeWithValue {

    private final int value;

    public GradePercent(int value) {
        super(value);
        this.value = value;
    }

    @Override
    public String asPrintVersion() {
        if (value < 0 || value > 100) {
            throw new ScoreOutOfRangeException("Grade score out of range. Please, enter a value between 0 and 100 inclusive");
        }
        if (value < 60) {
            return "< 60%";
        } else if (value < 70) {
            return "60 - 69%";
        } else if (value < 80) {
            return "70 - 79%";
        } else if (value < 90) {
            return "80 - 89%";
        } else {
            return "90 - 100%";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradePercent that = (GradePercent) o;
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
