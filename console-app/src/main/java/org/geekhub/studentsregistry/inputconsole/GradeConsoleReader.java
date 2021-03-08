package org.geekhub.studentsregistry.inputconsole;

import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.exceptions.checked.GradeNotSupportedException;
import org.geekhub.studentsregistry.logger.ConsoleLogger;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GradeConsoleReader {

    private final static ConsoleLogger LOG = new ConsoleLogger(GradeConsoleReader.class.getName());
    private final Scanner scanner;

    public GradeConsoleReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String inputGradeType() {
        while (true) {
            try {
                return readGradeTypeFromConsole();
            } catch (GradeNotSupportedException e) {
                LOG.warning("Invalid value of Grade Type", e);
            }
        }
    }

    private String readGradeTypeFromConsole() throws GradeNotSupportedException {
        System.out.println("Grade type:");
        String enteredGradeType = scanner.nextLine().toUpperCase();
        if (!isInputCorrect(enteredGradeType)) {
            throw new GradeNotSupportedException("Incorrect Grade Type. Please, enter one of next types: "
                    + allGradeTypes() + ", you can use lower case");
        }
        return enteredGradeType;
    }

    private boolean isInputCorrect(String enteredString) {
        return Stream.of(GradeType.values()).map(GradeType::toString).anyMatch(i -> i.equals(enteredString));
    }

    private String allGradeTypes() {
        return Stream.of(GradeType.values()).map(GradeType::toString).collect(Collectors.joining(", "));
    }
}
