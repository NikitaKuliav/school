package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculties")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}")
    public Faculty getStudent(@PathVariable Long id){
        return facultyService.getFaculty(id);
    }

    @PostMapping
    public Faculty createStudent(@RequestBody Faculty faculty){
        return facultyService.addFaculty(faculty);
    }

    @PutMapping("{id}")
    public Faculty updateStudent(@PathVariable Long id, @RequestBody Faculty faculty){
        return facultyService.updateFaculty(id, faculty);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id){
        facultyService.deleteFaculty(id);
    }

    @GetMapping(params = "color")
    public Collection<Faculty> getStudentsByAge(@RequestParam String color){
        return facultyService.getFacultiesByColor(color);
    }

}
