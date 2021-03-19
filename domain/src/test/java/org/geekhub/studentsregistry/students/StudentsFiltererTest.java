
package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.grades.grade.GradeGPA;
import org.geekhub.studentsregistry.grades.grade.GradeLetter;
import org.geekhub.studentsregistry.grades.grade.GradePercentage;
import org.geekhub.studentsregistry.enums.GradeType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.testng.Assert.assertEquals;

public class StudentsFiltererTest {

    private final LocalDateTime DATE_TIME = LocalDateTime.of(2020, Month.DECEMBER, 11,15,30);
    private StudentsFilterer studentsFilterer;
    private List<Student> studentsToFilter;
    private Map<GradeType, List<Student>> filteredStudents;

    @BeforeMethod
    public void setUp() {
        studentsFilterer = new StudentsFilterer();
        studentsToFilter = new ArrayList<>();
        filteredStudents = new HashMap<>();
    }

    @Test
    public void filtered_students_are_empty_lists_when_null_input_array() {
        filteredStudents = studentsFilterer.groupStudentsByGrade(null);
        Map<GradeType, List<Student>> expectResult = new HashMap<>();
        for(int i = 0; i < GradeType.values().length; i++){
            expectResult.put(GradeType.values()[i], new ArrayList<>());
        }
        assertEquals(expectResult, filteredStudents);
    }

    @Test
    public void filtered_students_are_empty_if_no_students_with_required_grade_type() {
        studentsToFilter.add(new Student(1,"Tom", new GradeLetter(95), DATE_TIME));
        studentsToFilter.add(new Student(2,"Bob", new GradePercentage(80), DATE_TIME));
        studentsToFilter.add(new Student(3,"Alan", new GradeLetter(88), DATE_TIME));
        filteredStudents = studentsFilterer.groupStudentsByGrade(studentsToFilter);
        List<Student> expectedStudents = Collections.emptyList();
        assertEquals(filteredStudents.get(GradeType.GPA), expectedStudents);
    }

    @Test
    public void filtered_students_are_only_with_required_grade_type() {
        studentsToFilter.add(new Student(1,"Tom", new GradeLetter(95), DATE_TIME));
        studentsToFilter.add(new Student(2,"Bob", new GradeLetter(80), DATE_TIME));
        filteredStudents = studentsFilterer.groupStudentsByGrade(studentsToFilter);
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1,"Tom", new GradeLetter(95), DATE_TIME));
        expectedStudents.add(new Student(2,"Bob", new GradeLetter(80), DATE_TIME));
        assertEquals(filteredStudents.get(GradeType.LETTER), expectedStudents);
    }

    @Test
    public void filtered_students_are_only_with_required_grade_type_when_input_contain_different_grade_types() {
        studentsToFilter.add(new Student(1,"Tom", new GradeLetter(95), DATE_TIME));
        studentsToFilter.add(new Student(2,"Bob", new GradePercentage(80), DATE_TIME));
        studentsToFilter.add(new Student(3,"Alan", new GradeLetter(88), DATE_TIME));
        studentsToFilter.add(new Student(4,"John", new GradeGPA(88), DATE_TIME));
        filteredStudents = studentsFilterer.groupStudentsByGrade(studentsToFilter);
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1,"Tom", new GradeLetter(95), DATE_TIME));
        expectedStudents.add(new Student(3,"Alan", new GradeLetter(88), DATE_TIME));
        assertEquals(filteredStudents.get(GradeType.LETTER), expectedStudents);
    }

    @Test
    public void students_filtered_by_LETTER_grade_type() {
        studentsToFilter.add(new Student(1,"Tom", new GradeLetter(95), DATE_TIME));
        studentsToFilter.add(new Student(2,"Bill", new GradeGPA(95), DATE_TIME));
        studentsToFilter.add(new Student(3,"Bob", new GradePercentage(80), DATE_TIME));
        studentsToFilter.add(new Student(4,"Alan", new GradeLetter(88), DATE_TIME));
        studentsToFilter.add(new Student(5,"Zara", new GradePercentage(88), DATE_TIME));
        studentsToFilter.add(new Student(6,"John", new GradeGPA(88), DATE_TIME));
        filteredStudents = studentsFilterer.groupStudentsByGrade(studentsToFilter);
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1,"Tom", new GradeLetter(95), DATE_TIME));
        expectedStudents.add(new Student(4,"Alan", new GradeLetter(88), DATE_TIME));
        assertEquals(filteredStudents.get(GradeType.LETTER), expectedStudents);

    }

    @Test
    public void students_filtered_by_PERCENTAGE_grade_type() {
        studentsToFilter.add(new Student(1,"Tom", new GradeLetter(95), DATE_TIME));
        studentsToFilter.add(new Student(2,"Bill", new GradeGPA(95), DATE_TIME));
        studentsToFilter.add(new Student(3,"Bob", new GradePercentage(80), DATE_TIME));
        studentsToFilter.add(new Student(4,"Alan", new GradeLetter(88), DATE_TIME));
        studentsToFilter.add(new Student(5,"Zara", new GradePercentage(88), DATE_TIME));
        studentsToFilter.add(new Student(6,"John", new GradeGPA(88), DATE_TIME));
        filteredStudents = studentsFilterer.groupStudentsByGrade(studentsToFilter);
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(3,"Bob", new GradePercentage(80), DATE_TIME));
        expectedStudents.add(new Student(5,"Zara", new GradePercentage(88), DATE_TIME));
        assertEquals(filteredStudents.get(GradeType.PERCENTAGE), expectedStudents);
    }

    @Test
    public void students_filtered_by_GPA_grade_type() {
        studentsToFilter.add(new Student(1,"Tom", new GradeLetter(95), DATE_TIME));
        studentsToFilter.add(new Student(2,"Bill", new GradeGPA(95), DATE_TIME));
        studentsToFilter.add(new Student(3,"Bob", new GradePercentage(80), DATE_TIME));
        studentsToFilter.add(new Student(4,"Alan", new GradeLetter(88), DATE_TIME));
        studentsToFilter.add(new Student(5,"Zara", new GradePercentage(88), DATE_TIME));
        studentsToFilter.add(new Student(6,"John", new GradeGPA(88), DATE_TIME));
        filteredStudents = studentsFilterer.groupStudentsByGrade(studentsToFilter);
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(2,"Bill", new GradeGPA(95), DATE_TIME));
        expectedStudents.add(new Student(6,"John", new GradeGPA(88), DATE_TIME));
        assertEquals(filteredStudents.get(GradeType.GPA), expectedStudents);
    }


}
