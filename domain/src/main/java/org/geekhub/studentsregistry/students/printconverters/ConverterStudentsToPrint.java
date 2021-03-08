package org.geekhub.studentsregistry.students.printconverters;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.students.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dependency
public class ConverterStudentsToPrint {

    public List<HashMap<String, String>> convertStudentsToPrintFormat(List<Student> studentsToPrint) {
        return Optional.ofNullable(studentsToPrint)
                .orElseThrow(() -> new IllegalArgumentException("Students list for printing is empty"))
                .stream()
                .map(this::createNewStudentToPrint)
                .collect(Collectors.toList());
    }

    private HashMap<String, String> createNewStudentToPrint(Student student) {
        HashMap<String, String> newStudent = new HashMap<>();
        newStudent.put("Name", student.name());
        newStudent.put("Grade", student.grade().asPrintVersion());
        return newStudent;
    }


}
