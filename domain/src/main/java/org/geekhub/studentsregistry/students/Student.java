package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.interfaces.Grade;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public record Student(String name, Grade grade, LocalDateTime dateAndTimeOfExamPass) implements Serializable {

    @Override
    public String toString() {
        return "Student { " +
                "name = '" + name + '\'' + ",\n" +
                "gradeType = " + grade.getGrade().getSimpleName() + ",\n" +
                "gradeScore = " + grade.getValue() + ",\n" +
                "dateAndTimeOfExamPass = " + dateAndTimeOfExamPass
                .format(DateTimeFormatter.ofPattern("HH:mm:ss, dd.MM.yyyy")) + " }" +
                "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) &&
                Objects.equals(grade, student.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, grade);
    }
}
