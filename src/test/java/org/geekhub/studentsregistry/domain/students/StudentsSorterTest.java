package org.geekhub.studentsregistry.domain.students;

import org.geekhub.studentsregistry.domain.grades.grade.GradeLetter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotSame;

public class StudentsSorterTest {

    private final LocalDateTime DATE_TIME = LocalDateTime.of(2020, Month.DECEMBER, 11,15,30);
    private List<Student> studentsToSortWithUniqueValues;
    private List<Student> studentsToSortWithSameValues;
    private List<Student> sortedStudents;
    private List<Student> expectedStudents;

    @BeforeMethod
    public void fillingStartStudentsArray() {
        studentsToSortWithUniqueValues = new ArrayList<>();
        studentsToSortWithUniqueValues.add(new Student("Tom", new GradeLetter(95), DATE_TIME));
        studentsToSortWithUniqueValues.add(new Student("Bob", new GradeLetter(80), DATE_TIME));
        studentsToSortWithUniqueValues.add(new Student("Alan", new GradeLetter(88), DATE_TIME));
    }

    @Test
    public void sorted_students_are_empty_when_input_students_is_null() {
        StudentsSorter studentsSorter = new StudentsSorter();
        sortedStudents = studentsSorter.sortStudentsByScoreAndName(null);
        expectedStudents = new ArrayList<>();
        assertEquals(sortedStudents, expectedStudents);
    }

    @Test
    public void sorted_students_are_not_same_array_as_empty_input_students() {
        StudentsSorter studentsSorter = new StudentsSorter();
        sortedStudents = studentsSorter.sortStudentsByScoreAndName(studentsToSortWithUniqueValues);
        List<Student> emptyStudents = new ArrayList<>();
        assertNotSame(sortedStudents, emptyStudents);
    }

    @Test
    public void sorted_students_are_not_same_array_as_not_empty_input_students() {
        StudentsSorter studentsSorter = new StudentsSorter();
        sortedStudents = studentsSorter.sortStudentsByScoreAndName(studentsToSortWithUniqueValues);
        assertNotSame(sortedStudents, studentsToSortWithUniqueValues);
    }

    @Test
    public void students_sorted_with_only_one_student_in_input() {
        List<Student> oneStudent = new ArrayList<>();
        oneStudent.add(studentsToSortWithUniqueValues.get(0));
        StudentsSorter studentsSorter = new StudentsSorter();
        sortedStudents = studentsSorter.sortStudentsByScoreAndName(oneStudent);
        assertEquals(sortedStudents, oneStudent);
    }

    @Test
    public void students_sorted_by_grades_with_unique_values() {
        expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student("Tom", new GradeLetter(95), DATE_TIME));
        expectedStudents.add(new Student("Alan", new GradeLetter(88), DATE_TIME));
        expectedStudents.add(new Student("Bob", new GradeLetter(80), DATE_TIME));
        StudentsSorter studentsSorter = new StudentsSorter();
        sortedStudents = studentsSorter.sortStudentsByScoreAndName(studentsToSortWithUniqueValues);
        assertEquals(sortedStudents, expectedStudents);
    }

    @Test
    public void students_sorted_by_grade_with_same_values() {
        studentsToSortWithSameValues = new ArrayList<>();
        studentsToSortWithSameValues.add(new Student("Tom", new GradeLetter(95), DATE_TIME));
        studentsToSortWithSameValues.add(new Student("Bob", new GradeLetter(95), DATE_TIME));
        studentsToSortWithSameValues.add(new Student("Alan", new GradeLetter(95), DATE_TIME));
        expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student("Alan", new GradeLetter(95), DATE_TIME));
        expectedStudents.add(new Student("Bob", new GradeLetter(95), DATE_TIME));
        expectedStudents.add(new Student("Tom", new GradeLetter(95), DATE_TIME));
        StudentsSorter studentsSorter = new StudentsSorter();
        sortedStudents = studentsSorter.sortStudentsByScoreAndName(studentsToSortWithSameValues);
        assertEquals(sortedStudents, expectedStudents);
    }

    @AfterMethod
    public void tearDown() {
        studentsToSortWithUniqueValues = null;
        studentsToSortWithSameValues = null;
        sortedStudents = null;
        expectedStudents = null;
    }
}
