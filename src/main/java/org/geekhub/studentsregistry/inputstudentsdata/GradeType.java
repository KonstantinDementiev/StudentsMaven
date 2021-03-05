package org.geekhub.studentsregistry.inputstudentsdata;

import org.geekhub.studentsregistry.exceptions.unchecked.InvalidGradeArgumentException;

public enum GradeType {
    LETTER, PERCENTAGE, GPA;

    public static GradeType from(String grade) {
        StringBuilder allGradeTypes = new StringBuilder();
        for (var gradeType : GradeType.values()) {
            if (gradeType.name().equals(grade)) {
                return gradeType;
            }
            allGradeTypes.append(gradeType.name()).append(", ");
        }
        throw new InvalidGradeArgumentException("Incorrect Grade Type. Please, enter one of next Grade types: "
                + allGradeTypes.toString() + " you can use lower case");
    }

}