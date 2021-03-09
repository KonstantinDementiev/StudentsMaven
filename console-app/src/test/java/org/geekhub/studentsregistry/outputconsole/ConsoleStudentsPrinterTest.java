package org.geekhub.studentsregistry.outputconsole;

import org.geekhub.studentsregistry.enums.GradeType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;

public class ConsoleStudentsPrinterTest {

    private ConsoleStudentsPrinter consoleStudentsPrinter;
    private List<String> studentsForPrinting;

    @BeforeMethod
    public void setUp() {
        consoleStudentsPrinter = new ConsoleStudentsPrinter();
        studentsForPrinting = new ArrayList<>();
    }

    @Test
    public void When_ListForPrintingIsNull_Then_PrintEmptyList() {
        assertThatCode(() -> consoleStudentsPrinter.printOneGradeTypeStudents(null))
                .doesNotThrowAnyException();
    }

    @Test
    public void When_StudentsToPrintIsOk_Then_PrintCorrectTable() {
        assertThatCode(() -> {
            studentsForPrinting.add("Tom");
            consoleStudentsPrinter.printOneGradeTypeStudents(studentsForPrinting);
        }).doesNotThrowAnyException();
    }

}
