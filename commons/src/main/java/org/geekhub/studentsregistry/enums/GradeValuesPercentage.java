package org.geekhub.studentsregistry.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum GradeValuesPercentage {

    A("90 - 100%"),
    B("80 - 89%"),
    C("70 - 79%"),
    D("60 - 69%"),
    F("< 60%");

    private final String title;

    GradeValuesPercentage(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<String> getAllTitles(){
        return Arrays.stream(GradeValuesPercentage.values()).map(e -> e.title).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "GradeValuesPercentage{" +
                "title='" + title + '\'' +
                '}';
    }
}
