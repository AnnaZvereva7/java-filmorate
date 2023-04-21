package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getFilmTest() throws Exception {
        mockMvc.perform(get("/films")).andExpect(status().isOk());
    }

    @Test
    void AddFilmTests() throws Exception {
        String jsonFilm = "{\n" +
                "  \"name\": \"nisi eiusmod\",\n" +
                "  \"description\": \"descriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptio\",\n" +
                "  \"releaseDate\": \"1895-12-28\",\n" +
                "  \"duration\": 100\n" +
                "}";
        mockMvc.perform(post("/films").
                contentType(MediaType.APPLICATION_JSON).
                content(jsonFilm)).andExpect(status().isOk());

        jsonFilm = "{\n" +
                "  \"name\": \" \",\n" +
                "  \"description\": \"adipisicing\",\n" +
                "  \"releaseDate\": \"1967-03-25\",\n" +
                "  \"duration\": 100\n" +
                "}";
        mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON).content(jsonFilm)).
                andExpect(status().is(400));
        jsonFilm = "{\n" +
                "  \"name\": \" \",\n" +
                "  \"description\": \"descriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescription\",\n" +
                "  \"releaseDate\": \"1967-03-25\",\n" +
                "  \"duration\": 100\n" +
                "}";
        mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON).content(jsonFilm)).
                andExpect(status().is(400));

        jsonFilm = "{\n" +
                "  \"name\": \"nisi eiusmod\",\n" +
                "  \"description\": \"adipisicing\",\n" +
                "  \"releaseDate\": \"1895-12-27\",\n" +
                "  \"duration\": 100\n" +
                "}";
        mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON).content(jsonFilm)).
                andExpect(status().is(400));

        jsonFilm = "{\n" +
                "  \"name\": \"nisi eiusmod\",\n" +
                "  \"description\": \"adipisicing\",\n" +
                "  \"releaseDate\": \"1895-12-29\",\n" +
                "  \"duration\": 0\n" +
                "}";
        mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON).content(jsonFilm)).
                andExpect(status().is(400));
    }

    @Test
    void getUsersTest() throws Exception {
        mockMvc.perform(get("/users")).andExpect(status().isOk());
    }

    @Test
    void addUserTests() throws Exception {
        String jsonUser = "{\n" +
                "  \"login\": \"login\",\n" +
                "  \"name\": \"name\",\n" +
                "  \"birthday\": \"1990-01-23\",\n" +
                "  \"email\": \"mail@mail.ru\"\n" +
                "}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).
                content(jsonUser)).andExpect(status().isOk());

        jsonUser = "{\n" +
                "  \"login\": \"lo gin\",\n" +
                "  \"name\": \"name\",\n" +
                "  \"birthday\": \"1990-01-23\",\n" +
                "  \"email\": \"mail@mail.ru\"\n" +
                "}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).
                content(jsonUser)).andExpect(status().is(400));

        jsonUser = "{\n" +
                "  \"login\": \"login\",\n" +
                "  \"birthday\": \"1990-01-23\",\n" +
                "  \"email\": \"mailru\"\n" +
                "}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).
                content(jsonUser)).andExpect(status().is(400));

        jsonUser = "{\n" +
                "  \"login\": \"lo gin\",\n" +
                "  \"birthday\": \"2024-01-23\",\n" +
                "  \"email\": \"mail@mail.ru\"\n" +
                "}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).
                content(jsonUser)).andExpect(status().is(400));
    }

}
