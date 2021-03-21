package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.analytics.StudentsAnalyst;
import org.geekhub.studentsregistry.enums.DataSourceMode;
import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.exceptions.checked.EmptyArgumentException;
import org.geekhub.studentsregistry.exceptions.checked.NumberFormatArgumentException;
import org.geekhub.studentsregistry.exceptions.checked.ZeroArgumentException;
import org.geekhub.studentsregistry.files.StudentsFileFinder;
import org.geekhub.studentsregistry.files.StudentsFileReader;
import org.geekhub.studentsregistry.files.StudentsFileWriter;
import org.geekhub.studentsregistry.inputgenerator.StudentsRandomGenerator;
import org.geekhub.studentsregistry.interfaces.DataReader;
import org.geekhub.studentsregistry.logger.StudentsLogger;
import org.geekhub.studentsregistry.outputconsole.ConsoleAnalyticsPrinter;
import org.geekhub.studentsregistry.outputconsole.ConsoleStudentsPrinter;
import org.geekhub.studentsregistry.converters.ConverterAnalyticsToPrint;
import org.geekhub.studentsregistry.converters.ConverterStudentsToPrint;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;


public class StudentsRegistry {

    private final static StudentsLogger LOG = new StudentsLogger(StudentsRegistry.class.getName());
    private final static int MAX_FILE_SIZE = 1024000;
    private final static String RECORDS_FILE_NAME = "Students.dat";
    private final static String TEMPORARY_FILE_PREFIX = "data";
    private final static Path RECORDS_FILE_DIRECTORY = Paths.get("C:\\Users\\Public\\StudentsRecords");

    private final DataReader dataReader;
    private final StudentsCreator studentsCreator;
    private final StudentsFilterer studentsFilterer;
    private final StudentsComparator studentsComparator;
    private final ConverterStudentsToPrint converterStudentsToPrint;
    private final ConsoleStudentsPrinter consoleStudentsPrinter;
    private final StudentsAnalyst studentsAnalyst;
    private final ConverterAnalyticsToPrint converterAnalyticsToPrint;
    private final ConsoleAnalyticsPrinter consoleAnalyticsPrinter;
    private final StudentsFileFinder studentsFileFinder;
    private final StudentsFileReader studentsFileReader;
    private final StudentsFileWriter studentsFileWriter;

    public StudentsRegistry(
            DataReader dataReader,
            StudentsCreator studentsCreator,
            StudentsFilterer studentsFilterer,
            StudentsComparator studentsComparator,
            ConverterStudentsToPrint converterStudentsToPrint,
            ConsoleStudentsPrinter consoleStudentsPrinter,
            StudentsAnalyst studentsAnalyst,
            ConverterAnalyticsToPrint converterAnalyticsToPrint,
            ConsoleAnalyticsPrinter consoleAnalyticsPrinter,
            StudentsFileFinder studentsFileFinder,
            StudentsFileReader studentsFileReader,
            StudentsFileWriter studentsFileWriter
    ) {
        this.dataReader = dataReader;
        this.studentsCreator = studentsCreator;
        this.studentsFilterer = studentsFilterer;
        this.studentsComparator = studentsComparator;
        this.converterStudentsToPrint = converterStudentsToPrint;
        this.consoleStudentsPrinter = consoleStudentsPrinter;
        this.studentsAnalyst = studentsAnalyst;
        this.converterAnalyticsToPrint = converterAnalyticsToPrint;
        this.consoleAnalyticsPrinter = consoleAnalyticsPrinter;
        this.studentsFileFinder = studentsFileFinder;
        this.studentsFileReader = studentsFileReader;
        this.studentsFileWriter = studentsFileWriter;
    }

