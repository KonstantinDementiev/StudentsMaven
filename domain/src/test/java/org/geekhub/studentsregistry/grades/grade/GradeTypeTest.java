package org.geekhub.studentsregistry.grades.grade;

import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.exceptions.unchecked.InvalidGradeArgumentException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GradeTypeTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_input_is_null_throw_exception() {
        GradeType.from(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_input_is_incorrect_throw_exception() {
        GradeType.from("something");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_input_in_lower_case_correct_throw_exception() {
        GradeType.from("letter");
    }

    @Test
    public void when_input_in_upper_case_correct_return_grade_type_successfully1() {
        GradeType.from("LETTER");
    }

    @Test
    public void get_all_values_is_correct() {
        GradeType[] allValues = GradeType.values();
        Assert.assertEquals(allValues.length, 4);
    }

}
