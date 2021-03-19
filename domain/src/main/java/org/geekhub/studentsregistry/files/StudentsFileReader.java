package org.geekhub.studentsregistry.files;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.logger.StudentsLogger;
import org.geekhub.studentsregistry.students.Student;
import org.springframework.stereotype.Component;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
@Dependency
public class StudentsFileReader {

    private final static StudentsLogger LOG = new StudentsLogger(StudentsFileReader.class.getName());

    public List<Student> readStudentsFromFile(Path file) {
        List<Student> result = new ArrayList<>();
        if (Files.exists(file)) {
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
        }
        return result;
    }


}
