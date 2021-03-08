package org.geekhub.studentsregistry.inputconsole;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class ConsoleDataReaderTest {

    @Mock
    private NameConsoleReader nameConsoleReader;
    @Mock
    private ScoreConsoleReader scoreConsoleReader;
    @Mock
    private GradeConsoleReader gradeConsoleReader;

    private ConsoleDataReader consoleDataReader;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        consoleDataReader = new ConsoleDataReader(
                nameConsoleReader,
                scoreConsoleReader,
                gradeConsoleReader
        );
    }

    @Test(expectedExceptions = NegativeArraySizeException.class)
    public void When_StudentsNumberIsNegative_Then_ThrowException() {
        int studentsNumber = -1;
        consoleDataReader.readStudentsFromConsole(studentsNumber);
    }

    @Test
    public void When_StudentsNumberIsZero_Then_ResultArrayIsEmpty() {
        int studentsNumber = 0;
        List<List<String>> actualResult = consoleDataReader.readStudentsFromConsole(studentsNumber);
        List<List<String>> expectResult = new ArrayList<>();
        assertEquals(actualResult, expectResult, "Array must be empty,");
    }

    @Test
    public void When_StudentsNumberIsPositive_Then_ReturnStudentsArray() {
        int studentsNumber = 3;
        List<List<String>> actualResult = consoleDataReader.readStudentsFromConsole(studentsNumber);
        assertEquals(actualResult.size(), studentsNumber, "Array length must be 3,");
    }


}
