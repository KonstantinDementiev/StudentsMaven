package org.geekhub.studentsregistry.reflection;

import org.geekhub.studentsregistry.students.StudentsCreator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MyClassLoaderTest {

    private final static String PATH_CLASS_CORRECT = "org.geekhub.studentsregistry.students.StudentsCreator";
    private final static String PATH_CLASS_INCORRECT = "students.StudentsCreator";
    private final static String[] PATH_PACKAGE_CORRECT =
            {"G:/JavaProject/GeekHub_project/geekhub-x/Homework/domain/build/classes/java/main/"};
    private final static String[] PATH_PACKAGE_INCORRECT =
            {"G:/JavaProject/GeekHub_project/classes/"};
    private MyClassLoader myClassLoader;

    @BeforeMethod
    public void setUp() {
        myClassLoader = new MyClassLoader(PATH_PACKAGE_CORRECT);
    }

    @Test(expectedExceptions = ClassNotFoundException.class)
    public void loader_throw_exception_when_path_name_is_incorrect() throws ClassNotFoundException {
        myClassLoader.findClass(PATH_CLASS_INCORRECT);
    }

    @Test
    public void loader_do_not_return_null_when_path_is_correct() throws ClassNotFoundException {
        Assert.assertNotNull(myClassLoader.findClass(PATH_CLASS_CORRECT));
    }

    @Test
    public void loader_can_find_class_successfully_when_path_is_correct() throws ClassNotFoundException {
        Class<?> actualClass = myClassLoader.findClass(PATH_CLASS_CORRECT);
        Class<?> expectedClass = StudentsCreator.class;
        Assert.assertEquals(actualClass.getName(), expectedClass.getName());
    }

    @Test
    public void class_from_my_loader_is_not_same_class_from_default_loader() throws ClassNotFoundException {
        Class<?> classFromMyLoader = myClassLoader.findClass(PATH_CLASS_CORRECT);
        Class<?> classFromDefaultLoader = StudentsCreator.class;
        Assert.assertNotEquals(classFromMyLoader, classFromDefaultLoader);
    }

    @Test
    public void class_from_my_loader_is_the_same_class_from_default_loader_when_path_package_is_incorrect()
            throws ClassNotFoundException {
        myClassLoader = new MyClassLoader(PATH_PACKAGE_INCORRECT);
        Class<?> classFromMyLoader = myClassLoader.findClass(PATH_CLASS_CORRECT);
        Class<?> classFromDefaultLoader = StudentsCreator.class;
        Assert.assertEquals(classFromMyLoader, classFromDefaultLoader);
    }

}
