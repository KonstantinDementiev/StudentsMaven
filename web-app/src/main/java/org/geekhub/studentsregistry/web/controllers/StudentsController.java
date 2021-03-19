package org.geekhub.studentsregistry.web.controllers;

import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.students.Student;
import org.geekhub.studentsregistry.students.StudentsCreator;
import org.geekhub.studentsregistry.students.StudentsDataForWeb;
import org.geekhub.studentsregistry.web.dao.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/students")
public class StudentsController {

    private final static int INPUT_PARAM_NUMBER = 3;
    private final StudentDAO studentDAO;
    private final StudentsCreator studentsCreator;

    @Autowired
    public StudentsController(
            StudentDAO studentDAO,
            StudentsCreator studentsCreator) {
        this.studentDAO = studentDAO;
        this.studentsCreator = studentsCreator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("students", studentDAO.show());
        model.addAttribute("gradeTypes", GradeType.values());
        model.addAttribute("studentsCount", studentDAO.getStudentsCount());
        return "show";
    }

    @GetMapping("/new")
    public String newStudents(Model model) {
        model.addAttribute("studentData", getEmptyList());
        model.addAttribute("gradeTypes", GradeType.values());
        model.addAttribute("studentId", studentDAO.getStudentsCount() + 1);
        return "new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("studentData") StudentsDataForWeb studentData, Model model,
                         @RequestParam("currentId") int currentId) {
        Student student = studentsCreator.createStudent(studentData.getInputData(), currentId);
        studentDAO.save(student);
        model.addAttribute("gradeTypes", GradeType.values());
        model.addAttribute("studentId", currentId + 1);
        return "new";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("studentData", existParamsList(id));
        model.addAttribute("gradeTypes", GradeType.values());
        model.addAttribute("studentId", id);
        return "edit";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") int id, Model model,
                         @ModelAttribute("studentData") StudentsDataForWeb studentData) {
        Student student = studentsCreator.createStudent(studentData.getInputData(), id);
        studentDAO.update(id, student);
        return "redirect:/students";
    }

    @PostMapping("/{id}/delete")
    public String remove(@PathVariable("id") int id) {
        studentDAO.delete(id);
        return "redirect:/students";
    }

    private StudentsDataForWeb getEmptyList() {
        StudentsDataForWeb studentData = new StudentsDataForWeb(new ArrayList<>());
        for (int i = 0; i < INPUT_PARAM_NUMBER; i++) studentData.addData("");
        return studentData;
    }

    private StudentsDataForWeb existParamsList(int id) {
        Student student = studentDAO.showOne(id);
        StudentsDataForWeb studentData = new StudentsDataForWeb(new ArrayList<>());
        studentData.addData(student.getName());
        studentData.addData(String.valueOf(student.getGrade().getValue()));
        studentData.addData(student.getGrade().getGrade().getSimpleName().substring(5).toUpperCase());
        return studentData;
    }


}
