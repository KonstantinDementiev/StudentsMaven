package org.geekhub.studentsregistry.converters;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.interfaces.Grade;
import org.geekhub.studentsregistry.grades.gradeFactory.GradeFactory;
import org.geekhub.studentsregistry.grades.gradeFactory.GradeFactoryImpl;
import org.geekhub.studentsregistry.students.Student;
import org.geekhub.studentsregistry.analytics.StudentsAnalyst;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Dependency
public class ConverterAnalyticsToPrint {

    private final GradeFactory gradeFactory = new GradeFactoryImpl();

    public List<String> getPrintDataForOneGradeType(GradeType gradeType,
                                                    List<Student> students,
                                                    StudentsAnalyst studentsAnalyst) {

        GradeType validGradeType = getValidGradeType(gradeType);
        List<Student> validStudentsList = getValidStudentsList(students);
        StudentsAnalyst validStudentsAnalyst = getValidStudentsAnalyst(studentsAnalyst);

        List<String> result = new ArrayList<>();
        result.add(printMaxScore(validGradeType, validStudentsList, validStudentsAnalyst));
        result.add(printMinScore(validGradeType, validStudentsList, validStudentsAnalyst));
        result.add(printMedianScore(validGradeType, validStudentsList, validStudentsAnalyst));
        return result;
    }

    private GradeType getValidGradeType(GradeType gradeType) {
        return Optional.ofNullable(gradeType).orElseThrow(()
                -> new IllegalArgumentException("Grade type is empty"));
    }

    private List<Student> getValidStudentsList(List<Student> students) {
        return Optional.ofNullable(students).orElseThrow(()
                -> new IllegalArgumentException("Students list is empty"));
    }

    private StudentsAnalyst getValidStudentsAnalyst(StudentsAnalyst studentsAnalyst) {
        return Optional.ofNullable(studentsAnalyst).orElseThrow(()
                -> new IllegalArgumentException("StudentsAnalyst instance is empty"));
    }

    private String printMaxScore(GradeType gradeType, List<Student> students, StudentsAnalyst studentsAnalyst) {
        String scoreRepresentation = studentsAnalyst.maxScore(students)
                .map(this::getAllGradeNames).orElse("N/A");
        return printDataLine("Max", gradeType, scoreRepresentation);
    }

    private String printMinScore(GradeType gradeType, List<Student> students, StudentsAnalyst studentsAnalyst) {
        String scoreRepresentation = studentsAnalyst.minScore(students)
                .map(this::getAllGradeNames).orElse("N/A");
        return printDataLine("Min", gradeType, scoreRepresentation);
    }

    private String printMedianScore(GradeType gradeType, List<Student> students, StudentsAnalyst studentsAnalyst) {
        String scoreRepresentation = studentsAnalyst.medianScore(students)
                .map(this::getAllGradeNames).orElse("N/A");
        return printDataLine("Median", gradeType, scoreRepresentation);
    }

    private String printDataLine(String scoreName, GradeType gradeType, String gradesValues) {
        return scoreName + " score in '"
                + gradeType.name()
                + "' category is: " + gradesValues;
    }

    private String getAllGradeNames(Integer score) {
        return Stream.of(GradeType.values())
                .map(gradeType -> gradeFactory.createGrade(gradeType, score))
                .map(Grade::asPrintVersion)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
