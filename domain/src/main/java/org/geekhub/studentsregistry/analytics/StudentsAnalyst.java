package org.geekhub.studentsregistry.analytics;

import org.geekhub.studentsregistry.anotations.Dependency;
import org.geekhub.studentsregistry.students.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Dependency
public interface StudentsAnalyst {

    Optional<Integer> averageScore(List<Student> students);

    Optional<Integer> maxScore(List<Student> students);

    Optional<Integer> minScore(List<Student> students);

    Optional<Integer> medianScore(List<Student> students);
}
