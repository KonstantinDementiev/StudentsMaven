package org.geekhub.studentsregistry.files;

import org.geekhub.studentsregistry.grades.grade.GradeGPA;
import org.geekhub.studentsregistry.grades.grade.GradeLetter;
import org.geekhub.studentsregistry.grades.grade.GradePercentage;
import org.geekhub.studentsregistry.students.Student;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentsFileWriterTest {

    private StudentsFileWriter studentsFileWriter;
    private List<Student> studentsToAdd;
    private Path file;

    @BeforeMethod
    public void setUp() {
        file = Paths.get("C:\\Users\\Public\\StudentsRecords\\Students.dat");
        studentsFileWriter = new StudentsFileWriter();
        studentsToAdd = new ArrayList<>();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void writing_null_to_file_with_correct_path_is_throw_exception() {
        studentsFileWriter.writeStudentsToFile(null, file);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void writing_students_to_file_with_null_path_is_throw_exception() {
        studentsToAdd.add(new Student("Tom", new GradeLetter(95), LocalDateTime.now()));
        studentsFileWriter.writeStudentsToFile(studentsToAdd, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void writing_students_to_file_with_incorrect_path_is_throw_exception() {
        Path file = Paths.get("W:\\Users\\Students.dat");
        studentsToAdd.add(new Student("Tom", new GradeLetter(95), LocalDateTime.now()));
        studentsFileWriter.writeStudentsToFile(studentsToAdd, file);
    }

    @Test
    public void writing_empty_students_list_to_file_with_is_successful() {
        studentsFileWriter.writeStudentsToFile(studentsToAdd, file);
    }

    @Test
    public void writing_students_to_file_with_correct_path_is_successful() {
        studentsToAdd.add(new Student("Tom", new GradeLetter(95), LocalDateTime.now()));
        studentsToAdd.add(new Student("Bob", new GradePercentage(80), LocalDateTime.now()));
        studentsToAdd.add(new Student("Alan", new GradeGPA(88), LocalDateTime.now()));
        studentsFileWriter.writeStudentsToFile(studentsToAdd, file);
    }


}
