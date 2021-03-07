package org.geekhub.studentsregistry.consoleapp.inputconsole;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.testng.Assert.assertEquals;

public class GradeConsoleReaderTest {

    private GradeConsoleReader gradeConsoleReader;
    private Scanner scanner;

    @Test(expectedExceptions = NoSuchElementException.class)
    public void When_EnteredWrongGradeType_Expect_InputGradeTypeLoopNextAndThrowException() {
        String actualType = "BlaBla";
        scanner = new Scanner(actualType);
        gradeConsoleReader = new GradeConsoleReader(scanner);
        gradeConsoleReader.inputGradeType();
    }

    @Test
    public void When_EnteredLetterGradeTypeToLowerCase_Expect_InputGradeTypeReturnNotNullString() {
        String actualType = "letter";
        scanner = new Scanner(actualType);
        gradeConsoleReader = new GradeConsoleReader(scanner);
        assertEquals(gradeConsoleReader.inputGradeType(), "LETTER");
    }

    @Test
    public void When_EnteredGPAGradeTypeToLowerCase_Expect_InputGradeTypeReturnNotNullString() {
        String actualType = "gpa";
        scanner = new Scanner(actualType);
        gradeConsoleReader = new GradeConsoleReader(scanner);
        assertEquals(gradeConsoleReader.inputGradeType(), "GPA");
    }

    @Test
    public void When_EnteredPercentGradeType_ExpectToLowerCase_InputGradeTypeReturnNotNullString() {
        String actualType = "percentage";
        scanner = new Scanner(actualType);
        gradeConsoleReader = new GradeConsoleReader(scanner);
        assertEquals(gradeConsoleReader.inputGradeType(), "PERCENTAGE");
    }

    @Test
    public void When_EnteredLetterGradeTypeToUpperCase_Expect_InputGradeTypeReturnNotNullString() {
        String actualType = "LETTER";
        scanner = new Scanner(actualType);
        gradeConsoleReader = new GradeConsoleReader(scanner);
        assertEquals(gradeConsoleReader.inputGradeType(), "LETTER");
    }

    @Test
    public void When_EnteredGPAGradeTypeToUpperCase_Expect_InputGradeTypeReturnNotNullString() {
        String actualType = "GPA";
        scanner = new Scanner(actualType);
        gradeConsoleReader = new GradeConsoleReader(scanner);
        assertEquals(gradeConsoleReader.inputGradeType(), "GPA");
    }

    @Test
    public void When_EnteredPercentGradeType_ExpectToUpperCase_InputGradeTypeReturnNotNullString() {
        String actualType = "PERCENTAGE";
        scanner = new Scanner(actualType);
        gradeConsoleReader = new GradeConsoleReader(scanner);
        assertEquals(gradeConsoleReader.inputGradeType(), "PERCENTAGE");
    }

    @AfterMethod
    public void tearDown() {
        scanner.close();
    }
}
