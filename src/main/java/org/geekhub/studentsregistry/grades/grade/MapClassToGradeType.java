package org.geekhub.studentsregistry.grades.grade;

import org.geekhub.studentsregistry.inputstudentsdata.GradeType;

import java.util.HashMap;
import java.util.Map;

public abstract class MapClassToGradeType {

    public static Map<Class<?>, GradeType> getGradeMap() {
        Map<Class<?>, GradeType> gradeMap = new HashMap<>();
        gradeMap.put(GradePointAverage.class, GradeType.GPA);
        gradeMap.put(GradeLetter.class, GradeType.LETTER);
        gradeMap.put(GradePercent.class, GradeType.PERCENTAGE);
        return gradeMap;
    }

}