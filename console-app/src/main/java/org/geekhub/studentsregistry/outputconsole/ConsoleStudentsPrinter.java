package org.geekhub.studentsregistry.outputconsole;

import org.geekhub.studentsregistry.anotations.Dependency;

import java.util.List;
import java.util.Optional;

@Dependency
public class ConsoleStudentsPrinter {

    public void printOneGradeTypeStudents(List<String> listToPrint) {
        Optional<List<String>> opt = Optional.ofNullable(listToPrint);
        opt.ifPresent(list -> list.forEach(System.out::println));
    }

}
