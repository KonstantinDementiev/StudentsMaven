package org.geekhub.studentsregistry.students;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;


public class StudentsRegistryCreatorTest {

    private StudentsRegistryCreator studentsRegistryCreator;

    @BeforeMethod
    public void setUp() {
        studentsRegistryCreator = new StudentsRegistryCreator();
    }

    @Test
    public void students_registry_is_created_not_nul() {
        Scanner scanner = new Scanner("");
        StudentsRegistry studentsRegistry = studentsRegistryCreator.create(scanner);
        assertThat(studentsRegistry).isNotNull();
    }

    @Test
    public void students_registry_is_created_successfully() {
        assertThatCode(() -> {
            Scanner scanner = new Scanner("");
            studentsRegistryCreator.create(scanner);
        }).doesNotThrowAnyException();
    }

    @Test
    public void students_registry_is_created_with_all_required_fields() {
        Scanner scanner = new Scanner("");
        StudentsRegistry studentsRegistry = studentsRegistryCreator.create(scanner);
        assertThat(studentsRegistry).hasNoNullFieldsOrProperties();
    }

}
