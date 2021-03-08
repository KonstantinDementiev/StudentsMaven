package org.geekhub.studentsregistry.files;

import org.geekhub.studentsregistry.grades.grade.GradeLetter;
import org.geekhub.studentsregistry.grades.grade.GradePercent;
import org.geekhub.studentsregistry.students.Student;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentsFileReaderTest {

    private final static Path RECORDS_FILE_DIRECTORY = Paths.get("C:\\Users\\Public\\StudentsRecords");
    private StudentsFileReader studentsFileReader;
    private Path studentsFile;
    private Path tempFile;

    @BeforeMethod
    public void setUp() throws IOException {
        studentsFile = Paths.get("C:\\Users\\Public\\StudentsRecords\\Students.dat");
        tempFile = RECORDS_FILE_DIRECTORY.resolve("testFile.tmp");
        Files.deleteIfExists(tempFile);
        Files.createFile(tempFile);
        studentsFileReader = new StudentsFileReader();
    }

    @Test
    public void when_reading_students_from_null_return_empty_list() {
        List<Student> result = studentsFileReader.readStudentsFromFile(null);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void when_reading_students_from_empty_file_return_empty_list() {
        List<Student> result = studentsFileReader.readStudentsFromFile(tempFile);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void reading_students_from_file_with_correct_path_is_successful() {
        List<Student> result = studentsFileReader.readStudentsFromFile(studentsFile);
        result.forEach(System.out::println);
    }

    @Test
    public void reading_students_from_file_with_correct_data_is_successful() {

        List<Student> expectedList = new ArrayList<>();
        expectedList.add(new Student("Mike", new GradePercent(74), LocalDateTime.MAX));
        expectedList.add(new Student("Sam", new GradePercent(98), LocalDateTime.MAX));
        expectedList.add(new Student("Sabrina", new GradeLetter(81), LocalDateTime.MAX));

        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream(String.valueOf(tempFile))))) {
            oos.writeObject(3);
            oos.writeObject(new Student("Mike", new GradePercent(74), LocalDateTime.MAX));
            oos.writeObject(new Student("Sam", new GradePercent(98), LocalDateTime.MAX));
            oos.writeObject(new Student("Sabrina", new GradeLetter(81), LocalDateTime.MAX));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Student> actualList = studentsFileReader.readStudentsFromFile(tempFile);
        Assert.assertEquals(actualList, expectedList);
    }

    @Test
    public void reading_students_from_file_with_incorrect_start_index_return_incorrect_list() {

        List<Student> expectedList = new ArrayList<>();
        expectedList.add(new Student("Mike", new GradePercent(74), LocalDateTime.MAX));
        expectedList.add(new Student("Sam", new GradePercent(98), LocalDateTime.MAX));

        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream(String.valueOf(tempFile))))) {
            oos.writeObject(1);
            oos.writeObject(new Student("Mike", new GradePercent(74), LocalDateTime.MAX));
            oos.writeObject(new Student("Sam", new GradePercent(98), LocalDateTime.MAX));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Student> actualList = studentsFileReader.readStudentsFromFile(tempFile);
        Assert.assertNotEquals(actualList, expectedList);
    }

    @Test
    public void reading_students_from_file_with_missing_student_data_is_successful() {

        List<Student> expectedList = new ArrayList<>();
        expectedList.add(new Student("Mike", new GradePercent(74), LocalDateTime.MAX));
        expectedList.add(new Student("Sabrina", new GradeLetter(81), LocalDateTime.MAX));

        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream(String.valueOf(tempFile))))) {
            oos.writeObject(3);
            oos.writeObject(new Student("Mike", new GradePercent(74), LocalDateTime.MAX));
            oos.writeObject(null);
            oos.writeObject(new Student("Sabrina", new GradeLetter(81), LocalDateTime.MAX));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Student> actualList = studentsFileReader.readStudentsFromFile(tempFile);
        Assert.assertEquals(actualList, expectedList);
    }

    @Test
    public void reading_students_from_file_with_lost_student_data_print_EOFException() {
        List<Student> expectedList = new ArrayList<>();
        expectedList.add(new Student("Mike", new GradePercent(74), LocalDateTime.MAX));
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream(String.valueOf(tempFile))))) {
            oos.writeObject(5);
            oos.writeObject(null);
            oos.writeObject(new Student("Mike", new GradePercent(74), LocalDateTime.MAX));
            oos.writeObject(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Student> actualList = studentsFileReader.readStudentsFromFile(tempFile);
        Assert.assertEquals(actualList, expectedList);
    }

    @AfterMethod
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }
}
