package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

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

    @Override
    public List<String> getAllNamesStartWithA() {
        String firstSymbol = "A";
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith(firstSymbol))
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public double getAvgAgeWithStream() {
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(-1);
    }

    @Override
    public void printStudents() {
        List<Student> students = studentRepository.findAll();

        printStudent(students.get(0));
        printStudent(students.get(1));

        Thread thread1 = new Thread(() -> {
            printStudent(students.get(2));
            printStudent(students.get(3));
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            printStudent(students.get(4));
            printStudent(students.get(5));
        });
        thread2.start();

    }

    @Override
    public void printStudentsSync() {
        List<Student> students = studentRepository.findAll();

        printStudentSync(students.get(0));
        printStudentSync(students.get(1));

        Thread thread1 = new Thread(() -> {
            printStudentSync(students.get(2));
            printStudentSync(students.get(3));
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            printStudentSync(students.get(4));
            printStudentSync(students.get(5));
        });
        thread2.start();

    }

    private void printStudent(Student student) {
        logger.info("Thread: {}, Student: {}", Thread.currentThread(), student);
    }

    private synchronized void printStudentSync(Student student) {
        printStudent(student);
    }
}
