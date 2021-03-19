package org.geekhub.studentsregistry.web.controllers;

import org.geekhub.studentsregistry.enums.GradeType;
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
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("studentsCount", studentDAO.getStudentsCount());
        return "show";
    }

    @GetMapping("/new")
    public String newStudents(Model model) {
        int id = studentDAO.getMaxId() + 1;
        model.addAttribute("studentData", new StudentDataForWeb());
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("studentId", id);
        return "new";
    }

    @PostMapping("/create")
    public String create(Model model,
                         @RequestParam("currentId") int id,
                         @ModelAttribute("studentData") @Valid StudentDataForWeb studentData,
                         BindingResult bindingResult) {
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("studentId", id);
        studentData.setId(id);
        if (bindingResult.hasErrors()) return "new";
        model.addAttribute("studentId", id + 1);
        studentDAO.save(createStudentFromWeb(studentData));
        return "new";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("studentData", getDataFromStudent(id));
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("studentId", id);
        return "edit";
    }

    @PostMapping("/{id}/update")
    public String update(Model model,
                         @PathVariable("id") int id,
                         @ModelAttribute("studentData") @Valid StudentDataForWeb studentData,
                         BindingResult bindingResult) {
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("studentId", id);
        if (bindingResult.hasErrors()) return "edit";
        studentDAO.update(id, createStudentFromWeb(studentData));
        return "redirect:/students";
    }

    @PostMapping("/{id}/delete")
    public String remove(@PathVariable("id") int id) {
        studentDAO.delete(id);
        return "redirect:/students";
    }

    private Student createStudentFromWeb(StudentDataForWeb data) {
        List<String> paramsForNewStudent = List.of(
                data.getName(), String.valueOf(data.getScore()), data.getGradeType());
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

}
