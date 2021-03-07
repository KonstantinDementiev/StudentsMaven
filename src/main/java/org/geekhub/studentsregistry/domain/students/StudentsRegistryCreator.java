package org.geekhub.studentsregistry.domain.students;

import org.geekhub.studentsregistry.domain.anotations.Dependency;
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

    private final static String CLASSES_SEARCHING_DIRECTORY_PATH = "org.geekhub.studentsregistry";
    private Scanner scanner;

    public StudentsRegistry create(Scanner scan) {
        scanner = Optional.ofNullable(scan).orElseThrow();
        try {
            return createStudentsRegistry(
                    getClassesWithDependencyAnnotation(),
                    getConstructorFromStudentsRegistry());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException("Cann`t create StudentsRegistry!", e);
        } finally {
            scanner.close();
        }
    }

    private List<Class<?>> getClassesWithDependencyAnnotation() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(CLASSES_SEARCHING_DIRECTORY_PATH))
                .setScanners(new TypeAnnotationsScanner(), new SubTypesScanner()));
        return new ArrayList<>(reflections.getTypesAnnotatedWith(Dependency.class));
    }

    private Constructor<?> getConstructorFromStudentsRegistry() {
        Class<?> clazz = StudentsRegistry.class;
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        return constructors[0];
    }

    private StudentsRegistry createStudentsRegistry(List<Class<?>> classesWithDependency,
                                                    Constructor<?> constructor)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        int parametersCount = constructor.getParameterCount();
        if (parametersCount > 0 && parametersCount <= classesWithDependency.size()) {
            return (StudentsRegistry) createNewObjectByReflection(classesWithDependency, constructor);
        }
        throw new IllegalArgumentException("Cann`t to collect StudentsRegistry from classes");
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
                argumentsListForNewInstance[i] = getObjectFromInterface(parameter, classesForNewInstance);
            } else {
                argumentsListForNewInstance[i] = addNewParameter(parameter);
            }
        }

        Class<?> newClass = StudentsRegistry.class;
        return newClass.getDeclaredConstructors()[0].newInstance(argumentsListForNewInstance);
    }

    private Class<?> getClassByName(String name, List<Class<?>> classes) {
        return classes.stream().filter(c -> c.getName().equals(name)).findFirst().orElseThrow();
    }

    private Object getObjectFromInterface(Class<?> interfaceClass, List<Class<?>> classesForNewInstance)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {
        List<Class<?>> implementClasses = getInterfaceImplementations(interfaceClass, classesForNewInstance);
        Class<?> classNeededScanner = implementClasses.stream()
                .filter(this::isThisClassNeedsScanner).findFirst().orElse(null);
        return null == classNeededScanner
                ? implementClasses.get(1).getDeclaredConstructor().newInstance()
                : classNeededScanner.getDeclaredConstructor(scanner.getClass()).newInstance(scanner);
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
