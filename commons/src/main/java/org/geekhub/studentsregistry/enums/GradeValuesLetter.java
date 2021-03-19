package org.geekhub.studentsregistry.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum GradeValuesLetter {

    A("A"),
    B("B"),
    C("C"),
    D("D"),
    F("F");

    private final String title;

    GradeValuesLetter(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<String> getAllTitles(){
        return Arrays.stream(GradeValuesLetter.values()).map(e -> e.title).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "GradeValuesLetter{" +
                "title='" + title + '\'' +
                '}';
    }
}
