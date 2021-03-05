package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.inputdata.DataSourceMode;
import org.geekhub.studentsregistry.inputdata.inputrandomgenerator.StudentsRandomGenerator;
import org.geekhub.studentsregistry.inputstudentsdata.DataReader;
import org.geekhub.studentsregistry.inputstudentsdata.GradeType;
import org.geekhub.studentsregistry.logger.StudentsLogger;
import org.geekhub.studentsregistry.outputconsole.ConsoleStudentsAnalystPrinter;
import org.geekhub.studentsregistry.outputconsole.ConsoleStudentsPrinter;
import org.geekhub.studentsregistry.students.exceptions.EmptyArgumentException;
import org.geekhub.studentsregistry.students.exceptions.NumberFormatArgumentException;
import org.geekhub.studentsregistry.students.exceptions.ZeroArgumentException;
import org.geekhub.studentsregistry.students.printconverters.ConverterStudentsAnalyticsInfoToPrint;
import org.geekhub.studentsregistry.students.printconverters.ConverterStudentsToPrint;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StudentsRegistry {

    private final static StudentsLogger LOG = new StudentsLogger(StudentsRegistry.class.getName());
    private final DataReader dataReader;
    private final StudentsCreator studentsCreator;
    private final StudentsFilterer studentsFilterer;
    private final StudentsComparator studentsComparator;
    private final ConverterStudentsToPrint converterStudentsToPrint;
    private final ConsoleStudentsPrinter consoleStudentsPrinter;
    private final ConverterStudentsAnalyticsInfoToPrint converterStudentsAnalyticsInfoToPrint;
    private final ConsoleStudentsAnalystPrinter consoleStudentsAnalystPrinter;

    public StudentsRegistry(
            DataReader dataReader,
            StudentsCreator studentsCreator,
            StudentsFilterer studentsFilterer,
            StudentsComparator studentsComparator,
            ConverterStudentsToPrint converterStudentsToPrint,
            ConsoleStudentsPrinter consoleStudentsPrinter,
            ConverterStudentsAnalyticsInfoToPrint converterStudentsAnalyticsInfoToPrint,
            ConsoleStudentsAnalystPrinter consoleStudentsAnalystPrinter
    ) {
        this.dataReader = dataReader;
        this.studentsCreator = studentsCreator;
        this.studentsFilterer = studentsFilterer;
        this.studentsComparator = studentsComparator;
        this.converterStudentsToPrint = converterStudentsToPrint;
        this.consoleStudentsPrinter = consoleStudentsPrinter;
        this.converterStudentsAnalyticsInfoToPrint = converterStudentsAnalyticsInfoToPrint;
        this.consoleStudentsAnalystPrinter = consoleStudentsAnalystPrinter;
    }

    public static void main(String[] args) {
        int totalStudentsCount = getTotalStudentsCount(args);
        DataSourceMode dataSourceMode = getInputMode(args);
        System.out.printf("Input mode: %s. Students count: %d%n", dataSourceMode, totalStudentsCount);

        try (var scanner = new Scanner(System.in)) {
            StudentsRegistryCreator studentsRegistryCreator = new StudentsRegistryCreator();
            StudentsRegistry studentsRegistry = studentsRegistryCreator.create(scanner);
            studentsRegistry.run(totalStudentsCount, dataSourceMode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void run(int totalStudentsCount, DataSourceMode dataSourceMode) {

        Map<GradeType, List<Student>> studentsByGrope =
                gropeStudentsToMap(
                        sortStudentList(
                                createStudentsList(totalStudentsCount, dataSourceMode)));
        printStudentsInfo(studentsByGrope);
        LOG.info("All students printed successfully!\n");
    }

    private List<Student> createStudentsList(int totalStudentsCount, DataSourceMode dataSourceMode) {
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
            consoleStudentsPrinter.printStudentsTable(gradeType,
                    converterStudentsToPrint.convertStudentsToPrintFormat(
                            students.get(gradeType)));
            consoleStudentsAnalystPrinter.printDataForOneGradeType(
                    converterStudentsAnalyticsInfoToPrint.getPrintDataForOneGradeType(
                            gradeType, students.get(gradeType)));
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
