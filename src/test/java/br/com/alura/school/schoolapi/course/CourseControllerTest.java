package br.com.alura.school.schoolapi.course;

import br.com.alura.school.schoolapi.enroll.NewEnrollRequest;
import br.com.alura.school.schoolapi.user.User;
import br.com.alura.school.schoolapi.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void should_retrieve_course_by_code() throws Exception {
        courseRepository.save(new Course("java-9", "Java Spring 9", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));

        mockMvc.perform(get("/courses/java-9")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is("java-9")))
                .andExpect(jsonPath("$.name", is("Java Spring 9")))
                .andExpect(jsonPath("$.shortDescription", is("Java and O...")));
    }

    @Test
    void should_retrieve_all_courses() throws Exception {
        courseRepository.save(new Course("spring-1", "Spring Basics", "Spring Core and Spring MVC."));
        courseRepository.save(new Course("spring-2", "Spring Boot", "Spring Boot"));

        mockMvc.perform(get("/courses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$[0].code", is("java-1")))
                .andExpect(jsonPath("$[0].name", is("Java OO")))
                .andExpect(jsonPath("$[0].shortDescription", is("Java and O...")))
                .andExpect(jsonPath("$[1].code", is("java-2")))
                .andExpect(jsonPath("$[1].name", is("Java Collections")))
                .andExpect(jsonPath("$[1].shortDescription", is("Java Colle...")))
                .andExpect(jsonPath("$[2].code", is("java-3")))
                .andExpect(jsonPath("$[2].name", is("Java 8")))
                .andExpect(jsonPath("$[2].shortDescription", is("Java 8")))
                .andExpect(jsonPath("$[3].code", is("spring-1")))
                .andExpect(jsonPath("$[3].name", is("Spring Basics")))
                .andExpect(jsonPath("$[3].shortDescription", is("Spring Cor...")))
                .andExpect(jsonPath("$[4].code", is("spring-2")))
                .andExpect(jsonPath("$[4].name", is("Spring Boot")))
                .andExpect(jsonPath("$[4].shortDescription", is("Spring Boot")));
    }

    @Test
    void should_add_new_course() throws Exception {
        NewCourseRequest newCourseRequest = new NewCourseRequest("java-20", "Java Novidades Versão 20", "Java Collections: Lists, Sets, Maps and more.");

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newCourseRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/courses/java-20"));
    }

    @Test
    void should_add_new_enroll() throws Exception {
        courseRepository.save(new Course("java-31", "Java 31", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
        userRepository.save(new User("john", "john@email.com"));
        NewEnrollRequest newEnrollRequest = new NewEnrollRequest("", "john");

        mockMvc.perform(post("/courses/java-31/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newEnrollRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/courses/java-31/enroll"));
    }

}
