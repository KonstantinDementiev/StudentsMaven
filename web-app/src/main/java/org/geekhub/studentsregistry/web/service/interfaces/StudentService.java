package org.geekhub.studentsregistry.web.service.interfaces;

import org.geekhub.studentsregistry.enums.*;
import org.geekhub.studentsregistry.students.Student;
import org.geekhub.studentsregistry.students.StudentDataForWeb;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface StudentService {

    Map<GradeType, List<Student>> showAll();

    Map<GradeType, List<String>> showAnalytics(Map<GradeType, List<Student>> ctudents);

    Optional<Student> showOne(int id);

    void saveStudentWithScore(StudentDataForWeb studentData);

    void saveStudentWithGrade(StudentDataForWeb studentData);

    void update(int id, StudentDataForWeb studentData, String updateType);

    void delete(int id);

    void deleteAll();

    long getStudentsCount();

    void generateNewStudents(int studentsQuantity);

    StudentDataForWeb getDataFromStudent(int id);

    static List<List<String>> getAllRealGrades() {
        List<String> letter = GradeValuesLetter.getAllTitles();
        List<String> percentage = GradeValuesPercentage.getAllTitles();
        List<String> gpa = GradeValuesGPA.getAllTitles();
        List<String> ukraine = GradeValuesUkraine.getAllTitles();
        return List.of(letter, percentage, gpa, ukraine);
    }

}
