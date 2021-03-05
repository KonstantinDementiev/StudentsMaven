package org.geekhub.studentsregistry.inputconsole;

import org.geekhub.studentsregistry.inputstudentsdata.DataReader;

import java.util.List;
import java.util.Scanner;

public class ConsoleStudentsReader implements DataReader {

    private final Scanner scanner;

    public ConsoleStudentsReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public List<List<String>> createOriginalStudentsList(int studentsNumber) {
        return new ConsoleDataReader(
                new NameConsoleReader(scanner),
                new ScoreConsoleReader(scanner),
                new GradeConsoleReader(scanner)
        ).readStudentsFromConsole(studentsNumber);
    }

}
