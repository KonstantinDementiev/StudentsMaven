package org.geekhub.studentsregistry.printconverters;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.students.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Dependency
public class ConverterStudentsToPrint {

    private final int[] WIDTH_COLUMN = {20, 13};

    public List<String> convertStudentsGroupToPrint(GradeType gradetype, List<Student> studentsToPrint) {
        List<HashMap<String, String>> studentsGropeToPrint = createStudentsGropeToPrint(studentsToPrint);
        return getStudentsTableToPrint(gradetype, studentsGropeToPrint);
    }


    private List<HashMap<String, String>> createStudentsGropeToPrint(List<Student> studentsToPrint) {
        return Optional.ofNullable(studentsToPrint)
                .orElse(new ArrayList<>())
                .stream()
                .map(this::createNewStudentToPrint)
                .collect(Collectors.toList());
    }

    private HashMap<String, String> createNewStudentToPrint(Student student) {
        HashMap<String, String> newStudent = new HashMap<>();
        newStudent.put("Name", student.getName());
        newStudent.put("Grade", student.getGrade().asPrintVersion());
        return newStudent;
    }

    private List<String> getStudentsTableToPrint(GradeType gradeType, List<HashMap<String, String>> studentsToPrint) {
        List<String> resultList = new ArrayList<>();
        String tableTitle = "\nWith " + getValidGradeType(gradeType) + " Grade " + studentsToPrint.size() + " students:";
        resultList.add(tableTitle);
        resultList.addAll(printTableForOneGradeType(studentsToPrint));
        return resultList;
    }

    private String getValidGradeType(GradeType gradeType) {
        Optional<GradeType> optGradeType = Optional.ofNullable(gradeType);
        return optGradeType
                .map(Enum::toString)
                .map(grade -> grade.charAt(0) + grade.substring(1).toLowerCase())
                .orElse("N/A");
    }

    private List<String> printTableForOneGradeType(List<HashMap<String, String>> studentsToPrint) {
        List<String> resultTable = new ArrayList<>(printTableHead());
        resultTable.addAll(printStudentList(studentsToPrint));
        resultTable.add(printDelimiterLine());
        return resultTable;
    }

    private List<String> printTableHead() {
        List<String> resultHead = new ArrayList<>();
        resultHead.add(printDelimiterLine());
        resultHead.add(printHeaderLine());
        resultHead.add(printDelimiterLine());
        return resultHead;
    }

    private String printHeaderLine() {
        StringBuilder line = new StringBuilder("|");
        int firstColumnSpaces = (WIDTH_COLUMN[0] - "Name".length()) / 2;
        int secondColumnSpaces = (WIDTH_COLUMN[1] - "Grade".length()) / 2;
        line.append(" ".repeat(firstColumnSpaces)).append("Name").append(" ".repeat(firstColumnSpaces)).append("|");
        line.append(" ".repeat(secondColumnSpaces)).append("Grade").append(" ".repeat(secondColumnSpaces)).append("|");
        return line.toString();
    }

    private List<String> printStudentList(List<HashMap<String, String>> studentsToPrint) {
        return studentsToPrint.stream().map(this::printStudentLine).collect(Collectors.toList());
    }

    private String printStudentLine(HashMap<String, String> student) {
        String name = "|" + student.get("Name");
        String grade = aligningTextInTheMiddleOfTableColumn(getGradeTypeName(student.get("Grade")));
        return name + " ".repeat(WIDTH_COLUMN[0] - name.length() + 1) + "|" + grade + "|";
    }

    private String getGradeTypeName(String name) {
        String optionalName = Optional.ofNullable(name).orElse("Unknown grade");
        if (optionalName.isEmpty()) return "Unknown grade";
        return optionalName;
    }

    private String aligningTextInTheMiddleOfTableColumn(String str) {
        final int NUMBER_OF_SIDE_SPACES = (WIDTH_COLUMN[1] - str.length()) / 2;
        String rightSpace = str.length() % 2 == 0 ? " " : "";
        return " ".repeat(NUMBER_OF_SIDE_SPACES)
                + str
                + " ".repeat(NUMBER_OF_SIDE_SPACES)
                + rightSpace;
    }

    private String printDelimiterLine() {
        StringBuilder line = new StringBuilder("|");
        for (int k : WIDTH_COLUMN) {
            line.append("-".repeat(k));
            line.append("|");
        }
        return line.toString();
    }


}
