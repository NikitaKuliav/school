package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() throws Exception {
        return "http://localhost:" + port + "/students";
    }

    @Test
    void create_success() throws Exception {
        // Подготовка входных данных
        Student studentForCreate = new Student("Ivan Krylov", 20);

        // Подготовка ожидаемого результата
        Student expectedStudent = new Student("Ivan Krylov", 20);

        // Начало теста
        Student actualStudent = this.restTemplate.postForObject(getBaseUrl(), studentForCreate, Student.class);
        expectedStudent.setId(actualStudent.getId());
        assertNotNull(actualStudent);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void get_success() throws Exception {
        // Подготовка входных данных
        Student studentForCreate = new Student("Ivan Krylov", 20);

        // Подготовка ожидаемого результата
        Student postedStudent = this.restTemplate.postForObject(getBaseUrl(), studentForCreate, Student.class);
        long id = postedStudent.getId();

        // Начало теста
        Student actualStudent = this.restTemplate.getForObject(getBaseUrl() + "/" + id, Student.class);
        assertNotNull(actualStudent);
        assertEquals(postedStudent, actualStudent);
    }

    @Test
    void update_success() throws Exception {
        // Подготовка входных данных
        Student studentForCreate = new Student("Ivan Krylov", 20);

        // Подготовка ожидаемого результата
        Student postedStudent = this.restTemplate.postForObject(getBaseUrl(), studentForCreate, Student.class);
        long id = postedStudent.getId();

        Student studentForUpdate = new Student("Ivan Krylov", 21);
        studentForUpdate.setId(id);

        // Начало теста
        this.restTemplate.put(getBaseUrl() + "/" + id, studentForUpdate);
        Student actualStudent = this.restTemplate.getForObject(getBaseUrl() + "/" + id, Student.class);
        assertNotNull(actualStudent);
        assertEquals(studentForUpdate, actualStudent);
    }

    @Test
    void delete_success() throws Exception {
        // Подготовка входных данных
        Student studentForCreate = new Student("Ivan Krylov", 20);

        // Подготовка ожидаемого результата
        Student postedStudent = this.restTemplate.postForObject(getBaseUrl(), studentForCreate, Student.class);
        long id = postedStudent.getId();

        // Начало теста
        Student studentForDelete = this.restTemplate.getForObject(getBaseUrl() + "/" + id, Student.class);
        assertNotNull(studentForDelete);
        assertEquals(postedStudent, studentForDelete);
        this.restTemplate.delete(getBaseUrl() + "/" + id);
        Student studentAfterDelete = this.restTemplate.getForObject(getBaseUrl() + "/" + id, Student.class);
        assertNull(studentAfterDelete.getId());

    }

    @Test
    void getStudentsByAge_success() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students" + "/age=" + 20, String.class))
                .isNotNull();
    }

    @Test
    void getStudentsByAgeRange_success() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students" + "/minAge=" + 20 + "&maxAge=" + 30, String.class))
                .isNotNull();
    }

    @Test
    void getStudentFaculty_success() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students" + "/1/faculties", String.class))
                .isNotNull();
    }
}