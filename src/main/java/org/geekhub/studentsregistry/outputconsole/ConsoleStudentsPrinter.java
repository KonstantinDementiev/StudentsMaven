package org.geekhub.studentsregistry.outputconsole;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.inputstudentsdata.GradeType;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Dependency
public class ConsoleStudentsPrinter {

    private final int[] WIDTH_COLUMN = {18, 13};

    public void printStudentsTable(GradeType gradeType, List<HashMap<String, String>> studentsForPrinting) {
        System.out.printf("\nWith %s Grade %s\n",
                getTableName(gradeType).orElse("N/A"), getStudentsListSize(studentsForPrinting));
        printTableForOneGradeType(studentsForPrinting);
    }

    private void printTableForOneGradeType(List<HashMap<String, String>> studentsToPrint) {
        printTableHead();
        studentsToPrint.forEach(this::printStudentLine);
        printLine();
    }

    private Optional<String> getTableName(GradeType gradeType) {
        return Optional.ofNullable(MapGradeTypeToTableName.getGradeTableName().get(gradeType));
    }

    private String getStudentsListSize(List<HashMap<String, String>> students) {
        return "" + students.size() + " students:";
    }

    private void printTableHead() {
        printLine();
        System.out.printf("|%-18s|%-13s|%s", "Name", "Grade", '\n');
        printLine();
    }

    private void printStudentLine(HashMap<String, String> student) {
        System.out.printf("|%-18.17s|%13s|%s",
                student.get("Name"),
                aligningTextInTheMiddleOfTableColumn(getGradeTypeName(student.get("Grade"))),
                '\n');
    }

    private String getGradeTypeName(String name) {
        String optionalName = Optional.ofNullable(name).orElse("Unknown grade");
        if (optionalName.isEmpty()) return "Unknown grade";
        return optionalName;
    }

    private String aligningTextInTheMiddleOfTableColumn(String str) {
        final int NUMBER_OF_SIDE_SPACES = (WIDTH_COLUMN[1] - str.length()) / 2;
        return " ".repeat(Math.max(0, NUMBER_OF_SIDE_SPACES)) +
                str +
                " ".repeat(Math.max(0, NUMBER_OF_SIDE_SPACES));
    }

    private void printLine() {
        System.out.print('|');
        for (int k : WIDTH_COLUMN) {
            for (int i = 0; i < k; i++) {
                System.out.print('-');
            }
            System.out.print('|');
        }
        System.out.println();
    }

}
