package org.geekhub.studentsregistry.grades.grade;

import org.geekhub.studentsregistry.enums.GradeType;

import java.util.HashMap;
import java.util.Map;

public abstract class MapClassToGradeType {

    public static Map<Class<?>, GradeType> getGradeMap() {
        Map<Class<?>, GradeType> gradeMap = new HashMap<>();
        gradeMap.put(GradeGPA.class, GradeType.GPA);
        gradeMap.put(GradeLetter.class, GradeType.LETTER);
        gradeMap.put(GradePercentage.class, GradeType.PERCENTAGE);
        gradeMap.put(GradeUkraine.class, GradeType.UKRAINE);
        return gradeMap;
    }

}
