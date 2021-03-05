package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.logger.StudentsLogger;
import org.geekhub.studentsregistry.reflection.MyClassLoader;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class StudentsRegistryCreator {

    private final static StudentsLogger LOG = new StudentsLogger(StudentsRegistryCreator.class.getName());
    private final static String CLASSES_SEARCHING_DIRECTORY_PATH = "org.geekhub.studentsregistry";
    private final static String MAIN_CLASS_NAME = "org.geekhub.studentsregistry.students.StudentsRegistry";
    private final static String[] PATH_PACKAGE_NAME =
            {"G:/JavaProject/GeekHub_project/geekhub-x/Homework/domain/build/classes/java/main/"};
    private final static ClassLoader MY_CLASS_LOADER = new MyClassLoader(PATH_PACKAGE_NAME);
    private Scanner scanner;

    public StudentsRegistry create(Scanner scan) {
        scanner = Optional.ofNullable(scan).orElseThrow();
        try {
            return createStudentsRegistry(
                    getClassesWithDependencyAnnotation(),
                    getConstructorFromStudentsRegistry());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            LOG.warning("Cann`t create StudentsRegistry: ", e);
        }
        scanner.close();
        throw new IllegalArgumentException("Cann`t create StudentsRegistry!");
    }

    private List<Class<?>> getClassesWithDependencyAnnotation() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(CLASSES_SEARCHING_DIRECTORY_PATH))
                .setScanners(new TypeAnnotationsScanner(), new SubTypesScanner()));
        return new ArrayList<>(reflections.getTypesAnnotatedWith(Dependency.class));
    }

    private Constructor<?> getConstructorFromStudentsRegistry() {
        Class<?> clazz = StudentsRegistry.class;
//        Class<?> clazz = getClassFromMyClassLoader(MAIN_CLASS_NAME).orElseThrow();
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        return constructors[0];
    }

    private Optional<Class<?>> getClassFromMyClassLoader(String className) {
        try {
            return Optional.of(Class.forName(className, false, MY_CLASS_LOADER));
        } catch (ClassNotFoundException e) {
            LOG.warning("Can not find class with this name", e);
        }
        return Optional.empty();
    }

    private StudentsRegistry createStudentsRegistry(List<Class<?>> classesWithDependency,
                                                    Constructor<?> constructor)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        int parametersCount = constructor.getParameterCount();
        if (parametersCount > 0 && parametersCount <= classesWithDependency.size()) {
            return (StudentsRegistry) createNewObjectByReflection(classesWithDependency, constructor);
        }
        throw new IllegalArgumentException("Cann`t create StudentsRegistry!");
    }

    private Object createNewObjectByReflection(List<Class<?>> classesForNewInstance, Constructor<?> studentsConstructor)
            throws IllegalAccessException, InvocationTargetException,
            InstantiationException, NoSuchMethodException, ClassNotFoundException {

        int parametersCount = studentsConstructor.getParameterCount();
        List<String> classes = classesForNewInstance.stream().map(Class::getName).collect(Collectors.toList());
        Class<?>[] constructorParameterTypes = studentsConstructor.getParameterTypes();
        Object[] argumentsListForNewInstance = new Object[parametersCount];
        Class<?> parameter;
        for (int i = 0; i < parametersCount; i++) {
            parameter = getClassByName(constructorParameterTypes[i].getName(), classesForNewInstance);
            if (!classes.contains(parameter.getName())) {
                throw new ClassNotFoundException("Not found input class to create StudentsRegistry: "
                        + parameter.getSimpleName());
            } else if (parameter.isInterface()) {
                argumentsListForNewInstance[i] = getObjectWithScannerFromInterface(parameter, classesForNewInstance);
            } else {
                argumentsListForNewInstance[i] = addNewParameter(parameter);
            }
        }
        //Class<?> newClass = new MyClassLoader(PATH_PACKAGE_NAME).loadClass(MAIN_CLASS_NAME);
        Class<?> newClass = StudentsRegistry.class;
        return newClass.getDeclaredConstructors()[0].newInstance(argumentsListForNewInstance);
    }

    private Class<?> getClassByName(String name, List<Class<?>> classes) {
        return classes.stream().filter(c -> c.getName().equals(name)).findFirst().orElseThrow();
    }

    private Object getObjectWithScannerFromInterface(Class<?> interfaceClass, List<Class<?>> classesForNewInstance)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {
        List<Class<?>> implementClasses = getInterfaceImplementations(interfaceClass, classesForNewInstance);
        Class<?> classWithScanner = implementClasses.stream()
                .filter(this::isThisClassNeedsScanner).findAny()
                .orElseThrow(() -> new ClassNotFoundException("Can not find class with scanner for this interface"));
        return classWithScanner.getDeclaredConstructor(scanner.getClass()).newInstance(scanner);
    }

    private Object addNewParameter(Class<?> classForParameter)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException,
            IllegalAccessException {
        return isThisClassNeedsScanner(classForParameter)
                ? getNewInstanceWithParameter(classForParameter, scanner)
                : getNewInstanceWithoutParameters(classForParameter);
    }

    private List<Class<?>> getInterfaceImplementations(Class<?> interfaceClass, List<Class<?>> allFoundedClasses) {
        return allFoundedClasses.stream()
                .filter(interfaceClass::isAssignableFrom)
                .collect(Collectors.toList());
    }

    private boolean isThisClassNeedsScanner(Class<?> inputClass) {
        try {
            Constructor<?>[] constructors = inputClass.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                Class<?>[] parameters = constructor.getParameterTypes();
                return Arrays.stream(parameters).map(Class::getName)
                        .anyMatch(name -> name.equals("java.util.Scanner"));
            }
        } catch (SecurityException | IllegalArgumentException ignored) {
        }
        return false;
    }

    private Object getNewInstanceWithParameter(Class<?> classForParameter, Object param)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {
        return classForParameter.getDeclaredConstructor(param.getClass()).newInstance(param);
    }

    private Object getNewInstanceWithoutParameters(Class<?> classForParameter)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {
        return classForParameter.getDeclaredConstructor().newInstance();
    }

}
