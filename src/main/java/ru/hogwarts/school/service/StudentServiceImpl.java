package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student getStudent(Long id) {
        logger.info("Was invoked method getStudent");
        return studentRepository.findById(id).get();
    }

    @Override
    public Student addStudent(Student student) {
        logger.info("Was invoked method addStudent");
        return studentRepository.save(student);

    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Was invoked method deleteStudent");
        studentRepository.deleteById(id);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        logger.info("Was invoked method updateStudent");
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getStudentsByAge(Long age) {
        logger.info("Was invoked method getStudentsByAge");
        return studentRepository.findByAge(age);
    }

    @Override
    public List<Student> getStudentsByAgeBetween(Long minAge, Long maxAge) {
        logger.info("Was invoked method getStudentsByAgeBetween");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }


    @Override
    public Faculty getStudentFaculty(Long studentId) {
        logger.info("Was invoked method getStudentFaculty");
        return studentRepository.findFacultyById(studentId);
    }

    @Override
    public int getCount() {
        logger.info("Was invoked method getCount");
        return studentRepository.getCount();
    }

    @Override
    public double getAverageAge() {
        logger.info("Was invoked method getAverageAge");
        return studentRepository.getAvgAge();
    }

    @Override
    public List<Student> getLastFive() {
        logger.info("Was invoked method getLastFive");
        return studentRepository.getLastFiveOrderByIdDesc();
    }
}
