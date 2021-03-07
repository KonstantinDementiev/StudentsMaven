package org.geekhub.studentsregistry.domain.grades.grade;

import org.geekhub.studentsregistry.domain.exceptions.unchecked.ScoreOutOfRangeException;
import org.testng.Assert;
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

    @Test
    public void When_GradeNumberIsCorrect_Expect_NotNullResult() {
        int value = 90;
        Assert.assertNotNull(new GradePointAverage(value).asPrintVersion());
    }

    @Test
    public void When_GradeNumberIsSame_Expect_GradeTypeIsEquals() {
        Grade actualGradeType = new GradePointAverage(50);
        Grade expectedGradeType = new GradePointAverage(50);
        Assert.assertEquals(actualGradeType, expectedGradeType);
    }

    @Test
    public void When_GradeNumberIsNotSame_Expect_GradeTypeIsNotEquals() {
        Grade actualGradeType = new GradePointAverage(40);
        Grade expectedGradeType = new GradePointAverage(50);
        Assert.assertNotEquals(actualGradeType, expectedGradeType);
    }

    @Test
    public void When_GradeNumberIsSame_Expect_GradeTypeHashCodeIsSame() {
        Grade actualGradeType = new GradePointAverage(50);
        Grade expectedGradeType = new GradePointAverage(50);
        Assert.assertEquals(actualGradeType.hashCode(), expectedGradeType.hashCode());
    }

    @Test
    public void When_GradeNumberIsNotSame_Expect_GradeTypeHashCodeIsSame() {
        Grade actualGradeType = new GradePointAverage(40);
        Grade expectedGradeType = new GradePointAverage(50);
        Assert.assertNotEquals(actualGradeType.hashCode(), expectedGradeType.hashCode());
    }

    @Test
    public void When_GradeNumberToString_Expect_GradeTypeCorrect() {
        String actualName = new GradePointAverage(80).toString();
        String expectedName = "GradePointAverage";
        Assert.assertEquals(actualName, expectedName);
    }


}
