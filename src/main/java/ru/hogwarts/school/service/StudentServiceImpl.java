package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student getStudent(Long id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);

    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Student updateStudent(Long id, Student student) {

        return studentRepository.save(student);
    }

    @Override
    public List<Student> getStudentsByAge(Long age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public List<Student> getStudentsByAgeBetween(Long minAge, Long maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }


    @Override
    public Faculty getStudentFaculty(Long studentId) {
        return studentRepository.findFacultyById(studentId);
    }
}
