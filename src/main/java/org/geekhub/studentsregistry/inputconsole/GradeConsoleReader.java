package org.geekhub.studentsregistry.inputconsole;

import org.geekhub.studentsregistry.exceptions.checked.GradeNotSupportedException;
import org.geekhub.studentsregistry.inputstudentsdata.GradeType;
import org.geekhub.studentsregistry.logger.ConsoleLogger;

import java.util.Optional;
import java.util.Scanner;

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
                    + allGradeTypes() + " you can use lower case");
        }
        return enteredGradeType;
    }

    private boolean isInputCorrect(String enteredString) {
        Optional<String> enteredGradeType = Optional.ofNullable(enteredString);
        return enteredGradeType
                .map(String::trim)
                .filter(this::isEnumContains)
                .isPresent();
    }

    private boolean isEnumContains(String item) {
        for (GradeType gt : GradeType.values()) {
            if (gt.name().equals(item)) {
                return true;
            }
        }
        return false;
    }

    private String allGradeTypes() {
        StringBuilder allGradeTypes = new StringBuilder();
        for (var gradeType : GradeType.values()) {
            allGradeTypes.append(gradeType.name()).append(", ");
        }
        return allGradeTypes.toString();
    }
}
