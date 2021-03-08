package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.interfaces.Grade;
import org.geekhub.studentsregistry.grades.gradeFactory.GradeFactory;
import org.geekhub.studentsregistry.grades.gradeFactory.GradeFactoryImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dependency
public class StudentsCreator {

    public List<Student> createStudentsList(List<List<String>> originalStudentsArray) {
        Optional<List<List<String>>> optionalOriginalStudentsArray = Optional.ofNullable(originalStudentsArray);
        return optionalOriginalStudentsArray.stream()
                .flatMap(List::stream)
                .map(this::createStudent)
                .collect(Collectors.toList());
    }

    private Student createStudent(List<String> enteredStudent) {
        return new Student(enteredStudent.get(0),
                createGradeForOneStudent(enteredStudent.get(1), enteredStudent.get(2)),
                LocalDateTime.now()
        );
    }

    private Grade createGradeForOneStudent(String gradeNumber, String gradeType) {
        final GradeFactory gradeFactory = new GradeFactoryImpl();
        return gradeFactory.createGrade(
                parseGradeTypeFromString(gradeType), parseGradeNumberFromString(gradeNumber));
    }

    private GradeType parseGradeTypeFromString(String gradeTypeName) {
        return GradeType.from(gradeTypeName);
    }

    private int parseGradeNumberFromString(String grateNumber) {
        return Integer.parseInt(grateNumber);
    }


}