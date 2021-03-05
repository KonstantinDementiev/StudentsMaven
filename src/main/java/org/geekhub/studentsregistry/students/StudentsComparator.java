package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.exceptions.unchecked.ScoreOutOfRangeException;
import org.geekhub.studentsregistry.grades.grade.Grade;

import java.util.Comparator;
import java.util.Optional;

@Dependency
public class StudentsComparator implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {

        int student1Score = getStudentScore(s1);
        int student2Score = getStudentScore(s2);
        String student1Name = getStudentName(s1);
        String student2Name = getStudentName(s2);
        int index;

        if (getGradeLevel(student1Score) > getGradeLevel(student2Score)) {
            index = -1;
        } else if (getGradeLevel(student1Score) < getGradeLevel(student2Score)) {
            index = 1;
        } else {
            index = student1Name.compareToIgnoreCase(student2Name);
        }
        return index;
    }

    private int getStudentScore(Student student) {
        Optional<Student> optionalStudent = Optional.ofNullable(student);
        return optionalStudent
                .map(Student::grade)
                .map(Grade::getValue)
                .orElse(0);
    }

    private String getStudentName(Student student) {
        Optional<Student> optionalStudent = Optional.ofNullable(student);
        return optionalStudent
                .map(Student::name)
                .orElse("Unknown name");
    }

    private int getGradeLevel(int score) {
        if (score < 0 || score > 100) {
            throw new ScoreOutOfRangeException("Grade score out of range. Please, enter a value between 0 and 100 inclusive");
        } else if (score < 60) {
            return 1;
        } else if (score < 70) {
            return 2;
        } else if (score < 80) {
            return 3;
        } else if (score < 90) {
            return 4;
        } else
            return 5;
    }

}