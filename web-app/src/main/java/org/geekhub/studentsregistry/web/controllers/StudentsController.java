package org.geekhub.studentsregistry.web.controllers;

import org.geekhub.studentsregistry.enums.*;
import org.geekhub.studentsregistry.grades.grade.RealGradeParser;
import org.geekhub.studentsregistry.students.Student;
import org.geekhub.studentsregistry.students.StudentDataForWeb;
import org.geekhub.studentsregistry.students.StudentsCreator;
import org.geekhub.studentsregistry.web.dao.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentsController {

    private static final GradeType[] GRADE_TYPES = GradeType.values();
    private final StudentDAO studentDAO;
    private final StudentsCreator studentsCreator;
    private final RealGradeParser realGradeParser;

    @Autowired
    public StudentsController(
            StudentDAO studentDAO,
            StudentsCreator studentsCreator,
            RealGradeParser realGradeParser
    ) {
        this.studentDAO = studentDAO;
        this.studentsCreator = studentsCreator;
        this.realGradeParser = realGradeParser;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("students", studentDAO.show());
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("studentsCount", studentDAO.getStudentsCount());
        return "show";
    }

    @GetMapping("/new/score")
    public String newStudentsByScore(Model model) {
        int id = studentDAO.getMaxId() + 1;
        model.addAttribute("studentData", new StudentDataForWeb());
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("studentId", id);
        return "new/score";
    }

    @PostMapping("/create/score")
    public String createStudentsByScore(Model model,
                                        @RequestParam("currentId") int id,
                                        @ModelAttribute("studentData") @Valid StudentDataForWeb studentData,
                                        BindingResult bindingResult) {
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("studentId", id);
        studentData.setId(id);
        if (bindingResult.hasErrors()) return "new/score";
        model.addAttribute("studentId", id + 1);
        studentDAO.save(createStudentFromScore(studentData));
        return "new/score";
    }

    @GetMapping("/new/grade")
    public String newStudentsByGrade(Model model) {
        int id = studentDAO.getMaxId() + 1;
        model.addAttribute("studentData", new StudentDataForWeb());
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("gradesValues", getAllRealGrades());
        model.addAttribute("studentId", id);
        return "new/grade";
    }

    @PostMapping("/create/grade")
    public String createStudentsByGrade(Model model,
                                        @RequestParam("currentId") int id,
                                        @ModelAttribute("studentData") @Valid StudentDataForWeb studentData,
                                        BindingResult bindingResult) {
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("gradesValues", getAllRealGrades());
        model.addAttribute("studentId", id);
        studentData.setId(id);
        if (bindingResult.hasErrors()) return "new/grade";
        model.addAttribute("studentId", id + 1);
        studentDAO.save(createStudentFromGrade(studentData));
        return "new/grade";
    }

    @GetMapping("/new/random")
    public String newStudentsRandom() {
        return "new/random";
    }

    @PostMapping("/create/random")
    public String generateStudentsRandom(@RequestParam("randomNumber") int number) {
        studentDAO.generate(number);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("studentData", getDataFromStudent(id));
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("studentId", id);
        model.addAttribute("gradesValues", getAllRealGrades());
        return "edit";
    }

    @PostMapping("/{id}/update/score")
    public String updateScore(Model model,
                         @PathVariable("id") int id,
                         @ModelAttribute("studentData") @Valid StudentDataForWeb studentData,
                         BindingResult bindingResult) {
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("studentId", id);
        model.addAttribute("gradesValues", getAllRealGrades());
        if (bindingResult.hasErrors()) return "edit";
        studentDAO.update(id, createStudentFromScore(studentData));
        return "redirect:/students";
    }

    @PostMapping("/{id}/update/grade")
    public String updateGrade(Model model,
                         @PathVariable("id") int id,
                         @ModelAttribute("studentData") @Valid StudentDataForWeb studentData,
                         BindingResult bindingResult) {
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("studentId", id);
        model.addAttribute("gradesValues", getAllRealGrades());
        if (bindingResult.hasErrors()) return "edit";
        studentDAO.update(id, createStudentFromGrade(studentData));
        return "redirect:/students";
    }

    @PostMapping("/{id}/delete")
    public String remove(@PathVariable("id") int id) {
        studentDAO.delete(id);
        return "redirect:/students";
    }

    private Student createStudentFromScore(StudentDataForWeb data) {
        List<String> paramsForNewStudent = List.of(
                data.getName(), String.valueOf(data.getScore()), data.getGradeType());
        return studentsCreator.createStudent(paramsForNewStudent, data.getId());
    }

    private Student createStudentFromGrade(StudentDataForWeb data) {
        int score = realGradeParser.getScoreFromGrade(data.getRealGrade(), data.getGradeType());
        List<String> paramsForNewStudent = List.of(
                data.getName(), String.valueOf(score), data.getGradeType());
        return studentsCreator.createStudent(paramsForNewStudent, data.getId());
    }

    private StudentDataForWeb getDataFromStudent(int id) {
        Student student = studentDAO.showOne(id);
        return new StudentDataForWeb(
                id,
                student.getName(),
                student.getGrade().getValue(),
                student.getGrade().getGrade().getSimpleName().substring(5).toUpperCase());
    }

    private List<List<String>> getAllRealGrades() {
        List<String> letter = GradeValuesLetter.getAllTitles();
        List<String> percentage = GradeValuesPercentage.getAllTitles();
        List<String> gpa = GradeValuesGPA.getAllTitles();
        List<String> ukraine = GradeValuesUkraine.getAllTitles();
        return List.of(letter, percentage, gpa, ukraine);
    }

}
