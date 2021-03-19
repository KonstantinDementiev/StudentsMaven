package org.geekhub.studentsregistry.grades.gradeFactory;

import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.interfaces.Grade;
import org.geekhub.studentsregistry.grades.grade.*;

import java.util.Optional;

public class GradeFactoryImpl implements GradeFactory {

    @Override
    public Grade createGrade(GradeType gradeType, int value) {
        GradeType optionalGradeType = Optional.ofNullable(gradeType)
                .orElseThrow(IllegalArgumentException::new);
        return getGradeValue(optionalGradeType, value);
    }

    private Grade getGradeValue(GradeType gradeType, int value) {
        return switch (gradeType) {
            case LETTER -> new GradeLetter(value);
            case PERCENTAGE -> new GradePercentage(value);
            case GPA -> new GradeGPA(value);
            case UKRAINE -> new GradeUkraine(value);
        };
    }
}