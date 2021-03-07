package org.geekhub.studentsregistry.consoleapp.inputconsole;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Scanner;

public class ConsoleStudentsReaderTest {

    private ConsoleStudentsReader consoleStudentsReader;
    private Scanner scanner;

    @BeforeMethod
    public void setUp() {
        scanner = new Scanner("Tom\n90\nletter");
        consoleStudentsReader = new ConsoleStudentsReader(scanner);
    }

    @Test
    public void when_input_zero_return_empty_list() {
        List<List<String>> actualList = consoleStudentsReader
                .createOriginalStudentsList(0);
        Assert.assertTrue(actualList.isEmpty());
    }

    @Test
    public void when_input_not_zero_return_not_empty_list() {
        List<List<String>> actualList = consoleStudentsReader
                .createOriginalStudentsList(1);
        Assert.assertFalse(actualList.isEmpty());
    }

    @Test(expectedExceptions = NegativeArraySizeException.class)
    public void when_input_negative_return_() {
        consoleStudentsReader.createOriginalStudentsList(-1);
    }

    @AfterMethod
    public void tearDown() {
        scanner.close();
    }
}
