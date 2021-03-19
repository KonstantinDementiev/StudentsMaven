package org.geekhub.studentsregistry.enums;

public enum GradeType {
    LETTER, PERCENTAGE, GPA, UKRAINE;

    public static GradeType from(String grade) {
        StringBuilder allGradeTypes = new StringBuilder();
        for (var gradeType : GradeType.values()) {
            if (gradeType.name().equals(grade)) {
                return gradeType;
            }
            allGradeTypes.append(gradeType.name()).append(", ");
        }
        throw new IllegalArgumentException("Incorrect Grade Type. Please, enter one of next Grade types: "
                + allGradeTypes.toString());
    }

}