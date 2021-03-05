package org.geekhub.studentsregistry.grades.grade;

import org.geekhub.studentsregistry.exceptions.unchecked.ScoreOutOfRangeException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GradeLetterTest {

    @DataProvider(name = "gradeProvider")
    public Object[][] gradeProvider() {
        return new Object[][]{
                {95, "A"}, {85, "B"}, {75, "C"}, {65, "D"}, {50, "F"}
        };
    }

    @Test(dataProvider = "gradeProvider")
    public void When_GradeNumberIsInTheRange_Expect_CorrectGrade(int scoreValue, String gradeValue) {
        Grade grade = new GradeLetter(scoreValue);
        assertEquals(grade.asPrintVersion(), gradeValue);
    }

    @Test(expectedExceptions = ScoreOutOfRangeException.class)
    public void When_GradeNumberIsMoreThenMaxValue_Expect_AsPrintVersionThrowsException() {
        int value = 101;
        new GradeLetter(value).asPrintVersion();
    }

    @Test(expectedExceptions = ScoreOutOfRangeException.class)
    public void When_GradeNumberIsLessThenMinValue_Expect_AsPrintVersionThrowsException() {
        int value = -1;
        new GradeLetter(value).asPrintVersion();
    }
}
