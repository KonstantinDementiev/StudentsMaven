package org.geekhub.studentsregistry.outputconsole;

import org.geekhub.studentsregistry.inputstudentsdata.GradeType;
import org.geekhub.studentsregistry.outputconsole.ConsoleStudentsPrinter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConsoleStudentsPrinterTest {

    private ConsoleStudentsPrinter consoleStudentsPrinter;
    private List<HashMap<String, String>> studentsForPrinting;

    @BeforeMethod
    public void setUp() {
        consoleStudentsPrinter = new ConsoleStudentsPrinter();
        studentsForPrinting = new ArrayList<>();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void When_ListForPrintingIsNull_ThenThrowException() {
        consoleStudentsPrinter.printStudentsTable(GradeType.LETTER, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void When_TableNameIsNull_ThenThrowException() {
        List<HashMap<String, String>> studentsForPrinting = new ArrayList<>();
        consoleStudentsPrinter.printStudentsTable(null, studentsForPrinting);
    }

    @Test
    public void When_StudentsToPrintIsOk_ThenPrintCorrectTable() {
        HashMap<String, String> newStudent1 = new HashMap<>();
        newStudent1.put("Name", "Tom");
        newStudent1.put("Grade", "A");
        HashMap<String, String> newStudent2 = new HashMap<>();
        newStudent2.put("Name", "Anna");
        newStudent2.put("Grade", "B");
        studentsForPrinting.add(newStudent1);
        studentsForPrinting.add(newStudent2);
        consoleStudentsPrinter.printStudentsTable(GradeType.LETTER, studentsForPrinting);
    }

    @Test
    public void When_PresentStudentWithEmptyOrNullGrade_ThenPrintUnknownGrade() {
        HashMap<String, String> newStudent1 = new HashMap<>();
        newStudent1.put("Name", "Tom");
        newStudent1.put("Grade", "");
        HashMap<String, String> newStudent2 = new HashMap<>();
        newStudent2.put("Name", "Anna");
        newStudent2.put("Grade", null);
        studentsForPrinting.add(newStudent1);
        studentsForPrinting.add(newStudent2);
        consoleStudentsPrinter.printStudentsTable(GradeType.LETTER, studentsForPrinting);
    }
}
