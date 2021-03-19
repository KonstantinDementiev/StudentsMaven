package org.geekhub.studentsregistry.grades.grade;

import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.interfaces.Grade;
import org.geekhub.studentsregistry.logger.StudentsLogger;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class RealGradeParser {

    private final static StudentsLogger LOG = new StudentsLogger(RealGradeParser.class.getName());
    private static final int MAX_SCORE = 100;
    private static final int MIN_SCORE = 0;
    private static final int SCORE_STEP = 4;

    public int getScoreFromGrade(String inputGradeValues, String inputGradeType) {
        GradeType gradeType = GradeType.from(inputGradeType);
        String realGrade = getRealGrade(inputGradeValues, gradeType);
        try {
            return switch (gradeType) {
                case LETTER -> getScoreFromGradeClass(realGrade, GradeLetter.class);
                case PERCENTAGE -> getScoreFromGradeClass(realGrade, GradePercentage.class);
                case GPA -> getScoreFromGradeClass(realGrade, GradeGPA.class);
                case UKRAINE -> getScoreFromGradeClass(realGrade, GradeUkraine.class);
            };
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            LOG.error("Entered grade type not found: ", e);
        }
        return 0;
    }

    private String getRealGrade(String allValues, GradeType gradeType){
        String [] grades = allValues.split(",");
        return switch (gradeType) {
            case LETTER -> grades[0];
            case PERCENTAGE -> grades[1];
            case GPA -> grades[2];
            case UKRAINE -> grades[3];
        };
    }

    private int getScoreFromGradeClass(String realGrade, Class<?> gradeClass)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        String currentGrade;
        for (int i = MIN_SCORE; i < MAX_SCORE; i += SCORE_STEP) {
            Grade grade = (Grade) gradeClass.getDeclaredConstructors()[0].newInstance(i);
            currentGrade = grade.asPrintVersion();
            if (currentGrade.equals(realGrade)) return i;
        }
        return 0;
    }

}
