package org.geekhub.studentsregistry.domain.grades.grade;

import org.geekhub.studentsregistry.domain.exceptions.unchecked.ScoreOutOfRangeException;

import java.util.Objects;

public class GradeUkraine extends GradeWithValue {

    private final int value;

    public GradeUkraine(int value) {
        super(value);
        this.value = value;
    }

    @Override
    public String asPrintVersion() {
        if (value < 0 || value > 100) {
            throw new ScoreOutOfRangeException("Grade score out of range. Please, enter a value between 0 and 100 inclusive");
        }
        if (value < 25) {
            return "1";
        } else if (value < 50) {
            return "2";
        } else if (value < 55) {
            return "3";
        } else if (value < 60) {
            return "4";
        } else if (value < 65) {
            return "5";
        } else if (value < 70) {
            return "6";
        } else if (value < 75) {
            return "7";
        } else if (value < 80) {
            return "8";
        } else if (value < 85) {
            return "9";
        } else if (value < 90) {
            return "10";
        } else if (value < 95) {
            return "11";
        } else {
            return "12";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradeUkraine that = (GradeUkraine) o;
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
