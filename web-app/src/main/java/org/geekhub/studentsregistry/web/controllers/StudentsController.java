package org.geekhub.studentsregistry.web.controllers;

import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.students.Student;
import org.geekhub.studentsregistry.students.StudentDataForWeb;
import org.geekhub.studentsregistry.web.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/students")
public class StudentsController {

    private static final GradeType[] GRADE_TYPES = GradeType.values();
    private final StudentService studentService;

    @Autowired
    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String invite() {
        return "hello";
    }

    @GetMapping("all")
    public String index(Model model) {
        Map<GradeType, List<Student>> students = studentService.showAll();
        model.addAttribute("students", students);
        model.addAttribute("analytics", studentService.showAnalytics(students));
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("studentsCount", studentService.getStudentsCount());
        return "/students/studentsShow";
    }

    @GetMapping("/new/score")
    public String newStudentsByScore(Model model) {
        model.addAttribute("studentData", new StudentDataForWeb());
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("message");
        return "/students/new/newStudentByScore";
    }

    @PostMapping("/create/score")
    public String createStudentsByScore(Model model,
                                        @ModelAttribute("studentData") @Valid StudentDataForWeb studentData,
                                        BindingResult bindingResult) {
        model.addAttribute("gradeTypes", GRADE_TYPES);
        if (bindingResult.hasErrors()) return "/students/new/newStudentByScore";
        studentService.saveStudentWithScore(studentData);
        model.addAttribute("message",
                "The student with name: '" + studentData.getName() + "' was successfully created!");
        return "/students/new/newStudentByScore";
    }

    @GetMapping("/new/grade")
    public String newStudentsByGrade(Model model) {
        model.addAttribute("studentData", new StudentDataForWeb());
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("gradesValues", StudentService.getAllRealGrades());
        model.addAttribute("message");
        return "/students/new/newStudentByGrade";
    }

    @PostMapping("/create/grade")
    public String createStudentsByGrade(Model model,
                                        @ModelAttribute("studentData") @Valid StudentDataForWeb studentData,
                                        BindingResult bindingResult) {
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("gradesValues", StudentService.getAllRealGrades());
        if (bindingResult.hasErrors()) return "/students/new/newStudentByGrade";
        studentService.saveStudentWithGrade(studentData);
        model.addAttribute("message",
                "The student with name: '" + studentData.getName() + "' was successfully created!");
        return "/students/new/newStudentByGrade";
    }

    @GetMapping("/new/random")
    public String newStudentsRandom(Model model) {
        model.addAttribute("message");
        return "/students/new/newStudentByRandom";
    }

    @PostMapping("/create/random")
    public String generateStudentsRandom(Model model, @RequestParam("randomNumber") int number) {
        studentService.generateNewStudents(number);
        model.addAttribute("message", number + " students were successfully generated!");
        return "/students/new/newStudentByRandom";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("studentData", studentService.getDataFromStudent(id));
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("studentId", id);
        model.addAttribute("gradesValues", StudentService.getAllRealGrades());
        return "students/studentEdit";
    }

    @PostMapping("/{id}/update/score")
    public String updateScore(Model model,
                              @PathVariable("id") int id,
                              @ModelAttribute("studentData") @Valid StudentDataForWeb studentData,
                              BindingResult bindingResult) {
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("gradesValues", StudentService.getAllRealGrades());
        if (bindingResult.hasErrors()) return "students/studentEdit";
        studentService.update(id, studentData, "byScore");
        return "redirect:/students/all";
    }

    @PostMapping("/{id}/update/grade")
    public String updateGrade(Model model,
                              @PathVariable("id") int id,
                              @ModelAttribute("studentData") @Valid StudentDataForWeb studentData,
                              BindingResult bindingResult) {
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("gradesValues", StudentService.getAllRealGrades());
        if (bindingResult.hasErrors()) return "students/studentEdit";
        studentService.update(id, studentData, "byGrade");
        return "redirect:/students/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/delete")
    public String remove(@PathVariable("id") int id) {
        studentService.delete(id);
        return "redirect:/students/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/deleteAll")
    public String removeAll() {
        studentService.deleteAll();
        return "redirect:/students/all";
    }


}
