package org.geekhub.studentsregistry.students;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

public class StudentDataForWeb {

    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Range(min = 0, max = 100, message = "Score should be between 0 and 100 include")
    private int score;

    private String gradeType;

    private String realGrade;

    public StudentDataForWeb() {
    }

    public StudentDataForWeb(int id, String name, int score, String gradeType) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.gradeType = gradeType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public String getRealGrade() {
        return realGrade;
    }

    public void setRealGrade(String realGrade) {
        this.realGrade = realGrade;
    }

    @Override
    public String toString() {
        return "StudentDataForWeb{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", gradeType='" + gradeType + '\'' +
                ", realGrade='" + realGrade + '\'' +
                '}';
    }
}


