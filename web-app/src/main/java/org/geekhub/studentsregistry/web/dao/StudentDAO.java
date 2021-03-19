package org.geekhub.studentsregistry.web.dao;

import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.files.StudentsFileFinder;
import org.geekhub.studentsregistry.files.StudentsFileReader;
import org.geekhub.studentsregistry.files.StudentsFileWriter;
import org.geekhub.studentsregistry.students.Student;
import org.geekhub.studentsregistry.students.StudentsComparator;
import org.geekhub.studentsregistry.students.StudentsFilterer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StudentDAO {

    private static final String RECORDS_FILE_NAME = "Students.dat";
    private static final Path RECORDS_FILE_DIRECTORY = Paths.get("C:\\Users\\Public\\StudentsRecords");

    private final StudentsComparator studentsComparator;
    private final StudentsFilterer studentsFilterer;
    private final StudentsFileFinder studentsFileFinder;
    private final StudentsFileReader studentsFileReader;
    private final StudentsFileWriter studentsFileWriter;

    @Autowired
    public StudentDAO(StudentsComparator studentsComparator,
                      StudentsFilterer studentsFilterer,
                      StudentsFileFinder studentsFileFinder,
                      StudentsFileReader studentsFileReader,
                      StudentsFileWriter studentsFileWriter) {
        this.studentsComparator = studentsComparator;
        this.studentsFilterer = studentsFilterer;
        this.studentsFileFinder = studentsFileFinder;
        this.studentsFileReader = studentsFileReader;
        this.studentsFileWriter = studentsFileWriter;
    }

    public List<Student> read() {
        Path existingStudentsFile = studentsFileFinder.findFile(RECORDS_FILE_NAME, RECORDS_FILE_DIRECTORY);
        return studentsFileReader.readStudentsFromFile(existingStudentsFile);
    }

    public void write(List<Student> studentsToFile) {
        Path existingStudentsFile = studentsFileFinder.findFile(RECORDS_FILE_NAME, RECORDS_FILE_DIRECTORY);
        studentsFileWriter.writeStudentsToFile(studentsToFile, existingStudentsFile);
    }

    public Map<GradeType, List<Student>> show() {
        return studentsFilterer.groupStudentsByGrade(
                read().stream().sorted(studentsComparator).collect(Collectors.toList()));
    }

    public Student showOne(int id) {
        return read().stream().filter(s -> s.getId() == id).findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public void save(Student student) {
        List<Student> studentsFromFile = read();
        studentsFromFile.add(student);
        write(studentsFromFile);
    }

    public void update(int id, Student newStudent) {
        List<Student> studentsFromFile = read();
        Student oldStudent = studentsFromFile.stream()
                .filter(s -> s.getId() == id)
                .findFirst().orElseThrow(IllegalArgumentException::new);
        oldStudent.setName(newStudent.getName());
        oldStudent.setGrade(newStudent.getGrade());
        oldStudent.setExamDate(newStudent.getExamDate());
        write(studentsFromFile);
    }

    public void delete(int id) {
        List<Student> studentsFromFile = read();
        studentsFromFile.removeIf(student -> student.getId() == id);
        write(studentsFromFile);
    }

    public int getStudentsCount(){
        return read().size();
    }

    public int getMaxId(){
        return read().stream().map(Student::getId).max(Integer::compareTo).orElse(0);
    }

}
