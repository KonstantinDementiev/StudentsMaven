package org.geekhub.studentsregistry.analytics;

import org.geekhub.studentsregistry.interfaces.Grade;
import org.geekhub.studentsregistry.grades.grade.GradeWithValue;
import org.geekhub.studentsregistry.students.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StudentsAnalystImpl implements StudentsAnalyst {

    private IntSummaryStatistics summaryStatistics = new IntSummaryStatistics();
    private List<Student> existingStudents = new ArrayList<>();

    @Override
    public Optional<Integer> maxScore(List<Student> students) {
        getSummaryStatistics(students);
        return summaryStatistics.getCount() > 0
                ? Optional.of(summaryStatistics.getMax())
                : Optional.empty();
    }

    @Override
    public Optional<Integer> minScore(List<Student> students) {
        getSummaryStatistics(students);
        return summaryStatistics.getCount() > 0
                ? Optional.of(summaryStatistics.getMin())
                : Optional.empty();
    }

    @Override
    public Optional<Integer> averageScore(List<Student> students) {
        getSummaryStatistics(students);
        return summaryStatistics.getCount() > 0
                ? Optional.of((int) summaryStatistics.getAverage())
                : Optional.empty();
    }

    @Override
    public Optional<Integer> medianScore(List<Student> students) {
        List<Student> optionalStudents = Optional.ofNullable(students).orElse(new ArrayList<>());
        List<Integer> gradesWithValue = optionalStudents.stream()
                .filter(s -> s.getGrade() instanceof GradeWithValue)
                .map(Student::getGrade)
                .map(Grade::getValue)
                .sorted()
                .collect(Collectors.toList());
        return gradesWithValue.isEmpty()
                ? Optional.empty()
                : Optional.of(getMedianScoreValue(gradesWithValue));
    }

    private int getMedianScoreValue(List<Integer> scoresList) {
        int medianElementIndex = scoresList.size() / 2;
        return scoresList.get(medianElementIndex);
    }

    private void getSummaryStatistics(List<Student> students) {
        List<Student> optionalStudents = Optional.ofNullable(students).orElse(new ArrayList<>());
        if (isIncomingStudentsNotSame(optionalStudents)) {
            summaryStatistics = optionalStudents.stream()
                    .filter(s -> s.getGrade() instanceof GradeWithValue)
                    .map(Student::getGrade)
                    .mapToInt(Grade::getValue)
                    .summaryStatistics();
            existingStudents = students;
        }
    }

    private boolean isIncomingStudentsNotSame(List<Student> students) {
        return !existingStudents.equals(students);
    }

}
