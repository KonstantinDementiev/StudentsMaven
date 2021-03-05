package org.geekhub.studentsregistry.inputconsole;

import org.geekhub.studentsregistry.inputconsole.NameConsoleReader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.testng.Assert.assertTrue;

public class NameConsoleReaderTest {

    private NameConsoleReader nameConsoleReader;
    private Scanner scanner;

    @Test(expectedExceptions = NoSuchElementException.class)
    public void When_StudentNameIsIncorrect_Expect_InputStudentNameLoopNextAndThrowException() {
        String name = "Salvador Domingo Felipe Jacinto Dali";
        scanner = new Scanner(name);
        nameConsoleReader = new NameConsoleReader(scanner);
        nameConsoleReader.inputStudentName();
    }

    @Test
    public void When_StudentNameIsLessThen18AndOver2Letters_Expect_IsNameCorrectIsTrue() {
        final int MAX_NAME_LENGTH = 18;
        final int MIN_NAME_LENGTH = 2;
        String name = "Salvador Dali";
        scanner = new Scanner(name);
        nameConsoleReader = new NameConsoleReader(scanner);
        int nameLength = nameConsoleReader.inputStudentName().length();
        assertTrue(nameLength < MAX_NAME_LENGTH && nameLength > MIN_NAME_LENGTH,
                "The length of the name is in range 2 < n < 18 symbols,");
    }

    @AfterMethod
    public void tearDown() {
        scanner.close();
    }
}
