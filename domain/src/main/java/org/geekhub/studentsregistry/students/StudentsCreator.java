package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.interfaces.Grade;
import org.geekhub.studentsregistry.grades.gradeFactory.GradeFactory;
import org.geekhub.studentsregistry.grades.gradeFactory.GradeFactoryImpl;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Dependency
public class StudentsCreator {

    public List<Student> createStudentsList(List<List<String>> originalStudentsArray) {
        Optional<List<List<String>>> optionalList = Optional.ofNullable(originalStudentsArray);
        return optionalList.stream()
                .flatMap(List::stream)
                .map(this::createStudent)
                .collect(Collectors.toList());
    }

    public Student createStudent(List<String> enteredData) {
        return new Student(
                enteredData.get(0),
                createGradeForOneStudent(enteredData.get(1), enteredData.get(2)),
                LocalDateTime.now()
        );
    }

    private Grade createGradeForOneStudent(String gradeNumber, String gradeType) {
        final GradeFactory gradeFactory = new GradeFactoryImpl();
        return gradeFactory.createGrade(
                parseGradeTypeFromString(gradeType), parseNumberFromString(gradeNumber));
    }

    private GradeType parseGradeTypeFromString(String gradeTypeName) {
        return GradeType.from(gradeTypeName);
    }

    private int parseNumberFromString(String grateNumber) {
        return Integer.parseInt(grateNumber);
    }


}
