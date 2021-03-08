package org.geekhub.studentsregistry.inputconsole;

import org.geekhub.studentsregistry.exceptions.checked.InvalidStudentNameException;
import org.geekhub.studentsregistry.logger.ConsoleLogger;

import java.util.Optional;
import java.util.Scanner;

public class NameConsoleReader {

    private final static ConsoleLogger LOG = new ConsoleLogger(NameConsoleReader.class.getName());
    private final Scanner scanner;

    public NameConsoleReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String inputStudentName() {
        while (true) {
            try {
                return readStudentNameFromConsole();
            } catch (InvalidStudentNameException e) {
                LOG.warning("Invalid value of Student Name", e);
            }
        }
    }

    private String readStudentNameFromConsole() throws InvalidStudentNameException {
        System.out.println("Student`s name:");
        String enteredName = scanner.nextLine();
        if (!isInputCorrect(enteredName)) {
            throw new InvalidStudentNameException("Student name is incorrect. Please, enter a name with length from 2 to 18 symbols");
        }
        return enteredName.trim();
    }

    private boolean isInputCorrect(String enteredString) {
        Optional<String> enteredGradeType = Optional.ofNullable(enteredString);
        return enteredGradeType
                .map(String::trim)
                .filter(this::isNameCorrect)
                .isPresent();
    }

    private boolean isNameCorrect(String name) {
        int MIN_LENGTH_NAME = 2;
        int MAX_LENGTH_NAME = 18;
        return name.length() >= MIN_LENGTH_NAME && name.length() <= MAX_LENGTH_NAME;
    }
}
