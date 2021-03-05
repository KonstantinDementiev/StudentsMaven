package org.geekhub.studentsregistry.grades.gradeFactory;

import org.geekhub.studentsregistry.grades.grade.Grade;
import org.geekhub.studentsregistry.grades.grade.GradeLetter;
import org.geekhub.studentsregistry.grades.grade.GradePercent;
import org.geekhub.studentsregistry.grades.grade.GradePointAverage;
import org.geekhub.studentsregistry.inputstudentsdata.GradeType;

import java.util.Optional;

public class GradeFactoryImpl implements GradeFactory {

    @Override
    public Grade createGrade(GradeType gradeType, int value) {
        GradeType optionalGradeType = Optional.ofNullable(gradeType).orElseThrow(IllegalArgumentException::new);
        return getGradeValue(optionalGradeType, value);
    }

    private Grade getGradeValue(GradeType gradeType, int value) {
        return switch (gradeType) {
            case LETTER -> new GradeLetter(value);
            case PERCENTAGE -> new GradePercent(value);
            case GPA -> new GradePointAverage(value);
        };
    }
}