package org.geekhub.studentsregistry.web.service;

import com.google.common.collect.Lists;
import org.geekhub.studentsregistry.converters.ConverterAnalyticsToWeb;
import org.geekhub.studentsregistry.converters.ConverterStudentToWeb;
import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.inputgenerator.StudentsRandomGenerator;
import org.geekhub.studentsregistry.students.*;
import org.geekhub.studentsregistry.web.logger.WebLogger;
import org.geekhub.studentsregistry.web.repository.StudentRepo;
import org.geekhub.studentsregistry.web.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private static final WebLogger logger = new WebLogger(StudentServiceImpl.class.getName());
    private final StudentRepo studentRepo;
    private final ConverterStudentToWeb converterStudentToWeb;
    private final ConverterAnalyticsToWeb converterAnalyticsToWeb;
    private final StudentsComparator studentsComparator;
    private final StudentsFilterer studentsFilterer;
    private final StudentsRandomGenerator studentsRandomGenerator;
    private final StudentsCreator studentsCreator;

    @Autowired
    public StudentServiceImpl(
            StudentRepo studentRepo,
            ConverterStudentToWeb converterStudentToWeb,
            ConverterAnalyticsToWeb converterAnalyticsToWeb,
            StudentsComparator studentsComparator,
            StudentsFilterer studentsFilterer,
            StudentsRandomGenerator studentsRandomGenerator,
            StudentsCreator studentsCreator
    ) {
        this.studentRepo = studentRepo;
        this.converterStudentToWeb = converterStudentToWeb;
        this.converterAnalyticsToWeb = converterAnalyticsToWeb;
        this.studentsComparator = studentsComparator;
        this.studentsFilterer = studentsFilterer;
        this.studentsRandomGenerator = studentsRandomGenerator;
        this.studentsCreator = studentsCreator;
    }

    @Override
    public Map<GradeType, List<Student>> showAll() {
        List<Student> studentsList = Lists.newArrayList(studentRepo.findAll());
        return studentsFilterer.groupStudentsByGrade(
                studentsList.stream().sorted(studentsComparator).collect(Collectors.toList()));
    }

    @Override
    public Map<GradeType, List<String>> showAnalytics(Map<GradeType, List<Student>> students,
                                                      HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return converterAnalyticsToWeb.getAllAnalytics(students);
        } else {
            return new HashMap<>();
        }
    }

    @Override
    public Optional<Student> showOne(int id) {
        Optional<Student> student = studentRepo.findById(id);
        if (student.isPresent()) {
            logger.info("Student with ID= " + id + " successfully loaded!");
        } else {
            logger.info("Student with ID= " + id + " was not found!");
        }
        return student;
    }

    @Override
    public void saveStudentWithScore(StudentDataForWeb studentData) {
        studentRepo.save(converterStudentToWeb.createStudentFromScore(studentData));
        logger.info("Student with name '" + studentData.getName() + "' has been successfully created!");
    }

    @Override
    public void saveStudentWithGrade(StudentDataForWeb studentData) {
        studentRepo.save(converterStudentToWeb.createStudentFromGrade(studentData));
        logger.info("Student with name '" + studentData.getName() + "' has been successfully created!");
    }

    @Override
    public void update(int id, StudentDataForWeb studentData, String updateType) {
        if (studentRepo.existsById(id)) {
            Student newStudent;
            switch (updateType) {
                case "byScore" -> newStudent = converterStudentToWeb.createStudentFromScore(studentData);
                case "byGrade" -> newStudent = converterStudentToWeb.createStudentFromGrade(studentData);
                default -> throw new IllegalStateException("Unexpected value of updateType: " + updateType);
            }
            Student existStudent = studentRepo.findById(id).orElseThrow(IllegalArgumentException::new);
            existStudent.setName(newStudent.getName());
            existStudent.setGrade(newStudent.getGrade());
            existStudent.setExamDate(newStudent.getExamDate());
            studentRepo.save(existStudent);
            logger.info("Student with ID= " + id + " successfully updated!");
        } else {
            logger.info("Student with ID= " + id + " was not found!");
        }
    }

    @Override
    public void delete(int id) {
        if (studentRepo.existsById(id)) {
            studentRepo.deleteById(id);
            logger.info("Student with ID= " + id + " successfully removed!");
        } else {
            logger.info("Student with ID= " + id + " was not found!");
        }
    }

    @Override
    public void deleteAll() {
        studentRepo.deleteAll();
        logger.info("All students have been removed!");
    }

    @Override
    public long getStudentsCount() {
        return studentRepo.count();
    }

    @Override
    public void generateNewStudents(int studentsQuantity) {
        List<List<String>> newStudentsData = studentsRandomGenerator.createOriginalStudentsList(studentsQuantity);
        List<Student> newStudents = studentsCreator.createStudentsList(newStudentsData);
        studentRepo.saveAll(newStudents);
        logger.info(studentsQuantity + " students were successfully generated!");
    }

    @Override
    public StudentDataForWeb getDataFromStudent(int id) {
        Student student = this.showOne(id).orElseThrow(IllegalAccessError::new);
        return new StudentDataForWeb(
                id,
                student.getName(),
                student.getGrade().getValue(),
                student.getGrade().getGrade().getSimpleName().substring(5).toUpperCase());
    }

}
