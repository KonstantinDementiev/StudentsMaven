package org.geekhub.studentsregistry.analytics;

import org.geekhub.studentsregistry.grades.grade.GradeLetter;
import org.geekhub.studentsregistry.grades.grade.GradePercentage;
import org.geekhub.studentsregistry.students.Student;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertEquals;

public class StudentsAnalystImplTest {

    private final LocalDateTime DATE_TIME = LocalDateTime.of(2020, Month.DECEMBER, 11, 15, 30);
    private List<Student> enteredArray;
    private StudentsAnalystImpl studentsAnalystImpl;

    @BeforeMethod
    public void setUp() {
        studentsAnalystImpl = new StudentsAnalystImpl();
        enteredArray = new ArrayList<>();
    }

    @Test
    public void When_Input_Null_Then_MaxScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalystImpl.maxScore(null);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_EmptyList_Then_MaxScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalystImpl.maxScore(enteredArray);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_OneStudentList_Then_MaxScore_Return_ThisScoreValue() {
        enteredArray.add(new Student(1, "Tom", new GradeLetter(95), DATE_TIME));
        int actualScore = studentsAnalystImpl.maxScore(enteredArray).orElse(0);
        assertEquals(actualScore, 95);
    }

    @Test
    public void When_Input_TwoStudentList_Then_MaxScore_Return_MaxScoreValue() {
        enteredArray.add(new Student(1, "Tom", new GradeLetter(95), DATE_TIME));
        enteredArray.add(new Student(2, "Zina", new GradeLetter(80), DATE_TIME));
        int actualScore = studentsAnalystImpl.maxScore(enteredArray).orElse(0);
        assertEquals(actualScore, 95);
    }

    @Test
    public void When_Input_TwoStudentListWithDifferentGradeType_Then_MaxScore_Return_MaxScoreValue() {
        enteredArray.add(new Student(1, "Tom", new GradeLetter(94), DATE_TIME));
        enteredArray.add(new Student(2, "Zina", new GradePercentage(80), DATE_TIME));
        int actualScore = studentsAnalystImpl.maxScore(enteredArray).orElse(0);
        assertEquals(actualScore, 94);
    }


    @Test
    public void When_Input_Null_Then_MinScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalystImpl.minScore(null);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_EmptyList_Then_MinScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalystImpl.minScore(enteredArray);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_OneStudentList_Then_MinScore_Return_ThisScoreValue() {
        enteredArray.add(new Student(1, "Tom", new GradeLetter(95), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalystImpl.minScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 95);
    }

    @Test
    public void When_Input_TwoStudentList_Then_MinScore_Return_MinScoreValue() {
        enteredArray.add(new Student(1, "Tom", new GradeLetter(95), DATE_TIME));
        enteredArray.add(new Student(2, "Zina", new GradeLetter(80), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalystImpl.minScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 80);
    }

    @Test
    public void When_Input_TwoStudentListWithDifferentGradeType_Then_MinScore_Return_MinScoreValue() {
        enteredArray.add(new Student(1, "Tom", new GradeLetter(94), DATE_TIME));
        enteredArray.add(new Student(2, "Zina", new GradePercentage(80), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalystImpl.minScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 80);
    }


    @Test
    public void When_Input_Null_Then_AverageScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalystImpl.averageScore(null);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_EmptyList_Then_AverageScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalystImpl.averageScore(enteredArray);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_OneStudentList_Then_AverageScore_Return_ThisScoreValue() {
        enteredArray.add(new Student(1, "Tom", new GradeLetter(95), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalystImpl.averageScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 95);
    }

    @Test
    public void When_Input_TwoStudentList_Then_AverageScore_Return_AverageScoreValue() {
        enteredArray.add(new Student(1, "Tom", new GradeLetter(95), DATE_TIME));
        enteredArray.add(new Student(2, "Zina", new GradeLetter(80), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalystImpl.averageScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, (80 + 95) / 2);
    }

    @Test
    public void When_Input_TwoStudentListWithDifferentGradeType_Then_AverageScore_Return_AverageScoreValue() {
        enteredArray.add(new Student(1, "Tom", new GradeLetter(94), DATE_TIME));
        enteredArray.add(new Student(2, "Zina", new GradePercentage(80), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalystImpl.averageScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, (80 + 94) / 2);
    }


    @Test
    public void When_Input_Null_Then_MedianScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalystImpl.medianScore(null);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_EmptyList_Then_MedianScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalystImpl.medianScore(enteredArray);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_OneStudentList_Then_MedianScore_Return_ThisScoreValue() {
        enteredArray.add(new Student(1, "Tom", new GradeLetter(95), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalystImpl.medianScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 95);
    }

    @Test
    public void When_Input_TwoStudentList_Then_MedianScore_Return_MedianScoreValue() {
        enteredArray.add(new Student(1, "Tom", new GradeLetter(95), DATE_TIME));
        enteredArray.add(new Student(2, "Zina", new GradeLetter(80), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalystImpl.medianScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 95);
    }

    @Test
    public void When_Input_ThreeStudentList_Then_MedianScore_Return_MedianScoreValue() {
        enteredArray.add(new Student(1, "Tom", new GradeLetter(95), DATE_TIME));
        enteredArray.add(new Student(2, "Zina", new GradeLetter(80), DATE_TIME));
        enteredArray.add(new Student(3, "John", new GradeLetter(68), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalystImpl.medianScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 80);
    }

    @Test
    public void When_Input_FiveStudentList_Then_MedianScore_Return_MedianScoreValue() {
        enteredArray.add(new Student(1, "Tom", new GradeLetter(95), DATE_TIME));
        enteredArray.add(new Student(2, "Zina", new GradeLetter(80), DATE_TIME));
        enteredArray.add(new Student(3, "John", new GradeLetter(68), DATE_TIME));
        enteredArray.add(new Student(4, "Bill", new GradeLetter(90), DATE_TIME));
        enteredArray.add(new Student(5, "Anna", new GradeLetter(74), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalystImpl.medianScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 80);
    }
}
