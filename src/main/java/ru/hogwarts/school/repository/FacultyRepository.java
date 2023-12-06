package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findByColor(String color);
    List<Faculty> findByNameIgnoreCase(String name);
    List<Faculty> findByColorIgnoreCase(String color);
    Collection<Student> findStudentsById(Long facultyId);
}
