package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.interfaces.Grade;
import org.geekhub.studentsregistry.exceptions.unchecked.ScoreOutOfRangeException;
import org.geekhub.studentsregistry.grades.grade.GradeUkraine;

import java.util.Comparator;
import java.util.Optional;

@Dependency
public class StudentsComparator implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {
        int index;
        if (studentsScore(s1) > studentsScore(s2)) {
            index = -1;
        } else if (studentsScore(s1) < studentsScore(s2)) {
            index = 1;
        } else {
            index = getStudentName(s1).compareToIgnoreCase(getStudentName(s2));
        }
        return index;
    }

    private int studentsScore(Student student) {
        int scores = getStudentScore(student);
        return isGradeTypeUkrainian(student) ? scores : getGradeLevel(scores);
    }

    private int getStudentScore(Student student) {
        Optional<Student> optionalStudent = Optional.ofNullable(student);
        return optionalStudent
                .map(Student::grade)
                .map(Grade::getValue)
                .orElse(0);
    }

    private boolean isGradeTypeUkrainian(Student student) {
        Optional<Student> optionalStudent = Optional.ofNullable(student);
        return optionalStudent
                .map(Student::grade)
                .map(Grade::getGrade)
                .filter(g -> g.equals(GradeUkraine.class))
                .isPresent();
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