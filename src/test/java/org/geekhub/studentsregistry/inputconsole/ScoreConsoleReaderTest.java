package org.geekhub.studentsregistry.inputconsole;

import org.geekhub.studentsregistry.inputconsole.ScoreConsoleReader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.testng.Assert.assertTrue;

public class ScoreConsoleReaderTest {

    private ScoreConsoleReader scoreConsoleReader;
    private Scanner scanner;

    @Test(expectedExceptions = NoSuchElementException.class)
    public void When_StudentScoreIsPositiveOutOfRange_Expect_InputGradeNumberLoopNextAndThrowException() {
        String score = "1500";
        scanner = new Scanner(score);
        scoreConsoleReader = new ScoreConsoleReader(scanner);
        scoreConsoleReader.inputGradeNumber();
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void When_StudentScoreIsNegativeOutOfRange_Expect_InputGradeNumberLoopNextAndThrowException() {
        String score = "-90";
        scanner = new Scanner(score);
        scoreConsoleReader = new ScoreConsoleReader(scanner);
        scoreConsoleReader.inputGradeNumber();
    }

    @Test
    public void When_StudentScoreIsInTheAllowRange_Expect_IsInTheRangeIsTrue() {
        final int MAX_SCORE = 100;
        final int MIN_SCORE = 0;
        String score = "80";
        scanner = new Scanner(score);
        scoreConsoleReader = new ScoreConsoleReader(scanner);
        String result = scoreConsoleReader.inputGradeNumber();
        int value = Integer.parseInt(result);
        assertTrue(value <= MAX_SCORE && value >= MIN_SCORE,
                "The score is in the range 0 < n < 100,");
    }

    @AfterMethod
    public void tearDown() {
        scanner.close();
    }
}
