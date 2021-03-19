package org.geekhub.studentsregistry.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum GradeValuesUkraine {

    A("12"),
    B("11"),
    C("10"),
    D("9"),
    F("8"),
    H("7"),
    J("6"),
    K("5"),
    L("4"),
    M("3"),
    N("2"),
    O("1");

    private final String title;

    GradeValuesUkraine(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<String> getAllTitles(){
        return Arrays.stream(GradeValuesUkraine.values()).map(e -> e.title).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "GradeValuesUkraine{" +
                "title='" + title + '\'' +
                '}';
    }
}
