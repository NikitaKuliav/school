package ru.hogwarts.school.controller;

import jdk.jshell.Snippet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id){
        Faculty faculty = facultyService.getFaculty(id);
        if(faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty){
        return facultyService.addFaculty(faculty);
    }

    @PutMapping("{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty faculty){
        Faculty foundFaculty = facultyService.updateFaculty(id, faculty);
        if(foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFaculty(@PathVariable Long id){
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = "color")
    public Collection<Faculty> getFacultiesByColor(@RequestParam String color){
        return facultyService.getFacultiesByColor(color);
    }

}
