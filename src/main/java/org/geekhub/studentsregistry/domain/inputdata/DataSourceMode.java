package org.geekhub.studentsregistry.domain.inputdata;

import org.geekhub.studentsregistry.domain.exceptions.unchecked.InvalidGradeArgumentException;

public enum DataSourceMode {
    MANUAL, AUTO;

    public static DataSourceMode from(String inputMode) {
        StringBuilder allModes = new StringBuilder();
        for (var mode : DataSourceMode.values()) {
            if (mode.name().equals(inputMode)) {
                return mode;
            }
            allModes.append(mode.name()).append(", ");
        }
        throw new InvalidGradeArgumentException("Input mode is not supported. Please, enter one of next: "
                + allModes.toString() + " you can use only UPPER case");
    }
}
