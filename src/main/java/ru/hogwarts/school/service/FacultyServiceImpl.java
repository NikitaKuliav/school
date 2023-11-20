package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService{
    private Map<Long, Faculty> facultyMap = new HashMap<>();
    private Long counter = 0L;
    @Override
    public Faculty getFaculty(Long id) {
        return facultyMap.get(id);
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        Long id = counter++;
        facultyMap.put(id, faculty);
        return faculty;
    }

    @Override
    public void deleteFaculty(Long id) {
        facultyMap.remove(id);

    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        Faculty oldFaculty = facultyMap.get(id);
        oldFaculty.setName(faculty.getName());
        oldFaculty.setColor(faculty.getColor());
        return oldFaculty;
    }

    @Override
    public List<Faculty> getFacultiesByColor(String color) {
        return facultyMap.values()
                .stream()
                .filter(faculty -> faculty.getColor() == color)
                .toList();
    }
}
