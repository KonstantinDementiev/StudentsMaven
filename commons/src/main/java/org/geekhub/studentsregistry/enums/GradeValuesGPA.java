package org.geekhub.studentsregistry.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum GradeValuesGPA {

    A("4.0"),
    B("3.0"),
    C("2.0"),
    D("1.0"),
    F("0.0");

    private final String title;

    GradeValuesGPA(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<String> getAllTitles(){
        return Arrays.stream(GradeValuesGPA.values()).map(e -> e.title).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "GradeValuesGPA{" +
                "title='" + title + '\'' +
                '}';
    }
}
