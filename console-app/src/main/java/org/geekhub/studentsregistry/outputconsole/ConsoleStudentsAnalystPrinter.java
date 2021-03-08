package org.geekhub.studentsregistry.outputconsole;

import org.geekhub.studentsregistry.anotations.Dependency;

import java.util.List;
import java.util.Optional;

@Dependency
public class ConsoleStudentsAnalystPrinter {

    public void printDataForOneGradeType(List<String> listToPrint) {
        Optional<List<String>> opt = Optional.ofNullable(listToPrint);
        opt.ifPresent(list -> list.forEach(System.out::println));
    }
}