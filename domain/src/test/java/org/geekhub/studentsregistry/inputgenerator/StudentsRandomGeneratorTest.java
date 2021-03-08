package org.geekhub.studentsregistry.inputgenerator;

import org.geekhub.studentsregistry.enums.GradeType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentsRandomGeneratorTest {

    private StudentsRandomGenerator studentsRandomGenerator;

    @BeforeMethod
    public void setUp() {
        studentsRandomGenerator = new StudentsRandomGenerator();
    }

    @Test(expectedExceptions = NegativeArraySizeException.class)
    public void When_StudentsNumberIsNegative_Then_ThrowException() {
        int studentsNumber = -1;
        studentsRandomGenerator.createOriginalStudentsList(studentsNumber);
    }

    @Test
    public void When_StudentsNumberIsZero_Then_ResultArrayIsEmpty() {
        int studentsNumber = 0;
        List<List<String>> actualResult = studentsRandomGenerator.createOriginalStudentsList(studentsNumber);
        List<List<String>> expectResult = new ArrayList<>();
        Assert.assertEquals(actualResult, expectResult, "Array must be empty,");
    }

    @Test
    public void When_StudentsNumberIsPositive_Then_ReturnStudentsList() {
        int studentsNumber = 3;
        List<List<String>> actualResult = studentsRandomGenerator.createOriginalStudentsList(studentsNumber);
        Assert.assertEquals(actualResult.size(), studentsNumber, "Array length must be 3,");
    }

    @Test
    public void When_ReturnStudentsList_Then_StudentsNameIsFromEnum() {
        int studentsNumber = 1;
        List<List<String>> actualResult = studentsRandomGenerator.createOriginalStudentsList(studentsNumber);
        List<StudentsNames> allStudentsNames = Arrays.asList(StudentsNames.values());
        StudentsNames actualName = StudentsNames.Anna;
        try {
            actualName = StudentsNames.valueOf(actualResult.get(0).get(0));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(allStudentsNames.contains(actualName), "Name is not from Enum,");
    }

    @Test
    public void When_ReturnStudentsList_Then_StudentsGradeTypeIsFromEnum() {
        int studentsNumber = 1;
        List<List<String>> actualResult = studentsRandomGenerator.createOriginalStudentsList(studentsNumber);
        boolean isFromEnum = true;
        try {
            GradeType.valueOf(actualResult.get(0).get(2));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            isFromEnum = false;
        }
        Assert.assertTrue(isFromEnum, "Grade type is not from Enum,");
    }

}
