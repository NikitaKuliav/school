package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService{
    private Map<Long, Student> studentMap = new HashMap<>();
    private Long counter = 0L;
    @Override
    public Student getStudent(Long id) {
        return studentMap.get(id);
    }

    @Override
    public Student addStudent(Student student) {
        Long id = counter++;
        Student newStudent = new Student(id, student.getName(), student.getAge());
        studentMap.put(id, newStudent);
        student.setId(id);
        return newStudent;
    }

    @Override
    public void deleteStudent(Long id) {
        studentMap.remove(id);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student oldStudent = studentMap.get(id);
        oldStudent.setName(student.getName());
        oldStudent.setAge(student.getAge());
        return oldStudent;
    }

    @Override
    public List<Student> getStudentsByAge(Long age) {
        return studentMap.values()
                .stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }

}
