package org.geekhub.studentsregistry.grades.grade;

public interface Grade {

    String asPrintVersion();

    Class<?> getGrade();

    Integer getValue();
}



