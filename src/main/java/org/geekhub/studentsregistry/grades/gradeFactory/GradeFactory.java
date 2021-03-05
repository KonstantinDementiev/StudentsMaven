package org.geekhub.studentsregistry.grades.gradeFactory;

import org.geekhub.studentsregistry.grades.grade.Grade;
import org.geekhub.studentsregistry.inputstudentsdata.GradeType;

public interface GradeFactory {
    Grade createGrade(GradeType gradeType, int value);
}