    public static void main(String[] args) {

        int totalStudentsCount = getTotalStudentsCount(args);
        DataSourceMode dataSourceMode = getInputMode(args);
        System.out.printf("Input mode: %s%nNew students count: %d%n", dataSourceMode, totalStudentsCount);
        try (var scanner = new Scanner(System.in)) {
            StudentsRegistryCreator studentsRegistryCreator = new StudentsRegistryCreator();
            StudentsRegistry studentsRegistry = studentsRegistryCreator.create(scanner);
            studentsRegistry.run(totalStudentsCount, dataSourceMode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void run(int totalStudentsCount, DataSourceMode dataSourceMode) {

        Path existingStudentsFile = studentsFileFinder.findFile(RECORDS_FILE_NAME, RECORDS_FILE_DIRECTORY);
        List<Student> oldStudents = studentsFileReader.readStudentsFromFile(existingStudentsFile);
        List<Student> newStudents = createStudentsList(totalStudentsCount, dataSourceMode, oldStudents.size());
        List<Student> commonStudentsLists = getCommonStudentsLists(newStudents, oldStudents, existingStudentsFile);
        studentsFileWriter.writeStudentsToFile(commonStudentsLists, existingStudentsFile);

        Map<GradeType, List<Student>> studentsByGrope = gropeStudentsToMap(sortStudentList(commonStudentsLists));
        System.out.printf("Total students count: %d%n", commonStudentsLists.size());
        printStudentsInfo(studentsByGrope);
        LOG.info("All students printed successfully!\n");
    }

    private List<Student> getCommonStudentsLists(List<Student> newStudents, List<Student> oldStudents,
                                                 Path existingFile) {
        int[] filesSizes = getFilesSizes(newStudents, existingFile);
        if (filesSizes[1] > MAX_FILE_SIZE) {
            throw new OutOfMemoryError("New file size is more than " + MAX_FILE_SIZE / 1000 + " KB");
        } else if (filesSizes[0] + filesSizes[1] > MAX_FILE_SIZE) {
            return newStudents;
        } else {
            oldStudents.addAll(newStudents);
            return oldStudents;
        }
    }

    private int[] getFilesSizes(List<Student> newStudents, Path existingFile) {
        int[] fileSize = new int[2];
        try {
            Path tempNewStudentsFile = Files.createTempFile(RECORDS_FILE_DIRECTORY, TEMPORARY_FILE_PREFIX, null);
            studentsFileWriter.writeStudentsToFile(newStudents, tempNewStudentsFile);
            fileSize[0] = (int) Files.size(existingFile);
            fileSize[1] = (int) Files.size(tempNewStudentsFile);
            Files.delete(tempNewStudentsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileSize;
    }

    private List<Student> createStudentsList(int totalStudentsCount, DataSourceMode dataSourceMode, int startId) {
        setForDataReaderActualImplementation(dataSourceMode);
        List<List<String>> enteredStudents = dataReader.createOriginalStudentsList(totalStudentsCount);
        return studentsCreator.createStudentsList(enteredStudents);
    }

    private void setForDataReaderActualImplementation(DataSourceMode dataSourceMode) {
        if (dataSourceMode == DataSourceMode.AUTO) {
            Class<?> stReg = StudentsRegistry.class;
            try {
                Field dataReader = stReg.getDeclaredField("dataReader");
                dataReader.setAccessible(true);
                dataReader.set(this, new StudentsRandomGenerator());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                LOG.error("Input DataReader error: ", e);
            }
        }
    }

    private List<Student> sortStudentList(List<Student> studentsToSort) {
        return studentsToSort.stream().sorted(studentsComparator).collect(Collectors.toList());
    }

    private Map<GradeType, List<Student>> gropeStudentsToMap(List<Student> studentsToGrope) {
        return studentsFilterer.groupStudentsByGrade(studentsToGrope);
    }

    private void printStudentsInfo(Map<GradeType, List<Student>> students) {
        for (GradeType gradeType : GradeType.values()) {
            consoleStudentsPrinter.printOneGradeTypeStudents(
                    converterStudentsToPrint.convertStudentsGroupToPrint(
                            gradeType, students.get(gradeType)));
            consoleAnalyticsPrinter.printOneGradeTypeAnalytics(
                    converterAnalyticsToPrint.getPrintDataForOneGradeType(
                            gradeType, students.get(gradeType), studentsAnalyst));
        }
    }

    private static int getTotalStudentsCount(String[] argument) {
        int count = 0;
        try {
            if (argument.length < 1) {
                throw new EmptyArgumentException("Total students count is expected as a first start argument but missing");
            }
            try {
                count = Integer.parseInt(argument[0]);
                if (count == 0) throw new ZeroArgumentException("Argument can not be zero");
            } catch (NumberFormatException e) {
                throw new NumberFormatArgumentException("Argument should be a positive integer number");
            }
        } catch (EmptyArgumentException | ZeroArgumentException | NumberFormatArgumentException e) {
            e.printStackTrace();
        }
        return count;
    }

    private static DataSourceMode getInputMode(String[] argument) {
        try {
            if (argument.length < 2) {
                throw new EmptyArgumentException("Input type argument is empty.");
            }
        } catch (EmptyArgumentException e) {
            e.printStackTrace();
        }
        return DataSourceMode.from(argument[1]);
    }

}
