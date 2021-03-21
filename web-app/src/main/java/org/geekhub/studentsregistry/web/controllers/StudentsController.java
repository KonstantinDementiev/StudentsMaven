package org.geekhub.studentsregistry.web.controllers;

import org.geekhub.studentsregistry.enums.GradeType;
import org.geekhub.studentsregistry.students.Student;
import org.geekhub.studentsregistry.students.StudentDataForWeb;
import org.geekhub.studentsregistry.web.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "show";
    }

    @GetMapping("/new/score")
    public String newStudentsByScore(Model model) {
        model.addAttribute("studentData", new StudentDataForWeb());
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("message");
        return "new/score";
    }

    @PostMapping("/create/score")
    public String createStudentsByScore(Model model,
                                        @ModelAttribute("studentData") @Valid StudentDataForWeb studentData,
                                        BindingResult bindingResult) {
        model.addAttribute("gradeTypes", GRADE_TYPES);
        if (bindingResult.hasErrors()) return "new/score";
        studentService.saveStudentWithScore(studentData);
        model.addAttribute("message",
                "The student with name: '" + studentData.getName() + "' was successfully created!");
        return "new/score";
    }

    @GetMapping("/new/grade")
    public String newStudentsByGrade(Model model) {
        model.addAttribute("studentData", new StudentDataForWeb());
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("gradesValues", StudentService.getAllRealGrades());
        model.addAttribute("message");
        return "new/grade";
    }

    @PostMapping("/create/grade")
    public String createStudentsByGrade(Model model,
                                        @ModelAttribute("studentData") @Valid StudentDataForWeb studentData,
                                        BindingResult bindingResult) {
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("gradesValues", StudentService.getAllRealGrades());
        if (bindingResult.hasErrors()) return "new/grade";
        studentService.saveStudentWithGrade(studentData);
        model.addAttribute("message",
                "The student with name: '" + studentData.getName() + "' was successfully created!");
        return "new/grade";
    }

    @GetMapping("/new/random")
    public String newStudentsRandom(Model model) {
        model.addAttribute("message");
        return "new/random";
    }

    @PostMapping("/create/random")
    public String generateStudentsRandom(Model model, @RequestParam("randomNumber") int number) {
        studentService.generateNewStudents(number);
        model.addAttribute("message", number + " students were successfully generated!");
        return "new/random";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("studentData", studentService.getDataFromStudent(id));
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("studentId", id);
        model.addAttribute("gradesValues", StudentService.getAllRealGrades());
        model.addAttribute("message");
        return "edit";
    }

    @PostMapping("/{id}/update/score")
    public String updateScore(Model model,
                              @PathVariable("id") int id,
                              @ModelAttribute("studentData") @Valid StudentDataForWeb studentData,
                              BindingResult bindingResult) {
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("gradesValues", StudentService.getAllRealGrades());
        if (bindingResult.hasErrors()) return "edit";
        studentService.update(id, studentData, "byScore");
        model.addAttribute("message", "Student with ID= " + id + " successfully updated!");
        return "redirect:/students/all";
    }

    @PostMapping("/{id}/update/grade")
    public String updateGrade(Model model,
                              @PathVariable("id") int id,
                              @ModelAttribute("studentData") @Valid StudentDataForWeb studentData,
                              BindingResult bindingResult) {
        model.addAttribute("gradeTypes", GRADE_TYPES);
        model.addAttribute("gradesValues", StudentService.getAllRealGrades());
        if (bindingResult.hasErrors()) return "edit";
        studentService.update(id, studentData, "byGrade");
        model.addAttribute("message", "Student with ID= " + id + " successfully updated!");
        return "redirect:/students/all";
    }

    @PostMapping("/{id}/delete")
    public String remove(@PathVariable("id") int id) {
        studentService.delete(id);
        return "redirect:/students/all";
    }

}
