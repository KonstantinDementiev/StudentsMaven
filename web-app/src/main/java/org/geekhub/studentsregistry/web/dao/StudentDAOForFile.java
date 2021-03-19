package org.geekhub.studentsregistry.web.dao;

import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.files.StudentsFileFinder;
import org.geekhub.studentsregistry.files.StudentsFileReader;
import org.geekhub.studentsregistry.files.StudentsFileWriter;
import org.geekhub.studentsregistry.inputgenerator.StudentsRandomGenerator;
import org.geekhub.studentsregistry.students.Student;
import org.geekhub.studentsregistry.students.StudentsComparator;
import org.geekhub.studentsregistry.students.StudentsCreator;
import org.geekhub.studentsregistry.students.StudentsFilterer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StudentDAOForFile implements StudentDAO {

    private static final String RECORDS_FILE_NAME = "Students.dat";
    private static final Path RECORDS_FILE_DIRECTORY = Paths.get("C:\\Users\\Public\\StudentsRecords");

    private final StudentsComparator studentsComparator;
    private final StudentsFilterer studentsFilterer;
    private final StudentsFileFinder studentsFileFinder;
    private final StudentsFileReader studentsFileReader;
    private final StudentsFileWriter studentsFileWriter;
    private final StudentsRandomGenerator studentsRandomGenerator;
    private final StudentsCreator studentsCreator;


    @Autowired
    public StudentDAOForFile(StudentsComparator studentsComparator,
                             StudentsFilterer studentsFilterer,
                             StudentsFileFinder studentsFileFinder,
                             StudentsFileReader studentsFileReader,
                             StudentsFileWriter studentsFileWriter,
                             StudentsRandomGenerator studentsRandomGenerator,
                             StudentsCreator studentsCreator) {
        this.studentsComparator = studentsComparator;
        this.studentsFilterer = studentsFilterer;
        this.studentsFileFinder = studentsFileFinder;
        this.studentsFileReader = studentsFileReader;
        this.studentsFileWriter = studentsFileWriter;
        this.studentsRandomGenerator = studentsRandomGenerator;
        this.studentsCreator = studentsCreator;
    }

    @Override
    public List<Student> read() {
        Path existingStudentsFile = studentsFileFinder.findFile(RECORDS_FILE_NAME, RECORDS_FILE_DIRECTORY);
        return studentsFileReader.readStudentsFromFile(existingStudentsFile);
    }

    @Override
    public void write(List<Student> studentsToFile) {
        Path existingStudentsFile = studentsFileFinder.findFile(RECORDS_FILE_NAME, RECORDS_FILE_DIRECTORY);
        studentsFileWriter.writeStudentsToFile(studentsToFile, existingStudentsFile);
    }

    @Override
    public Map<GradeType, List<Student>> show() {
        return studentsFilterer.groupStudentsByGrade(
                read().stream().sorted(studentsComparator).collect(Collectors.toList()));
    }

    @Override
    public Student showOne(int id) {
        return read().stream().filter(s -> s.getId() == id).findFirst().orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void save(Student student) {
        List<Student> studentsFromFile = read();
        studentsFromFile.add(student);
        write(studentsFromFile);
    }

    @Override
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

    @Override
    public void delete(int id) {
        List<Student> studentsFromFile = read();
        studentsFromFile.removeIf(student -> student.getId() == id);
        write(studentsFromFile);
    }

    @Override
    public int getStudentsCount() {
        return read().size();
    }

    @Override
    public int getMaxId() {
        return read().stream().map(Student::getId).max(Integer::compareTo).orElse(0);
    }

    @Override
    public void generate(int studentsQuantity){
        List<List<String>> newStudentsData = studentsRandomGenerator.createOriginalStudentsList(studentsQuantity);
        List<Student> newStudents = studentsCreator.createStudentsList(newStudentsData, getMaxId());
        List<Student> studentsFromFile = read();
        studentsFromFile.addAll(newStudents);
        write(studentsFromFile);
    }
}
