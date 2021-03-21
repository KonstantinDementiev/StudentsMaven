package org.geekhub.studentsregistry.converters;

import org.geekhub.studentsregistry.analytics.StudentsAnalyst;
import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.students.Student;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConverterAnalyticsToWeb {

    private final ConverterAnalyticsToPrint converterAnalyticsToPrint;
    private final StudentsAnalyst studentsAnalyst;

    public ConverterAnalyticsToWeb(
            ConverterAnalyticsToPrint converterAnalyticsToPrint,
            StudentsAnalyst studentsAnalyst
    ) {
        this.converterAnalyticsToPrint = converterAnalyticsToPrint;
        this.studentsAnalyst = studentsAnalyst;
    }

    public Map<GradeType, List<String>> getAllAnalytics(Map<GradeType, List<Student>> students) {
        Map<GradeType, List<String>> result = new HashMap<>();
        for (GradeType gradeType : GradeType.values()) {
            result.put(gradeType, converterAnalyticsToPrint.getPrintDataForOneGradeType(
                    gradeType, students.get(gradeType), studentsAnalyst));
        }
        return result;
    }

}
