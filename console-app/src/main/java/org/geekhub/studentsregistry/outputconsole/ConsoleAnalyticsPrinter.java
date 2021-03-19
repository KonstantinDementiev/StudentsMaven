package org.geekhub.studentsregistry.outputconsole;

import org.geekhub.studentsregistry.anotations.Dependency;


import java.util.List;
import java.util.Optional;


@Dependency
public class ConsoleAnalyticsPrinter {

    public void printOneGradeTypeAnalytics(List<String> listToPrint) {
        Optional<List<String>> opt = Optional.ofNullable(listToPrint);
        opt.ifPresent(list -> list.forEach(System.out::println));
    }
}