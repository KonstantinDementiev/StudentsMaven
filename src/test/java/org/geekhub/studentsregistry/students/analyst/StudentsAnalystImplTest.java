package org.geekhub.studentsregistry.students.analyst;

import org.geekhub.studentsregistry.grades.grade.GradeLetter;
import org.geekhub.studentsregistry.grades.grade.GradePercent;
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
    private StudentsAnalystImpl studentsAnalyst;

    @BeforeMethod
    public void setUp() {
        studentsAnalyst = new StudentsAnalystImpl();
        enteredArray = new ArrayList<>();
    }

    @Test
    public void When_Input_Null_Then_MaxScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalyst.maxScore(null);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_EmptyList_Then_MaxScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalyst.maxScore(enteredArray);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_OneStudentList_Then_MaxScore_Return_ThisScoreValue() {
        enteredArray.add(new Student("Tom", new GradeLetter(95), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalyst.maxScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 95);
    }

    @Test
    public void When_Input_TwoStudentList_Then_MaxScore_Return_MaxScoreValue() {
        enteredArray.add(new Student("Tom", new GradeLetter(95), DATE_TIME));
        enteredArray.add(new Student("Zina", new GradeLetter(80), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalyst.maxScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 95);
    }

    @Test
    public void When_Input_TwoStudentListWithDifferentGradeType_Then_MaxScore_Return_MaxScoreValue() {
        enteredArray.add(new Student("Tom", new GradeLetter(94), DATE_TIME));
        enteredArray.add(new Student("Zina", new GradePercent(80), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalyst.maxScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 94);
    }

    @Test
    public void When_Input_Null_Then_MinScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalyst.minScore(null);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_EmptyList_Then_MinScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalyst.minScore(enteredArray);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_OneStudentList_Then_MinScore_Return_ThisScoreValue() {
        enteredArray.add(new Student("Tom", new GradeLetter(95), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalyst.minScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 95);
    }

    @Test
    public void When_Input_TwoStudentList_Then_MinScore_Return_MinScoreValue() {
        enteredArray.add(new Student("Tom", new GradeLetter(95), DATE_TIME));
        enteredArray.add(new Student("Zina", new GradeLetter(80), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalyst.minScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 80);
    }

    @Test
    public void When_Input_TwoStudentListWithDifferentGradeType_Then_MinScore_Return_MinScoreValue() {
        enteredArray.add(new Student("Tom", new GradeLetter(94), DATE_TIME));
        enteredArray.add(new Student("Zina", new GradePercent(80), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalyst.minScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 80);
    }

    @Test
    public void When_Input_Null_Then_AverageScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalyst.averageScore(null);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_EmptyList_Then_AverageScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalyst.averageScore(enteredArray);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_OneStudentList_Then_AverageScore_Return_ThisScoreValue() {
        enteredArray.add(new Student("Tom", new GradeLetter(95), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalyst.averageScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 95);
    }

    @Test
    public void When_Input_TwoStudentList_Then_AverageScore_Return_AverageScoreValue() {
        enteredArray.add(new Student("Tom", new GradeLetter(95), DATE_TIME));
        enteredArray.add(new Student("Zina", new GradeLetter(80), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalyst.averageScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, (80 + 95) / 2);
    }

    @Test
    public void When_Input_TwoStudentListWithDifferentGradeType_Then_AverageScore_Return_AverageScoreValue() {
        enteredArray.add(new Student("Tom", new GradeLetter(94), DATE_TIME));
        enteredArray.add(new Student("Zina", new GradePercent(80), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalyst.averageScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, (80 + 94) / 2);
    }

    @Test
    public void When_Input_Null_Then_MedianScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalyst.medianScore(null);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_EmptyList_Then_MedianScore_Return_EmptyOptional() {
        Optional<Integer> actualScore = studentsAnalyst.medianScore(enteredArray);
        Optional<Integer> expectedScore = Optional.empty();
        assertEquals(actualScore, expectedScore);
    }

    @Test
    public void When_Input_OneStudentList_Then_MedianScore_Return_ThisScoreValue() {
        enteredArray.add(new Student("Tom", new GradeLetter(95), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalyst.medianScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 95);
    }

    @Test
    public void When_Input_TwoStudentList_Then_MedianScore_Return_MedianScoreValue() {
        enteredArray.add(new Student("Tom", new GradeLetter(95), DATE_TIME));
        enteredArray.add(new Student("Zina", new GradeLetter(80), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalyst.medianScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 95);
    }

    @Test
    public void When_Input_ThreeStudentList_Then_MedianScore_Return_MedianScoreValue() {
        enteredArray.add(new Student("Tom", new GradeLetter(95), DATE_TIME));
        enteredArray.add(new Student("Zina", new GradeLetter(80), DATE_TIME));
        enteredArray.add(new Student("John", new GradeLetter(68), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalyst.medianScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 80);
    }

    @Test
    public void When_Input_FiveStudentList_Then_MedianScore_Return_MedianScoreValue() {
        enteredArray.add(new Student("Tom", new GradeLetter(95), DATE_TIME));
        enteredArray.add(new Student("Zina", new GradeLetter(80), DATE_TIME));
        enteredArray.add(new Student("John", new GradeLetter(68), DATE_TIME));
        enteredArray.add(new Student("Bill", new GradeLetter(90), DATE_TIME));
        enteredArray.add(new Student("Anna", new GradeLetter(74), DATE_TIME));
        int actualScore = 0;
        Optional<Integer> optionalActualScore = studentsAnalyst.medianScore(enteredArray);
        if (optionalActualScore.isPresent()) actualScore = optionalActualScore.get();
        assertEquals(actualScore, 80);
    }
}
