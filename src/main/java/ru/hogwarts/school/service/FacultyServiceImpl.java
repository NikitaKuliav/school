package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService{

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Faculty getFaculty(Long id) {
        logger.info("Was invoked method getFaculty");
        return facultyRepository.findById(id).get();
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method addFaculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        logger.info("Was invoked method deleteFaculty");
        facultyRepository.deleteById(id);

    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        logger.info("Was invoked method updateFaculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public List<Faculty> getFacultiesByColor(String color) {
        logger.info("Was invoked method getFacultiesByColor");
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> getFacultiesByNameIgnoreCase(String name) {
        logger.info("Was invoked method getFacultiesByNameIgnoreCase");
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public List<Faculty> getFacultiesByColorIgnoreCase(String color) {
        logger.info("Was invoked method getFacultiesByColorIgnoreCase");
        return facultyRepository.findByColorIgnoreCase(color);
    }

    @Override
    public Collection<Student> getFacultyStudents(Long facultyId) {
        logger.info("Was invoked method getFacultyStudents");
        return facultyRepository.findStudentsById(facultyId);
    }
}
