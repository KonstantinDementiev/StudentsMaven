package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.grades.grade.GradeLetter;
import org.geekhub.studentsregistry.inputdata.DataSourceMode;
import org.geekhub.studentsregistry.inputdata.inputrandomgenerator.StudentsRandomGenerator;
import org.geekhub.studentsregistry.inputstudentsdata.DataReader;
import org.geekhub.studentsregistry.inputstudentsdata.GradeType;
import org.geekhub.studentsregistry.outputconsole.ConsoleStudentsAnalystPrinter;
import org.geekhub.studentsregistry.outputconsole.ConsoleStudentsPrinter;
import org.geekhub.studentsregistry.students.printconverters.ConverterStudentsAnalyticsInfoToPrint;
import org.geekhub.studentsregistry.students.printconverters.ConverterStudentsToPrint;
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
    private ConverterStudentsAnalyticsInfoToPrint converterStudentsAnalyticsInfoToPrint;
    @Mock
    private ConsoleStudentsAnalystPrinter consoleStudentsAnalystPrinter;

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
                converterStudentsAnalyticsInfoToPrint,
                consoleStudentsAnalystPrinter
        );
        Mockito.when(studentsCreator.createStudentsList(eq(enteredStudents))).thenReturn(createdStudents);
        Mockito.when(studentsFilterer.groupStudentsByGrade(eq(createdStudents))).thenReturn(filteredStudents);
        studentsRegistryConsole.run(TOTAL_STUDENTS_NUMBER, DataSourceMode.MANUAL);
        Mockito.verify(consoleStudentsPrinter, Mockito.times(3)).printStudentsTable(any(), anyList());
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
                converterStudentsAnalyticsInfoToPrint,
                consoleStudentsAnalystPrinter
        );
        Mockito.when(studentsCreator.createStudentsList(eq(enteredStudents))).thenReturn(createdStudents);
        Mockito.when(studentsFilterer.groupStudentsByGrade(eq(createdStudents))).thenReturn(filteredStudents);
        studentsRegistryGenerator.run(TOTAL_STUDENTS_NUMBER, DataSourceMode.AUTO);
        Mockito.verify(consoleStudentsPrinter, Mockito.times(3)).printStudentsTable(any(), anyList());
    }
}
