package org.geekhub.studentsregistry.inputconsole;

import java.util.ArrayList;
import java.util.List;

public class ConsoleDataReader {

    private final NameConsoleReader studentReader;
    private final ScoreConsoleReader scoreReader;
    private final GradeConsoleReader gradeReader;

    public ConsoleDataReader(
            NameConsoleReader studentReader,
            ScoreConsoleReader scoreReader,
            GradeConsoleReader gradeReader
    ) {
        this.studentReader = studentReader;
        this.scoreReader = scoreReader;
        this.gradeReader = gradeReader;
    }

    public List<List<String>> readStudentsFromConsole(int studentsNumber) {
        if (studentsNumber < 0) {
            throw new NegativeArraySizeException("Students number can not be negative.");
        }
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < studentsNumber; i++) {
            System.out.println("\nPlease enter the " + (i + 1) + " student info:");
            List<String> newStudent = new ArrayList<>();
            newStudent.add(studentReader.inputStudentName());
            newStudent.add(scoreReader.inputGradeNumber());
            newStudent.add(gradeReader.inputGradeType());
            result.add(newStudent);
        }
        return result;
    }

}
