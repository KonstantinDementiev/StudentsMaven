package org.geekhub.studentsregistry.domain.students.printconverters;

import org.geekhub.studentsregistry.domain.grades.grade.GradePointAverage;
import org.geekhub.studentsregistry.domain.grades.grade.GradeType;
import org.geekhub.studentsregistry.domain.students.Student;
import org.geekhub.studentsregistry.domain.students.analyst.StudentsAnalyst;
import org.geekhub.studentsregistry.domain.students.analyst.StudentsAnalystImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ConverterStudentsAnalyticsInfoToPrintTest {

    private final LocalDateTime DATE_TIME = LocalDateTime.of(2020, Month.DECEMBER, 11, 15, 30);
    private ConverterStudentsAnalyticsInfoToPrint converter;
    private StudentsAnalyst studentsAnalyst;
    private List<Student> studentsToAnalyze;

    @BeforeMethod
    public void setUp() {
        converter = new ConverterStudentsAnalyticsInfoToPrint();
        studentsAnalyst = new StudentsAnalystImpl();
        studentsToAnalyze = new ArrayList<>();
        studentsToAnalyze.add(new Student("Tom", new GradePointAverage(90), DATE_TIME));
        studentsToAnalyze.add(new Student("Bob", new GradePointAverage(80), DATE_TIME));
        studentsToAnalyze.add(new Student("Jessy", new GradePointAverage(70), DATE_TIME));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_input_grade_type_is_null_then_throw_exception() {
        converter.getPrintDataForOneGradeType(null, studentsToAnalyze, studentsAnalyst);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_input_students_list_is_null_then_throw_exception() {
        converter.getPrintDataForOneGradeType(GradeType.GPA, null, studentsAnalyst);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_input_analyst_is_null_then_throw_exception() {
        converter.getPrintDataForOneGradeType(GradeType.GPA, studentsToAnalyze, null);
    }

    @Test
    public void when_input_is_correct_then_return_correct_list() {
        List<String> actualList = converter.getPrintDataForOneGradeType(
                GradeType.GPA, studentsToAnalyze, studentsAnalyst);
        Assert.assertEquals(actualList.size(), 3);
    }


}
