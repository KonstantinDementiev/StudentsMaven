package org.geekhub.studentsregistry.grades.grade;

import org.geekhub.studentsregistry.exceptions.unchecked.ScoreOutOfRangeException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GradePercentTest {

    @DataProvider(name = "gradeProvider")
    public Object[][] gradeProvider() {
        return new Object[][]{
                {95, "90 - 100%"}, {85, "80 - 89%"}, {75, "70 - 79%"}, {65, "60 - 69%"}, {50, "< 60%"}
        };
    }

    @Test(dataProvider = "gradeProvider")
    public void When_GradeNumberIsInTheRange_Expect_CorrectGrade(int scoreValue, String gradeValue) {
        Grade grade = new GradePercent(scoreValue);
        assertEquals(grade.asPrintVersion(), gradeValue);
    }

    @Test(expectedExceptions = ScoreOutOfRangeException.class)
    public void When_GradeNumberIsMoreThenMaxValue_Expect_AsPrintVersionThrowsException() {
        int value = 101;
        new GradePercent(value).asPrintVersion();
    }

    @Test(expectedExceptions = ScoreOutOfRangeException.class)
    public void When_GradeNumberIsLessThenMinValue_Expect_AsPrintVersionThrowsException() {
        int value = -1;
        new GradePercent(value).asPrintVersion();
    }

}
