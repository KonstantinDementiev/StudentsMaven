package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.inputdata.inputrandomgenerator.StudentsRandomGenerator;
import org.geekhub.studentsregistry.logger.StudentsLogger;
import org.geekhub.studentsregistry.outputconsole.ConsoleStudentsAnalystPrinter;
import org.geekhub.studentsregistry.outputconsole.ConsoleStudentsPrinter;
import org.geekhub.studentsregistry.reflection.MyClassLoader;
import org.geekhub.studentsregistry.students.printconverters.ConverterStudentsAnalyticsInfoToPrint;
import org.geekhub.studentsregistry.students.printconverters.ConverterStudentsToPrint;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

public class StudentsRegistryCreator2 {

    private final static StudentsLogger LOG = new StudentsLogger(StudentsRegistryCreator2.class.getName());
    private final static String[] PATH_PACKAGE_NAME =
            {"G:/JavaProject/GeekHub_project/geekhub-x/Homework/domain/build/classes/java/main/"};
    private final static ClassLoader MY_CLASS_LOADER = new MyClassLoader(PATH_PACKAGE_NAME);
    private final static String MAIN_CLASS_NAME = "org.geekhub.studentsregistry.students.StudentsRegistry";
    private final static String MAIN_CLASS_DYNAMIC = "org.geekhub.studentsregistry.students.DynamicSon";
    private final static String DIRECTORY_PATH = "org.geekhub.studentsregistry";

    public StudentsRegistry create(Scanner scanner) {
        return new StudentsRegistry(
                new StudentsRandomGenerator(),
                new StudentsCreator(),
                new StudentsFilterer(),
                new StudentsComparator(),
                new ConverterStudentsToPrint(),
                new ConsoleStudentsPrinter(),
                new ConverterStudentsAnalyticsInfoToPrint(),
                new ConsoleStudentsAnalystPrinter()
        );
    }

    public StudentsRegistry create2(Scanner scanner) {
        final Class<?> clazz = StudentsRegistry.class;
        System.out.println("--------Class: " + clazz.hashCode());
        final URL[] urls = new URL[1];
        urls[0] = clazz.getProtectionDomain().getCodeSource().getLocation();
        final ClassLoader delegateParent = clazz.getClassLoader().getParent();
        try (final URLClassLoader cl = new URLClassLoader(urls, delegateParent)) {
            final Class<?> reloadedClazz = cl.loadClass(clazz.getName());
            System.out.println("----------Class reloaded: " + reloadedClazz.hashCode());
            System.out.println("----------Are the same: " + (clazz != reloadedClazz));
            while (true) System.out.print("-");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
