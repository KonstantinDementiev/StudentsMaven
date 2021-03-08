package org.geekhub.studentsregistry.students.printconverters;

import org.geekhub.studentsregistry.grades.grade.GradePointAverage;
import org.geekhub.studentsregistry.students.Student;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConverterStudentsToPrintTest {

    private final LocalDateTime DATE_TIME = LocalDateTime.of(2020, Month.DECEMBER, 11, 15, 30);
    private ConverterStudentsToPrint converter;
    private List<Student> studentsToPrint;

    @BeforeMethod
    public void setUp() {
        converter = new ConverterStudentsToPrint();
        studentsToPrint = new ArrayList<>();
        studentsToPrint.add(new Student("Bob", new GradePointAverage(98), DATE_TIME));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_input_students_list_is_null_then_throw_exception() {
        converter.convertStudentsToPrintFormat(null);
    }

    @Test
    public void when_input_students_list_is_correct_then_print_is_successfully() {
        List<HashMap<String, String>> actualMap = converter.convertStudentsToPrintFormat(studentsToPrint);
        List<HashMap<String, String>> expectMap = new ArrayList<>();
        HashMap<String, String> newStudent = new HashMap<>();
        newStudent.put("Name", "Bob");
        newStudent.put("Grade", "4.0");
        expectMap.add(newStudent);
        Assert.assertEquals(actualMap, expectMap);
    }


}
