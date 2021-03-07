package org.geekhub.studentsregistry.consoleapp.outputconsole;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ConsoleStudentsAnalystImplPrinterTest {

    private ConsoleStudentsAnalystPrinter consoleStudentsAnalystPrinter;

    @BeforeMethod
    public void setUp() {
        consoleStudentsAnalystPrinter = new ConsoleStudentsAnalystPrinter();
    }

    @Test
    public void when_input_is_null_print_nothing_without_exceptions() {
        consoleStudentsAnalystPrinter.printDataForOneGradeType(null);
    }

    @Test
    public void when_input_list_is_not_null_print_list_successfully() {
        List<String> list = List.of("one", "two", "three");
        consoleStudentsAnalystPrinter.printDataForOneGradeType(list);
    }
}
