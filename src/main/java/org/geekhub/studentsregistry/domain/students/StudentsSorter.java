package org.geekhub.studentsregistry.domain.students;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class StudentsSorter {

    private final Comparator<Student> STUDENTS_COMPARATOR = new StudentsComparator();

    public List<Student> sortStudentsByScoreAndName(List<Student> studentsToSort) {
        Optional<List<Student>> optionalStudentsToSort = Optional.ofNullable(studentsToSort);
        List<Student> sortedStudents = new ArrayList<>();
        optionalStudentsToSort.ifPresent(students -> {
            sortedStudents.addAll(students);
            sortedStudents.sort(STUDENTS_COMPARATOR);
        });
        return sortedStudents;
    }

}
