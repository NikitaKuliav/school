package ru.hogwarts.school.WebMvcTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerTest {

    private static final String MOCK_STUDENT_NAME = "Гарри Поттер";
    private static final String MOCK_STUDENT_NEW_NAME = "Вася Пупкин";
    private static final int MOCK_STUDENT_AGE = 21;
    private static final long MOCK_STUDENT_ID = 1L;
    private static final Student MOCK_STUDENT = new Student("Гарри Поттер", 21);


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createStudentTest() throws Exception {

        Student studentForCreate = new Student(MOCK_STUDENT_NAME, MOCK_STUDENT_AGE);
        studentForCreate.setId(MOCK_STUDENT_ID);
        String request = mapper.writeValueAsString(studentForCreate);

        //Подготовка ожидаемого результата

        Student studentAfterCreate = new Student(MOCK_STUDENT_NAME, MOCK_STUDENT_AGE);
        studentAfterCreate.setId(MOCK_STUDENT_ID);

        when(studentService.addStudent(MOCK_STUDENT)).thenReturn(studentAfterCreate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/students") //send
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getStudent() throws Exception {

        Student studentForCreate = new Student(MOCK_STUDENT_NAME, MOCK_STUDENT_AGE);
        studentForCreate.setId(MOCK_STUDENT_ID);

        when(studentService.getStudent(MOCK_STUDENT_ID)).thenReturn(studentForCreate);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/" + MOCK_STUDENT_ID)
                        .param("id", String.valueOf(MOCK_STUDENT_ID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_STUDENT_ID))
                .andExpect(jsonPath("$.name").value(studentForCreate.getName()))
                .andExpect(jsonPath("$.age").value(studentForCreate.getAge()))
                .andReturn();
    }

    @Test
    public void updateStudent() throws Exception {

        //Подготовка входных данных
        Student studentForUpdate = new Student(MOCK_STUDENT_NAME, MOCK_STUDENT_AGE);
        studentForUpdate.setId(MOCK_STUDENT_ID);


        String request = mapper.writeValueAsString(studentForUpdate);

        //Подготовка ожидаемого результата
        when(studentService.updateStudent(MOCK_STUDENT_ID, MOCK_STUDENT)).thenReturn(studentForUpdate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/students") //send
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deleteStudent() throws Exception {
        when(studentRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/students/" + MOCK_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
