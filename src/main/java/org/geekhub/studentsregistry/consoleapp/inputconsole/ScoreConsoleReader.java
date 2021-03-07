package org.geekhub.studentsregistry.consoleapp.inputconsole;

import org.geekhub.studentsregistry.domain.exceptions.unchecked.ScoreOutOfRangeException;
import org.geekhub.studentsregistry.domain.logger.StudentsLogger;

import java.util.Optional;
import java.util.Scanner;

public class ScoreConsoleReader {

    private final static StudentsLogger LOG = new StudentsLogger(ScoreConsoleReader.class.getName());
    private final Scanner scanner;

    public ScoreConsoleReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String inputGradeNumber() {
        while (true) {
            try {
                return readGradeNumberFromConsole();
            } catch (NumberFormatException | ScoreOutOfRangeException e) {
                LOG.warning("Invalid value of Grade Number", e);
            }
        }
    }

    private String readGradeNumberFromConsole() throws ScoreOutOfRangeException {
        System.out.println("Grade value:");
        String enteredGradeNumber = scanner.nextLine();
        if (!isInputCorrect(enteredGradeNumber)) {
            throw new ScoreOutOfRangeException("Incorrect Grade score. Please, enter a value between 0 and 100 inclusive");
        }
        return enteredGradeNumber;
    }

    private boolean isInputCorrect(String enteredString) {
        Optional<String> enteredGradeType = Optional.ofNullable(enteredString);
        final int MAX_GRADE_NUMBER = 100;
        final int MIN_GRADE_NUMBER = 0;
        return enteredGradeType
                .map(String::trim)
                .map(this::getNumeric)
                .filter(score -> score >= MIN_GRADE_NUMBER)
                .filter(score -> score <= MAX_GRADE_NUMBER)
                .isPresent();
    }

    private Integer getNumeric(String grateNumber) {
        return Optional.of(Integer.parseInt(grateNumber)).orElseThrow(NumberFormatException::new);
    }

}
