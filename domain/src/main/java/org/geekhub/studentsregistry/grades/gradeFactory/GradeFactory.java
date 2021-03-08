package org.geekhub.studentsregistry.grades.gradeFactory;

import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.interfaces.Grade;

public interface GradeFactory {
    Grade createGrade(GradeType gradeType, int value);
}
