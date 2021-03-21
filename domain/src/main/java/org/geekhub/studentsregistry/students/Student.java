package org.geekhub.studentsregistry.students;

import org.geekhub.studentsregistry.interfaces.Grade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Grade grade;
    private LocalDateTime examDate;

    public Student() {
    }

    public Student(String name, Grade grade, LocalDateTime examDate) {
        this.name = name;
        this.grade = grade;
        this.examDate = examDate;
    }

    public Student(Integer id, String name, Grade grade, LocalDateTime examDate) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.examDate = examDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Grade getGrade() {
        return grade;
    }

    public LocalDateTime getExamDate() {
        return examDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setExamDate(LocalDateTime examDate) {
        this.examDate = examDate;
    }

    @Override
    public String toString() {
        return "Student { " +
                "id = '" + id + '\'' + ",\n" +
                "name = '" + name + '\'' + ",\n" +
                "gradeType = " + grade.getGrade().getSimpleName() + ",\n" +
                "gradeScore = " + grade.getValue() + ",\n" +
                "examDate = " + examDate
                .format(DateTimeFormatter.ofPattern("HH:mm:ss, dd.MM.yyyy")) + " }" +
                "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id) &&
                name.equals(student.name) &&
                Objects.equals(grade, student.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade);
    }
}
