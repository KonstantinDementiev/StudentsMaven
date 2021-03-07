package org.geekhub.studentsregistry.domain.grades.grade;

import java.util.HashMap;
import java.util.Map;

public abstract class MapClassToGradeType {

    public static Map<Class<?>, GradeType> getGradeMap() {
        Map<Class<?>, GradeType> gradeMap = new HashMap<>();
        gradeMap.put(GradePointAverage.class, GradeType.GPA);
        gradeMap.put(GradeLetter.class, GradeType.LETTER);
        gradeMap.put(GradePercent.class, GradeType.PERCENTAGE);
        gradeMap.put(GradeUkraine.class, GradeType.UKRAINE);
        return gradeMap;
    }

}
