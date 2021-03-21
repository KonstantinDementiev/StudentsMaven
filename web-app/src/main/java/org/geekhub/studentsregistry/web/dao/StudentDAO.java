package org.geekhub.studentsregistry.web.dao;

import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.students.Student;

import java.util.List;
import java.util.Map;


public interface StudentDAO {

    List<Student> read();

    void write(List<Student> studentsToFile);

    Map<GradeType, List<Student>> show();

    Student showOne(int id);

    void save(Student student);

    void update(int id, Student newStudent);

    void delete(int id);

    int getStudentsCount();

    int getMaxId();

    void generateNewStudents(int studentsQuantity);

}
