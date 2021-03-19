package org.geekhub.studentsregistry.classloaders;

import org.geekhub.studentsregistry.students.StudentsCreator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MyClassLoaderTest {

    private final static String PATH_CLASS_CORRECT = "org.geekhub.studentsregistry.students.StudentsCreator";
    private final static String PATH_CLASS_INCORRECT = "org.studentsregistry.StudentsCreator";
    private final static String[] PATH_PACKAGE_CORRECT =
            {"G:/JavaProject/GeekHub_project/HomeworkMaven/domain/target/classes"};
    private final static String[] PATH_PACKAGE_INCORRECT =
            {"G:/JavaProject/GeekHub_project/classes/"};
    private MyClassLoader myClassLoader;

    @BeforeMethod
    public void setUp() {
        myClassLoader = new MyClassLoader(PATH_PACKAGE_CORRECT);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void loader_throw_exception_when_path_name_is_null() throws ClassNotFoundException {
        Class.forName(null, false, myClassLoader);
    }

    @Test(expectedExceptions = ClassNotFoundException.class)
    public void loader_throw_exception_when_path_name_is_incorrect() throws ClassNotFoundException {
        Class.forName(PATH_CLASS_INCORRECT, false, myClassLoader);
    }

    @Test
    public void loader_can_find_class_successfully_when_path_is_correct() throws ClassNotFoundException {
        Class<?> actualClass = Class.forName(PATH_CLASS_CORRECT, false, myClassLoader);
        Class<?> expectedClass = StudentsCreator.class;
        Assert.assertEquals(actualClass.getName(), expectedClass.getName());
    }

    @Test
    public void class_from_my_loader_is_not_same_class_from_default_loader() throws ClassNotFoundException {
        Class<?> classFromMyLoader = Class.forName(PATH_CLASS_CORRECT, true, myClassLoader);
        Class<?> classFromDefaultLoader = StudentsCreator.class;
        Assert.assertNotEquals(classFromMyLoader, classFromDefaultLoader);
    }

    @Test
    public void class_from_my_loader_is_the_same_class_from_default_loader_when_path_package_is_incorrect()
            throws ClassNotFoundException {
        myClassLoader = new MyClassLoader(PATH_PACKAGE_INCORRECT);
        Class<?> classFromMyLoader = Class.forName(PATH_CLASS_CORRECT, false, myClassLoader);
        Class<?> classFromDefaultLoader = StudentsCreator.class;
        Assert.assertEquals(classFromMyLoader, classFromDefaultLoader);
    }

    @Test
    public void classes_from_my_loader_with_true_and_false_initialize_is_the_same()
            throws ClassNotFoundException {
        Class<?> classFromMyLoader1 = Class.forName(PATH_CLASS_CORRECT, true, myClassLoader);
        Class<?> classFromMyLoader2 = Class.forName(PATH_CLASS_CORRECT, false, myClassLoader);
        Assert.assertEquals(classFromMyLoader1, classFromMyLoader2);
    }

    @Test
    public void same_classes_from_different_instances_of_my_loader_is_not_same()
            throws ClassNotFoundException {
        Class<?> classFromMyLoader1 = Class.forName(PATH_CLASS_CORRECT, true, myClassLoader);
        myClassLoader = new MyClassLoader(PATH_PACKAGE_CORRECT);
        Class<?> classFromMyLoader2 = Class.forName(PATH_CLASS_CORRECT, true, myClassLoader);
        Assert.assertNotEquals(classFromMyLoader1, classFromMyLoader2);
    }


}
