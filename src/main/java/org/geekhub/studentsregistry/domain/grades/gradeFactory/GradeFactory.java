package org.geekhub.studentsregistry.domain.grades.gradeFactory;

import org.geekhub.studentsregistry.domain.grades.grade.Grade;
import org.geekhub.studentsregistry.domain.grades.grade.GradeType;

public interface GradeFactory {
    Grade createGrade(GradeType gradeType, int value);
}
