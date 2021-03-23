package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.grades.grade.GradeLetter;
import org.geekhub.studentsregistry.grades.grade.GradeUkraine;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class StudentsComparatorTest {

    private final LocalDateTime DATE_TIME =
            LocalDateTime.of(2020, Month.DECEMBER, 11,15,30);
    private StudentsComparator studentsComparator;

    @BeforeMethod
    public void setUp() {
        studentsComparator = new StudentsComparator();
    }

    @Test
    public void When_FirstArgumentIsNull_Then_ReturnPositiveNumber() {
        Student s2 = new Student("Valera", new GradeLetter(80), DATE_TIME);
        assertEquals(studentsComparator.compare(null, s2), 1);
    }

    @Test
    public void When_SecondArgumentIsNull_Then_ReturnNegativeNumber() {
        Student s1 = new Student("Valera", new GradeLetter(80), DATE_TIME);
        assertEquals(studentsComparator.compare(s1, null), -1);
    }

    @Test
    public void When_FirstStudentHasMoreScore_Then_ReturnNegativeNumber() {
        Student s1 = new Student("Valera", new GradeLetter(80), DATE_TIME);
        Student s2 = new Student("Alena", new GradeLetter(70), DATE_TIME);
        assertEquals(studentsComparator.compare(s1, s2), -1);
    }

    @Test
    public void When_SecondStudentHasMoreScore_Then_ReturnPositiveNumber() {
        Student s1 = new Student("Valera", new GradeLetter(70), DATE_TIME);
        Student s2 = new Student("Alena", new GradeLetter(80), DATE_TIME);
        assertEquals(studentsComparator.compare(s1, s2), 1);
    }

    @Test
    public void When_BothStudentsHaveSameScore_Then_ReturnNegativeNumberIfFirstStudentFirstAlphabetically() {
        Student s1 = new Student("Alena", new GradeLetter(80), DATE_TIME);
        Student s2 = new Student("Valera", new GradeLetter(80), DATE_TIME);
        assertTrue(studentsComparator.compare(s1, s2) < 0);
    }

    @Test
    public void When_BothStudentsHaveSameScore_Then_ReturnPositiveNumberIfSecondStudentFirstAlphabetically() {
        Student s1 = new Student("Valera", new GradeUkraine(80), DATE_TIME);
        Student s2 = new Student("Alena", new GradeUkraine(80), DATE_TIME);
        assertTrue(studentsComparator.compare(s1, s2) > 0);
    }

    @Test
    public void When_BothStudentsHaveSameScoreAndSameName_Then_ReturnZero() {
        Student s1 = new Student("Valera", new GradeUkraine(80), DATE_TIME);
        Student s2 = new Student("Valera", new GradeUkraine(80), DATE_TIME);
        assertEquals(studentsComparator.compare(s1, s2), 0);
    }

    @Test
    public void When_BothStudentsHaveSameScoreAndSameNameButDifferentCase_Then_ReturnZero() {
        Student s1 = new Student("VALERA", new GradeUkraine(80), DATE_TIME);
        Student s2 = new Student("Valera", new GradeUkraine(80), DATE_TIME);
        assertEquals(studentsComparator.compare(s1, s2), 0);
    }

}
