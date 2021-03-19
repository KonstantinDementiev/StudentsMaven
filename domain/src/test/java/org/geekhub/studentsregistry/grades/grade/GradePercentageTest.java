package org.geekhub.studentsregistry.grades.grade;

import org.geekhub.studentsregistry.interfaces.Grade;
import org.geekhub.studentsregistry.exceptions.unchecked.ScoreOutOfRangeException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GradePercentageTest {

    @DataProvider(name = "gradeProvider")
    public Object[][] gradeProvider() {
        return new Object[][]{
                {95, "90 - 100%"}, {85, "80 - 89%"}, {75, "70 - 79%"}, {65, "60 - 69%"}, {50, "< 60%"}
        };
    }

    @Test(dataProvider = "gradeProvider")
    public void When_GradeNumberIsInTheRange_Expect_CorrectGrade(int scoreValue, String gradeValue) {
        Grade grade = new GradePercentage(scoreValue);
        assertEquals(grade.asPrintVersion(), gradeValue);
    }

    @Test(expectedExceptions = ScoreOutOfRangeException.class)
    public void When_GradeNumberIsMoreThenMaxValue_Expect_AsPrintVersionThrowsException() {
        int value = 101;
        new GradePercentage(value).asPrintVersion();
    }

    @Test(expectedExceptions = ScoreOutOfRangeException.class)
    public void When_GradeNumberIsLessThenMinValue_Expect_AsPrintVersionThrowsException() {
        int value = -1;
        new GradePercentage(value).asPrintVersion();
    }

    @Test
    public void When_GradeNumberIsCorrect_Expect_NotNullResult() {
        int value = 90;
        Assert.assertNotNull(new GradePercentage(value).asPrintVersion());
    }

    @Test
    public void When_GradeNumberIsSame_Expect_GradeTypeIsEquals() {
        Grade actualGradeType = new GradePercentage(50);
        Grade expectedGradeType = new GradePercentage(50);
        Assert.assertEquals(actualGradeType, expectedGradeType);
    }

    @Test
    public void When_GradeNumberIsNotSame_Expect_GradeTypeIsNotEquals() {
        Grade actualGradeType = new GradePercentage(40);
        Grade expectedGradeType = new GradePercentage(50);
        Assert.assertNotEquals(actualGradeType, expectedGradeType);
    }

    @Test
    public void When_GradeNumberIsSame_Expect_GradeTypeHashCodeIsSame() {
        Grade actualGradeType = new GradePercentage(50);
        Grade expectedGradeType = new GradePercentage(50);
        Assert.assertEquals(actualGradeType.hashCode(), expectedGradeType.hashCode());
    }

    @Test
    public void When_GradeNumberIsNotSame_Expect_GradeTypeHashCodeIsSame() {
        Grade actualGradeType = new GradePercentage(40);
        Grade expectedGradeType = new GradePercentage(50);
        Assert.assertNotEquals(actualGradeType.hashCode(), expectedGradeType.hashCode());
    }

    @Test
    public void When_GradeNumberToString_Expect_GradeTypeCorrect() {
        String actualName = new GradePercentage(80).toString();
        String expectedName = "GradePercentage";
        Assert.assertEquals(actualName, expectedName);
    }


}
