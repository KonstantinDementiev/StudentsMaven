package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.grades.grade.MapClassToGradeType;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Dependency
public class StudentsFilterer {

    private static final Map<Class<?>, GradeType> gradeMap = MapClassToGradeType.getGradeMap();

    public Map<GradeType, List<Student>> groupStudentsByGrade(List<Student> studentsToGroup) {
        List<Student> optionalStudentsToGroup = Optional.ofNullable(studentsToGroup).orElse(new ArrayList<>());
        Map<GradeType, List<Student>> resultMap = new HashMap<>();
        for (GradeType gradeType : GradeType.values()) {
            resultMap.put(gradeType, createOneStudentGroup(optionalStudentsToGroup, gradeType));
        }
        return resultMap;
    }

    private List<Student> createOneStudentGroup(List<Student> studentsToGroup, GradeType gradeType) {
        List<Student> gradeGroup = new ArrayList<>();
        for (Student student : studentsToGroup) {
            if (isGradeTypeTheSame(gradeType, student)) {
                gradeGroup.add(student);
            }
        }
        return gradeGroup;
    }

    private boolean isGradeTypeTheSame(GradeType gropeGradeType, Student student) {
        GradeType studentGradeType = gradeMap.get(student.getGrade().getGrade());
        return gropeGradeType.equals(studentGradeType);
    }

}

