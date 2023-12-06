package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyService {
//    create read update delete

    Faculty getFaculty(Long id);
    Faculty addFaculty(Faculty faculty);
    void deleteFaculty(Long id);
    Faculty updateFaculty(Long id, Faculty faculty);
    List<Faculty> getFacultiesByColor(String color);
    List<Faculty> getFacultiesByNameIgnoreCase(String name);
    List<Faculty> getFacultiesByColorIgnoreCase(String color);
    public Collection<Student> getFacultyStudents(Long facultyId);

}
