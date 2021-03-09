package org.geekhub.studentsregistry.web.dao;

import org.geekhub.studentsregistry.files.StudentsFileFinder;
import org.geekhub.studentsregistry.files.StudentsFileReader;
import org.geekhub.studentsregistry.files.StudentsFileWriter;
import org.geekhub.studentsregistry.students.Student;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class StudentDAO {

    private final static String RECORDS_FILE_NAME = "Students.dat";
    private final static Path RECORDS_FILE_DIRECTORY = Paths.get("C:\\Users\\Public\\StudentsRecords");

    private final StudentsFileFinder studentsFileFinder;
    private final StudentsFileReader studentsFileReader;
    private final StudentsFileWriter studentsFileWriter;

    public StudentDAO(StudentsFileFinder studentsFileFinder,
                      StudentsFileReader studentsFileReader,
                      StudentsFileWriter studentsFileWriter) {
        this.studentsFileFinder = studentsFileFinder;
        this.studentsFileReader = studentsFileReader;
        this.studentsFileWriter = studentsFileWriter;
    }


    public void create(Student student) {

    }

    public List<Student> read() {
        Path existingStudentsFile = studentsFileFinder.findFile(RECORDS_FILE_NAME, RECORDS_FILE_DIRECTORY);
        return studentsFileReader.readStudentsFromFile(existingStudentsFile);
    }

    public void update(int id, Student updatedStudent) {

    }

    public void delete(int id) {

    }

}
