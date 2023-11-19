package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {

    Student getStudent(Long id);
    Student addStudent(Student student);
    void deleteStudent(Long id);
    Student updateStudent(Long id, Student student);
    List<Student> getStudentsByAge(Long age);
}
