package org.geekhub.studentsregistry.files;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.logger.StudentsLogger;
import org.geekhub.studentsregistry.students.Student;
import org.springframework.stereotype.Component;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Component
@Dependency
public class StudentsFileWriter {

    private final static StudentsLogger LOG = new StudentsLogger(StudentsFileWriter.class.getName());

    public void writeStudentsToFile(List<Student> students, Path file) {
        List<Student> validStudents = getValidStudentsList(students);
        Path validFilePath = getValidFilePath(file);
        createDirectoryIfItDoesNotExist(validFilePath);
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream(String.valueOf(validFilePath))))) {
            oos.writeObject(validStudents.size());
            for (Student student : validStudents) {
                oos.writeObject(student);
            }
        } catch (IOException e) {
            LOG.error("Can not write org.geekhub.studentsregistry.students to file: " + file, e);
        }
    }

    private List<Student> getValidStudentsList(List<Student> students) {
        Optional<List<Student>> optList = Optional.ofNullable(students);
        return optList.orElseThrow(() -> new IllegalArgumentException("Students list is empty"));
    }

    private Path getValidFilePath(Path file) {
        Optional<Path> optPath = Optional.ofNullable(file);
        return optPath.orElseThrow(() -> new IllegalArgumentException("Path file is empty"));
    }

    private void createDirectoryIfItDoesNotExist(Path file) {
        if (Files.notExists(file.getParent())) {
            try {
                Files.createDirectory(file.getParent());
            } catch (IOException e) {
                throw new IllegalArgumentException("Can not create directory: " + file.getParent());
            }
        }
    }

}
