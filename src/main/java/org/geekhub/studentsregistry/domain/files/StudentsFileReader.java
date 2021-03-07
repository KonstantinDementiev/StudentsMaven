package org.geekhub.studentsregistry.domain.files;

import org.geekhub.studentsregistry.domain.anotations.Dependency;
import org.geekhub.studentsregistry.domain.logger.StudentsLogger;
import org.geekhub.studentsregistry.domain.students.Student;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Dependency
public class StudentsFileReader {

    private final static StudentsLogger LOG = new StudentsLogger(StudentsFileReader.class.getName());

    public List<Student> readStudentsFromFile(Path file) {
        List<Student> result = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(
                new FileInputStream(String.valueOf(file))))) {
            int studentsCount = (int) ois.readObject();
            Object object;
            for (int i = 0; i < studentsCount; i++) {
                object = ois.readObject();
                if (object instanceof Student) result.add((Student) object);
            }
        } catch (IOException | ClassNotFoundException e) {
            LOG.error("Can not read students from file: " + file, e);
        }
        return result;
    }


}
