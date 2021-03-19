package org.geekhub.studentsregistry.inputgenerator;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.interfaces.DataReader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Dependency
public class StudentsRandomGenerator implements DataReader {

    @Override
    public List<List<String>> createOriginalStudentsList(int totalStudentsCount) {
        checkForLegalArgument(totalStudentsCount);
        List<List<String>> resultList = new ArrayList<>();
        for (int i = 0; i < totalStudentsCount; i++) {
            List<String> newStudent = new ArrayList<>();
            newStudent.add(generateStudentsParameterByEnum(StudentsNames.values()));
            newStudent.add(generateStudentsScore());
            newStudent.add(generateStudentsParameterByEnum(GradeType.values()));
            resultList.add(newStudent);
        }
        return resultList;
    }

    private void checkForLegalArgument(int totalStudentsCount) {
        if (totalStudentsCount < 0) {
            throw new NegativeArraySizeException("Students number can not be negative.");
        }
    }

    private <T extends Enum<T>> String generateStudentsParameterByEnum(T[] enumElements) {
        int randomIndex = (int) (Math.random() * enumElements.length);
        return String.valueOf(enumElements[randomIndex]);
    }

    private String generateStudentsScore() {
        return "" + (int) (Math.random() * 100);
    }
}
