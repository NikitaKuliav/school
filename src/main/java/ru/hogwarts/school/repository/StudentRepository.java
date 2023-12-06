package ru.hogwarts.school.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(Long age);
    List<Student> findByAgeBetween(Long minAge, Long maxAge);
    Faculty findFacultyById(Long studentId);
}
