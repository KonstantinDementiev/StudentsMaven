package org.geekhub.studentsregistry.grades.grade;

import org.geekhub.studentsregistry.exceptions.unchecked.ScoreOutOfRangeException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GradePointAverageTest {

    @DataProvider(name = "gradeProvider")
    public Object[][] gradeProvider() {
        return new Object[][]{
                {95, "4.0"}, {85, "3.0"}, {75, "2.0"}, {65, "1.0"}, {50, "0.0"}
        };
    }

    @Test(dataProvider = "gradeProvider")
    public void When_GradeNumberIsInTheRange_Expect_CorrectGrade(int scoreValue, String gradeValue) {
        Grade grade = new GradePointAverage(scoreValue);
        assertEquals(grade.asPrintVersion(), gradeValue);
    }

    @Test(expectedExceptions = ScoreOutOfRangeException.class)
    public void When_GradeNumberIsMoreThenMaxValue_Expect_AsPrintVersionThrowsException() {
        int value = 101;
        new GradePointAverage(value).asPrintVersion();
    }

    @Test(expectedExceptions = ScoreOutOfRangeException.class)
    public void When_GradeNumberIsLessThenMinValue_Expect_AsPrintVersionThrowsException() {
        int value = -1;
        new GradePointAverage(value).asPrintVersion();
    }

}
