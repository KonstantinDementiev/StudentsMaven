package org.geekhub.studentsregistry.inputdata;

import org.geekhub.studentsregistry.enums.DataSourceMode;
import org.geekhub.studentsregistry.exceptions.unchecked.InvalidGradeArgumentException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DataSourceModeTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_input_is_null_throw_exception() {
        DataSourceMode.from(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_input_is_incorrect_throw_exception() {
        DataSourceMode.from("something");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_input_in_lower_case_correct_throw_exception() {
        DataSourceMode.from("auto");
    }

    @Test
    public void when_input_in_upper_case_correct_return_grade_type_successfully1() {
        DataSourceMode.from("AUTO");
    }

    @Test
    public void get_all_values_is_correct() {
        DataSourceMode[] allValues = DataSourceMode.values();
        Assert.assertEquals(allValues.length, 2);
    }

}
