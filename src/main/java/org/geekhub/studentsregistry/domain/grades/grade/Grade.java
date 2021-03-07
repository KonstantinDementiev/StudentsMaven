package org.geekhub.studentsregistry.domain.grades.grade;

public interface Grade {

    String asPrintVersion();

    Class<?> getGrade();

    Integer getValue();
}



