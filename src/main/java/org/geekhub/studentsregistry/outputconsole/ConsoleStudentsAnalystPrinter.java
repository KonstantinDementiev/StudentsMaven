package org.geekhub.studentsregistry.outputconsole;

import org.geekhub.studentsregistry.anotations.Dependency;

import java.util.List;

@Dependency
public class ConsoleStudentsAnalystPrinter {

    public void printDataForOneGradeType(List<String> listToPrint) {
        listToPrint.forEach(System.out::println);
    }

}
