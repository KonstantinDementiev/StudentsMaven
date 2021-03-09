package org.geekhub.studentsregistry.outputconsole;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ConsoleAnalyticsPrinterTest {

    private ConsoleAnalyticsPrinter consoleAnalyticsPrinter;

    @BeforeMethod
    public void setUp() {
        consoleAnalyticsPrinter = new ConsoleAnalyticsPrinter();
    }

    @Test
    public void when_input_is_null_print_nothing_without_exceptions() {
        consoleAnalyticsPrinter.printOneGradeTypeAnalytics(null);
    }

    @Test
    public void when_input_list_is_not_null_print_list_successfully() {
        List<String> list = List.of("one", "two", "three");
        consoleAnalyticsPrinter.printOneGradeTypeAnalytics(list);
    }
}
