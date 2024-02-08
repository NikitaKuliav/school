package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student foundStudent = studentService.updateStudent(id, student);
        if (foundStudent == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = "age")
    public Collection<Student> getStudentsByAge(@RequestParam Long age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping(params = {"minAge", "maxAge"})
    public Collection<Student> getStudentsByAgeRange(@RequestParam Long minAge, @RequestParam Long maxAge) {
        return studentService.getStudentsByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/{studentId}/faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long studentId) {
        Faculty faculty = studentService.getStudentFaculty(studentId);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/count")
    public int getCount() {
        return studentService.getCount();
    }

    @GetMapping("/avgAge")
    public double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/lastFive")
    public List<Student> getLastFive() {
        return studentService.getLastFive();
    }

    @GetMapping("/names-start-with-a")
    public List<String> getAllNamesStartWithA() {
        return studentService.getAllNamesStartWithA();
    }

    @GetMapping("/avg-age-with-stream")
    public double getAvgAgeWithStream() {
        return studentService.getAvgAgeWithStream();
    }

    @GetMapping("/sum-parallel")
    public void getSumParallel() {
        int sum = Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
    }

    @GetMapping("/print-parallel")
    public void printParallel() {
        studentService.printStudents();
    }

    @GetMapping("/print-synchronized")
    public void printSynchronized() {
        studentService.printStudentsSync();
    }
}
