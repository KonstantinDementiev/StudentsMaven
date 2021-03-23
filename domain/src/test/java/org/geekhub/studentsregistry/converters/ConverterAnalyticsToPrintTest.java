package org.geekhub.studentsregistry.converters;

import org.geekhub.studentsregistry.grades.grade.GradeGPA;
import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.students.Student;
import org.geekhub.studentsregistry.analytics.StudentsAnalyst;
import org.geekhub.studentsregistry.analytics.StudentsAnalystImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ConverterAnalyticsToPrintTest {

    private final LocalDateTime DATE_TIME = LocalDateTime.of(2020, Month.DECEMBER, 11, 15, 30);
    private ConverterAnalyticsToPrint converter;
    private StudentsAnalyst studentsAnalyst;
    private List<Student> studentsToAnalyze;

    @BeforeMethod
    public void setUp() {
        converter = new ConverterAnalyticsToPrint();
        studentsAnalyst = new StudentsAnalystImpl();
        studentsToAnalyze = new ArrayList<>();
        studentsToAnalyze.add(new Student("Tom", new GradeGPA(90), DATE_TIME));
        studentsToAnalyze.add(new Student("Bob", new GradeGPA(80), DATE_TIME));
        studentsToAnalyze.add(new Student("Jessy", new GradeGPA(70), DATE_TIME));
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
