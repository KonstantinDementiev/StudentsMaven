package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.grades.grade.GradeLetter;
import org.geekhub.studentsregistry.grades.grade.GradePercentage;
import org.geekhub.studentsregistry.grades.grade.GradeGPA;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class StudentsCreatorTest {

    private final LocalDateTime DATE_TIME = LocalDateTime.of(2020, Month.DECEMBER, 11,15,30);
    private StudentsCreator studentsCreator;
    private List<List<String>> enteredArray;
    private int studentId;

    @BeforeMethod
    public void setUp() {
        studentsCreator = new StudentsCreator();
        enteredArray = new ArrayList<>();
        studentId = 1;
    }

    @Test
    public void When_InputNullArray_Then_ReturnEmptyArray() {
        List<Student> actualStudents = studentsCreator.createStudentsList(null, studentId);
        List<Student> expectStudents = new ArrayList<>();
        Assert.assertEquals(actualStudents, expectStudents);
    }

    @Test
    public void When_InputEmptyArray_Then_ReturnEmptyArray() {
        List<Student> actualStudents = studentsCreator.createStudentsList(enteredArray, studentId);
        List<Student> expectStudents = new ArrayList<>();
        Assert.assertEquals(actualStudents, expectStudents);
    }

    @Test
    public void When_InputOneStudent_Then_ReturnArrayWithLengthOne() {
        List<String> student = List.of("Zina", "80", "LETTER");
        enteredArray.add(student);
        List<Student> actualStudents = studentsCreator.createStudentsList(enteredArray, studentId);
        int actualArrayLength = actualStudents.size();
        Assert.assertEquals(actualArrayLength, 1);
    }

    @Test
    public void When_InputNotEmptyArray_Then_ReturnNotEmptyArray() {
        enteredArray.add(List.of("1", "Zina", "80", "LETTER"));
        enteredArray.add(List.of("2", "Anna", "80", "GPA"));
        enteredArray.add(List.of("3", "John", "80", "PERCENTAGE"));
        List<Student> actualStudents = studentsCreator.createStudentsList(enteredArray, studentId);
        List<Student> expectStudents = new ArrayList<>();
        expectStudents.add(new Student(1,"Zina", new GradeLetter(80), DATE_TIME));
        expectStudents.add(new Student(2,"Anna", new GradeGPA(80), DATE_TIME));
        expectStudents.add(new Student(3,"John", new GradePercentage(80), DATE_TIME));
        Assert.assertEquals(actualStudents, expectStudents);
    }
}
