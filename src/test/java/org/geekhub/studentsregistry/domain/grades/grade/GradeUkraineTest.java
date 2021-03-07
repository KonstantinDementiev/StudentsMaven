package org.geekhub.studentsregistry.domain.grades.grade;

import org.geekhub.studentsregistry.domain.exceptions.unchecked.ScoreOutOfRangeException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GradeUkraineTest {

    @DataProvider(name = "gradeProvider")
    public Object[][] gradeProvider() {
        return new Object[][]{
                {96, "12"}, {91, "11"}, {86, "10"}, {81, "9"}, {76, "8"}, {71, "7"},
                {66, "6"}, {61, "5"}, {56, "4"}, {51, "3"}, {46, "2"}, {20, "1"}};
    }

    @Test(dataProvider = "gradeProvider")
    public void When_GradeNumberIsInTheRange_Expect_CorrectGrade(int scoreValue, String gradeValue) {
        Grade grade = new GradeUkraine(scoreValue);
        assertEquals(grade.asPrintVersion(), gradeValue);
    }

    @Test(expectedExceptions = ScoreOutOfRangeException.class)
    public void When_GradeNumberIsMoreThenMaxValue_Expect_AsPrintVersionThrowsException() {
        int value = 101;
        new GradeUkraine(value).asPrintVersion();
    }

    @Test(expectedExceptions = ScoreOutOfRangeException.class)
    public void When_GradeNumberIsLessThenMinValue_Expect_AsPrintVersionThrowsException() {
        int value = -1;
        new GradeUkraine(value).asPrintVersion();
    }

    @Test
    public void When_GradeNumberIsCorrect_Expect_NotNullResult() {
        int value = 90;
        Assert.assertNotNull(new GradeUkraine(value).asPrintVersion());
    }

    @Test
    public void When_GradeNumberIsSame_Expect_GradeTypeIsEquals() {
        Grade actualGradeType = new GradeUkraine(50);
        Grade expectedGradeType = new GradeUkraine(50);
        Assert.assertEquals(actualGradeType, expectedGradeType);
    }

    @Test
    public void When_GradeNumberIsNotSame_Expect_GradeTypeIsNotEquals() {
        Grade actualGradeType = new GradeUkraine(40);
        Grade expectedGradeType = new GradeUkraine(50);
        Assert.assertNotEquals(actualGradeType, expectedGradeType);
    }

    @Test
    public void When_GradeNumberIsSame_Expect_GradeTypeHashCodeIsSame() {
        Grade actualGradeType = new GradeUkraine(50);
        Grade expectedGradeType = new GradeUkraine(50);
        Assert.assertEquals(actualGradeType.hashCode(), expectedGradeType.hashCode());
    }

    @Test
    public void When_GradeNumberIsNotSame_Expect_GradeTypeHashCodeIsSame() {
        Grade actualGradeType = new GradeUkraine(40);
        Grade expectedGradeType = new GradeUkraine(50);
        Assert.assertNotEquals(actualGradeType.hashCode(), expectedGradeType.hashCode());
    }

    @Test
    public void When_GradeNumberToString_Expect_GradeTypeCorrect() {
        String actualName = new GradeUkraine(80).toString();
        String expectedName = "GradeUkraine";
        Assert.assertEquals(actualName, expectedName);
    }

}
