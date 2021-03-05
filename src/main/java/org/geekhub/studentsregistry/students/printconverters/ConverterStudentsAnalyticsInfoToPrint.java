package org.geekhub.studentsregistry.students.printconverters;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.grades.grade.Grade;
import org.geekhub.studentsregistry.grades.gradeFactory.GradeFactory;
import org.geekhub.studentsregistry.grades.gradeFactory.GradeFactoryImpl;
import org.geekhub.studentsregistry.inputstudentsdata.GradeType;
import org.geekhub.studentsregistry.outputconsole.MapGradeTypeToTableName;
import org.geekhub.studentsregistry.students.Student;
import org.geekhub.studentsregistry.students.analyst.StudentsAnalyst;
import org.geekhub.studentsregistry.students.analyst.StudentsAnalystImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Dependency
public class ConverterStudentsAnalyticsInfoToPrint {

    private final GradeFactory gradeFactory = new GradeFactoryImpl();
    private final StudentsAnalyst studentsAnalyst = new StudentsAnalystImpl();

    public List<String> getPrintDataForOneGradeType(GradeType gradeType, List<Student> students){
        List<String> result = new ArrayList<>();
        result.add(printMaxScore(gradeType, students));
        result.add(printMinScore(gradeType, students));
        result.add(printMedianScore(gradeType, students));
        return result;
    }

    private String printMaxScore(GradeType gradeType, List<Student> students) {
        String scoreRepresentation = studentsAnalyst.maxScore(students)
                .map(this::getAllGradeNames).orElse("N/A");
        return printDataLine("Max", gradeType, scoreRepresentation);
    }

    private String printMinScore(GradeType gradeType, List<Student> students) {
        String scoreRepresentation = studentsAnalyst.minScore(students)
                .map(this::getAllGradeNames).orElse("N/A");
        return printDataLine("Min", gradeType, scoreRepresentation);
    }

    private String printMedianScore(GradeType gradeType, List<Student> students) {
        String scoreRepresentation = studentsAnalyst.medianScore(students)
                .map(this::getAllGradeNames).orElse("N/A");
        return printDataLine("Median", gradeType, scoreRepresentation);
    }

    private String printDataLine(String scoreName, GradeType gradeType, String gradesValues) {
        return scoreName + " score in '"
                + MapGradeTypeToTableName.getGradeTableName().get(gradeType)
                + "' category is: " + gradesValues;
    }

    private String getAllGradeNames(Integer score) {
        return Stream.of(GradeType.values())
                .map(gradeType -> gradeFactory.createGrade(gradeType, score))
                .map(Grade::asPrintVersion)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
