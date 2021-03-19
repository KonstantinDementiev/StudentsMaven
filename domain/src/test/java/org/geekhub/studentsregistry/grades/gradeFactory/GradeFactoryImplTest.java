package org.geekhub.studentsregistry.grades.gradeFactory;

import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.interfaces.Grade;
import org.geekhub.studentsregistry.grades.grade.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GradeFactoryImplTest {

    public GradeFactoryImpl gradeFactory;
    private final int SCORE_VALUE = 0;

    @BeforeMethod
    public void beforeMethod() {
        gradeFactory = new GradeFactoryImpl();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void When_EnterNull_Expect_CreateGradeThrowsException() {
        gradeFactory.createGrade(null, SCORE_VALUE);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void When_EnterInvalidGradeType_Expect_ThrowsException() {
        GradeType gradeType = GradeType.from("BlaBla");
        gradeFactory.createGrade(gradeType, SCORE_VALUE);
    }

    @Test
    public void When_ScoreRangeIsIncorrect_Expect_CreateInstance() {
        GradeType gradeType = GradeType.LETTER;
        gradeFactory.createGrade(gradeType, -20);
    }

    @Test
    public void When_GradeTypeIs1_Expect_Create1Instance() {
        GradeType gradeType = GradeType.LETTER;
        Grade actualResult = gradeFactory.createGrade(gradeType, SCORE_VALUE);
        Grade expectResult = new GradeLetter(SCORE_VALUE);
        assertEquals(actualResult, expectResult, "The Grade must be an instance of GradeLetter.");
    }

    @Test
    public void When_GradeTypeIs2_Expect_Create2Instance() {
        GradeType gradeType = GradeType.PERCENTAGE;
        Grade actualResult = gradeFactory.createGrade(gradeType, SCORE_VALUE);
        Grade expectResult = new GradePercentage(SCORE_VALUE);
        assertEquals(actualResult, expectResult, "The Grade must be an instance of GradePercentage.");
    }

    @Test
    public void When_GradeTypeIs3_Expect_Create3Instance() {
        GradeType gradeType = GradeType.GPA;
        Grade actualResult = gradeFactory.createGrade(gradeType, SCORE_VALUE);
        Grade expectResult = new GradeGPA(SCORE_VALUE);
        assertEquals(actualResult, expectResult, "The Grade must be an instance of GradeGPA.");
    }

    @Test
    public void When_GradeTypeIs4_Expect_Create4Instance() {
        GradeType gradeType = GradeType.UKRAINE;
        Grade actualResult = gradeFactory.createGrade(gradeType, SCORE_VALUE);
        Grade expectResult = new GradeUkraine(SCORE_VALUE);
        assertEquals(actualResult, expectResult, "The Grade must be an instance of GradeUkraine.");
    }

}
