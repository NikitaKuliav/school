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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerTest {


    private static final Faculty MOCK_FACULTY = new Faculty("Gryffindor", "red");
    static final String MOCK_FACULTY_NAME = "Gryffindor";
    static final String MOCK_FACULTY_COLOR = "red";
    static final long MOCK_FACULTY_ID = 1L;
    static final String MOCK_FACULTY_NEW_NAME = "white";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private FacultyService facultyService;
    @InjectMocks
    private FacultyController facultyController;

    private ObjectMapper mapper = new ObjectMapper();


    @Test
    public void createFaculty() throws Exception {

        Faculty facultyForCreate = new Faculty(MOCK_FACULTY_NAME, MOCK_FACULTY_COLOR);

        String request = mapper.writeValueAsString(facultyForCreate);

        //Подготовка ожидаемого результата
        Faculty facultyAfterCreate = new Faculty(MOCK_FACULTY_NAME, MOCK_FACULTY_COLOR);
        facultyAfterCreate.setId(MOCK_FACULTY_ID);

        when(facultyService.addFaculty(MOCK_FACULTY)).thenReturn(facultyAfterCreate);



        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculties") //send
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(MOCK_FACULTY_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(facultyForCreate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(facultyForCreate.getColor()))
                .andReturn();
    }


    @Test
    void get() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Gryffindor";
        String color = "red";
        long id = 1L;
        Faculty facultyForCreate = new Faculty(name, color);
        facultyForCreate.setId(id);

        when(facultyService.getFaculty(id)).thenReturn(facultyForCreate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/{id}", MOCK_FACULTY_ID) //send
                        )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(facultyForCreate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(facultyForCreate.getColor()))
                .andReturn();
    }

    @Test
    public void deleteFaculty() throws Exception {
        when(facultyRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculties/" + MOCK_FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    @Test
    public void updateFaculty() throws Exception {
        Faculty facultyForUpdate = new Faculty(MOCK_FACULTY_NEW_NAME, MOCK_FACULTY_COLOR);
        facultyForUpdate.setId(MOCK_FACULTY_ID);

        String request = mapper.writeValueAsString(facultyForUpdate);

        when(facultyService.updateFaculty(MOCK_FACULTY_ID, facultyForUpdate)).thenReturn(facultyForUpdate);
        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(MOCK_FACULTY);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculties/" + MOCK_FACULTY_ID)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(MOCK_FACULTY_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(MOCK_FACULTY_NEW_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(MOCK_FACULTY_COLOR))
                .andReturn();
    }
}
