package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.files.StudentsFileFinder;
import org.geekhub.studentsregistry.files.StudentsFileReader;
import org.geekhub.studentsregistry.files.StudentsFileWriter;
import org.geekhub.studentsregistry.grades.grade.GradeLetter;
import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.enums.DataSourceMode;
import org.geekhub.studentsregistry.interfaces.DataReader;
import org.geekhub.studentsregistry.outputconsole.ConsoleAnalyticsPrinter;
import org.geekhub.studentsregistry.outputconsole.ConsoleStudentsPrinter;
import org.geekhub.studentsregistry.analytics.StudentsAnalyst;
import org.geekhub.studentsregistry.printconverters.ConverterAnalyticsToPrint;
import org.geekhub.studentsregistry.printconverters.ConverterStudentsToPrint;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;

public class StudentsRegistryTest {

    @Mock
    private DataReader dataReader;
    @Mock
    private StudentsCreator studentsCreator;
    @Mock
    private StudentsFilterer studentsFilterer;
    @Mock
    private StudentsComparator studentsComparator;
    @Mock
    private ConverterStudentsToPrint converterStudentsToPrint;
    @Mock
    private ConsoleStudentsPrinter consoleStudentsPrinter;
    @Mock
    private StudentsAnalyst studentsAnalyst;
    @Mock
    private ConverterAnalyticsToPrint converterAnalyticsToPrint;
    @Mock
    private ConsoleAnalyticsPrinter consoleAnalyticsPrinter;

    private StudentsFileFinder studentsFileFinder;
    private StudentsFileReader studentsFileReader;
    private StudentsFileWriter studentsFileWriter;

    private final int TOTAL_STUDENTS_NUMBER = 1;
    private List<List<String>> enteredStudents;
    private List<Student> createdStudents;
    private Map<GradeType, List<Student>> filteredStudents;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        enteredStudents = new ArrayList<>();
        List<String> student = List.of("Zina", "50", "LETTER");
        enteredStudents.add(student);
        createdStudents = new ArrayList<>();
        createdStudents.add(new Student("Zina", new GradeLetter(50), LocalDateTime.now()));
        filteredStudents = new HashMap<>();
        for (GradeType gradeType : GradeType.values()) {
            filteredStudents.put(gradeType, createdStudents);
        }
        studentsFileFinder = new StudentsFileFinder();
        studentsFileReader = new StudentsFileReader();
        studentsFileWriter = new StudentsFileWriter();
    }

    @Test
    public void application_flow_is_completed_successfully_with_manual_input() {
        StudentsRegistry studentsRegistryConsole = new StudentsRegistry(
                dataReader,
                studentsCreator,
                studentsFilterer,
                studentsComparator,
                converterStudentsToPrint,
                consoleStudentsPrinter,
                studentsAnalyst,
                converterAnalyticsToPrint,
                consoleAnalyticsPrinter,
                studentsFileFinder,
                studentsFileReader,
                studentsFileWriter
        );
        Mockito.when(studentsCreator.createStudentsList(eq(enteredStudents))).thenReturn(createdStudents);
        Mockito.when(studentsFilterer.groupStudentsByGrade(eq(createdStudents))).thenReturn(filteredStudents);
        studentsRegistryConsole.run(TOTAL_STUDENTS_NUMBER, DataSourceMode.MANUAL);
        Mockito.verify(consoleStudentsPrinter, Mockito.times(4)).printOneGradeTypeStudents(anyList());
    }

    @Test
    public void application_flow_is_completed_successfully_with_auto_input() {
        StudentsRegistry studentsRegistryGenerator = new StudentsRegistry(
                dataReader,
                studentsCreator,
                studentsFilterer,
                studentsComparator,
                converterStudentsToPrint,
                consoleStudentsPrinter,
                studentsAnalyst,
                converterAnalyticsToPrint,
                consoleAnalyticsPrinter,
                studentsFileFinder,
                studentsFileReader,
                studentsFileWriter
        );
        Mockito.when(studentsCreator.createStudentsList(eq(enteredStudents))).thenReturn(createdStudents);
        Mockito.when(studentsFilterer.groupStudentsByGrade(eq(createdStudents))).thenReturn(filteredStudents);
        studentsRegistryGenerator.run(TOTAL_STUDENTS_NUMBER, DataSourceMode.AUTO);
        Mockito.verify(consoleStudentsPrinter, Mockito.times(4)).printOneGradeTypeStudents(anyList());
    }
}
