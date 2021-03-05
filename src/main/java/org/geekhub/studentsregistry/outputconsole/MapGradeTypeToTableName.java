package org.geekhub.studentsregistry.outputconsole;

import org.geekhub.studentsregistry.inputstudentsdata.GradeType;

import java.util.HashMap;
import java.util.Map;

public abstract class MapGradeTypeToTableName {

    public static Map<GradeType, String> getGradeTableName() {
        Map<GradeType, String> gradeTableName = new HashMap<>();
        gradeTableName.put(GradeType.GPA, "GPA");
        gradeTableName.put(GradeType.LETTER, "Letter");
        gradeTableName.put(GradeType.PERCENTAGE, "Percentage");
        return gradeTableName;
    }

}
