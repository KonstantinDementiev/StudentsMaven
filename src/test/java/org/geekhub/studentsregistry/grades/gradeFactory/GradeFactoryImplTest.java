package org.geekhub.studentsregistry.grades.gradeFactory;

import org.geekhub.studentsregistry.exceptions.unchecked.InvalidGradeArgumentException;
import org.geekhub.studentsregistry.grades.grade.Grade;
import org.geekhub.studentsregistry.grades.grade.GradePointAverage;
import org.geekhub.studentsregistry.inputstudentsdata.GradeType;
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

    @Test(expectedExceptions = InvalidGradeArgumentException.class)
    public void When_InvalidGradeType_Expect_CreateGradeThrowsException() {
        GradeType gradeType = GradeType.from("BlaBla");
        gradeFactory.createGrade(gradeType, SCORE_VALUE);
    }

    @Test
    public void When_GradeTypeIsGPA_Expect_CreateGradePointAverageInstance() {
        GradeType gradeType = GradeType.GPA;
        Grade actualResult = gradeFactory.createGrade(gradeType, SCORE_VALUE);
        Grade expectResult = new GradePointAverage(SCORE_VALUE);
        assertEquals(actualResult, expectResult, "The Grade must be an instance of GradePointAverage.");
    }

}
