package org.geekhub.studentsregistry.students.analyst;

import org.geekhub.studentsregistry.students.Student;

import java.util.List;
import java.util.Optional;

public interface StudentsAnalyst {

    Optional<Integer> averageScore(List<Student> students);
    Optional<Integer> maxScore(List<Student> students);
    Optional<Integer> minScore(List<Student> students);
    Optional<Integer> medianScore(List<Student> students);

}
