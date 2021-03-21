package org.geekhub.studentsregistry.converters;

import org.geekhub.studentsregistry.grades.grade.RealGradeParser;
import org.geekhub.studentsregistry.students.Student;
import org.geekhub.studentsregistry.students.StudentDataForWeb;
import org.geekhub.studentsregistry.students.StudentsCreator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConverterStudentToWeb {

    private final StudentsCreator studentsCreator;
    private final RealGradeParser realGradeParser;

    public ConverterStudentToWeb(StudentsCreator studentsCreator, RealGradeParser realGradeParser) {
        this.studentsCreator = studentsCreator;
        this.realGradeParser = realGradeParser;
    }

    public Student createStudentFromScore(StudentDataForWeb data) {
        List<String> paramsForNewStudent = List.of(
                data.getName(), String.valueOf(data.getScore()), data.getGradeType());
        return studentsCreator.createStudent(paramsForNewStudent, data.getId());
    }

    public Student createStudentFromGrade(StudentDataForWeb data) {
        int score = realGradeParser.getScoreFromGrade(data.getRealGrade(), data.getGradeType());
        List<String> paramsForNewStudent = List.of(
                data.getName(), String.valueOf(score), data.getGradeType());
        return studentsCreator.createStudent(paramsForNewStudent, data.getId());
    }


}
