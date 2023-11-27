package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;
import java.util.Optional;

public interface FacultyService {
//    create read update delete

    Faculty getFaculty(Long id);
    Faculty addFaculty(Faculty faculty);
    void deleteFaculty(Long id);
    Faculty updateFaculty(Long id, Faculty faculty);
    List<Faculty> getFacultiesByColor(String color);

}
