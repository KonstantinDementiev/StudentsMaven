package org.geekhub.studentsregistry.files;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StudentsFileFinderTest {

    private static final String DIRECTORY_PATH = "C:\\Users\\Public\\StudentsRecords";
    private static final String FILE_NAME = "Students.dat";
    private StudentsFileFinder studentsFileFinder;

    @BeforeMethod
    public void setUp() {
        studentsFileFinder = new StudentsFileFinder();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_file_name_is_null_throw_exception() {
        studentsFileFinder.findFile(null, Paths.get(DIRECTORY_PATH));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_file_name_is_empty_throw_exception() {
        studentsFileFinder.findFile("", Paths.get(DIRECTORY_PATH));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_directory_path_is_null_throw_exception() {
        studentsFileFinder.findFile(FILE_NAME, null);
    }

    @Test
    public void when_path_is_correct_find_existing_files_successfully() {
        studentsFileFinder.findFile(FILE_NAME, Paths.get(DIRECTORY_PATH));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_directory_path_is_incorrect_throw_exception() {
        studentsFileFinder.findFile(FILE_NAME, Paths.get("K:\\Users\\"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_file_name_is_incorrect_throw_exception() {
        studentsFileFinder.findFile("Students.txt", Paths.get(DIRECTORY_PATH));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_find_more_then_one_file_throw_exception() throws IOException {
        Path testDirectory = Paths.get(DIRECTORY_PATH).resolve("testFolder");
        Files.createDirectory(testDirectory);
        Files.createFile(testDirectory.resolve(FILE_NAME));
        studentsFileFinder.findFile(FILE_NAME, Paths.get(DIRECTORY_PATH));
        Files.deleteIfExists(testDirectory.resolve(FILE_NAME));
        Files.deleteIfExists(testDirectory);
    }

    @AfterMethod
    public void tearDown() throws IOException {
        Path testDirectory = Paths.get(DIRECTORY_PATH).resolve("testFolder");
        Files.deleteIfExists(testDirectory.resolve(FILE_NAME));
        Files.deleteIfExists(testDirectory);
    }
}