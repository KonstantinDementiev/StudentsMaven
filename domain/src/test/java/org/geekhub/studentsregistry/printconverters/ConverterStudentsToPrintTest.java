package org.geekhub.studentsregistry.printconverters;

import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.grades.grade.GradeGPA;
import org.geekhub.studentsregistry.students.Student;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;

public class ConverterStudentsToPrintTest {

    private final LocalDateTime DATE_TIME = LocalDateTime.of(2020, Month.DECEMBER, 11, 15, 30);
    private ConverterStudentsToPrint converter;
    private List<Student> studentsToPrint;

    @BeforeMethod
    public void setUp() {
        converter = new ConverterStudentsToPrint();
        studentsToPrint = new ArrayList<>();
        studentsToPrint.add(new Student(1,"Bob", new GradeGPA(98), DATE_TIME));
    }

    @Test
    public void when_input_students_list_is_null_then_print_empty_table() {
        List<String> actualList = converter.convertStudentsGroupToPrint(GradeType.GPA, null);
        Assert.assertEquals(actualList.get(4), "|--------------------|-------------|");
    }

    @Test
    public void when_input_grade_type_is_null_then_throw_exception() {
        assertThatCode(() ->
                converter.convertStudentsGroupToPrint(null, studentsToPrint)
        ).doesNotThrowAnyException();
    }

    @Test
    public void when_input_students_list_is_correct_then_print_all_lines() {
        List<String> actualList = converter.convertStudentsGroupToPrint(GradeType.GPA, studentsToPrint);
        Assert.assertEquals(actualList.size(), 6);
    }

    @Test
    public void when_input_students_list_is_correct_then_print_student_is_successfully() {
        List<String> actualList = converter.convertStudentsGroupToPrint(GradeType.GPA, studentsToPrint);
        Assert.assertEquals(actualList.get(4), "|Bob                 |     4.0     |");
    }


}
