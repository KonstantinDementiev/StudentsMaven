package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.grades.grade.Grade;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Student(String name, Grade grade, LocalDateTime dateAndTimeOfExamPass) {

    @Override
    public String toString() {
        return "Student { " +
                "name = '" + name + '\'' +
                ", gradeType = " + grade.getGrade().toString() +
                ", gradeScore = " + grade.getValue() +
                ", dateAndTimeOfExamPass = " + dateAndTimeOfExamPass.format(DateTimeFormatter.ISO_DATE_TIME) +
                " }";
    }
}
