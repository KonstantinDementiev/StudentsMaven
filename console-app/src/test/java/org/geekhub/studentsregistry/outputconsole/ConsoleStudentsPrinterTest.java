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
    private List<HashMap<String, String>> studentsForPrinting;

    @BeforeMethod
    public void setUp() {
        consoleStudentsPrinter = new ConsoleStudentsPrinter();
        studentsForPrinting = new ArrayList<>();
    }

    @Test
    public void When_ListForPrintingIsNull_Then_PrintEmptyList() {
        assertThatCode(() -> consoleStudentsPrinter.printStudentsTable(
                GradeType.LETTER, null)).doesNotThrowAnyException();
    }

    @Test
    public void When_TableNameIsNull_Then_PrintStudentsSuccessfully() {
        assertThatCode(() -> {
            HashMap<String, String> newStudent1 = new HashMap<>();
            newStudent1.put("Name", "Tom");
            newStudent1.put("Grade", "A");
            studentsForPrinting.add(newStudent1);
            consoleStudentsPrinter.printStudentsTable(null, studentsForPrinting);
        }).doesNotThrowAnyException();
    }

    @Test
    public void When_StudentsToPrintIsOk_Then_PrintCorrectTable() {
        assertThatCode(() -> {
            HashMap<String, String> newStudent1 = new HashMap<>();
            newStudent1.put("Name", "Tom");
            newStudent1.put("Grade", "A");
            HashMap<String, String> newStudent2 = new HashMap<>();
            newStudent2.put("Name", "Anna");
            newStudent2.put("Grade", "B");
            studentsForPrinting.add(newStudent1);
            studentsForPrinting.add(newStudent2);
            consoleStudentsPrinter.printStudentsTable(GradeType.LETTER, studentsForPrinting);
        }).doesNotThrowAnyException();
    }

    @Test
    public void When_PresentStudentWithEmptyOrNullGrade_Then_PrintUnknownGrade() {
        assertThatCode(() -> {
            HashMap<String, String> newStudent1 = new HashMap<>();
            newStudent1.put("Name", "Tom");
            newStudent1.put("Grade", "");
            HashMap<String, String> newStudent2 = new HashMap<>();
            newStudent2.put("Name", "Anna");
            newStudent2.put("Grade", null);
            studentsForPrinting.add(newStudent1);
            studentsForPrinting.add(newStudent2);
            consoleStudentsPrinter.printStudentsTable(GradeType.LETTER, studentsForPrinting);
        }).doesNotThrowAnyException();
    }
}
