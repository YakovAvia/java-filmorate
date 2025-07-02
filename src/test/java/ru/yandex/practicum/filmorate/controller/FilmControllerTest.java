package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilmController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void filmCreateTest() throws Exception {

        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(2000,1,1));
        film.setDuration(100L);

        mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Film"));
    }

    @Test
    public void filmCreateNameIsEmptyTest() throws Exception {

        Film film = new Film();
        film.setName("");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(2000,1,1));
        film.setDuration(100L);

        mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void filmDescriptionTest() throws Exception {

        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("""
                                Test Descriptionfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
                                Descriptionfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
                                Descriptionfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
                                """);
        film.setReleaseDate(LocalDate.of(2000,1,1));
        film.setDuration(100L);

        mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void filmDateTest() throws Exception {

        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(1800,1,1));
        film.setDuration(100L);

        mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void filmDurationTest() throws Exception {

        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(2000,1,1));
        film.setDuration(0L);

        mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void filmUpdateTest() throws Exception {

        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(2000,1,1));
        film.setDuration(100L);

        String responseContent = mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Film createdFilm = objectMapper.readValue(responseContent, Film.class);

        createdFilm.setName("Updated Test Film");
        createdFilm.setDescription("Updated Test Description");
        createdFilm.setReleaseDate(LocalDate.of(2010,5,15));

        mvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Test Film"))
                .andExpect(jsonPath("$.description").value("Updated Test Description"))
                .andExpect(jsonPath("$.releaseDate").value("2010-05-15"));
    }

    @Test
    public void notFoundFilmTest() throws Exception {

        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(2000,1,1));
        film.setDuration(100L);

        String responseContent = mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Film createdFilm = objectMapper.readValue(responseContent, Film.class);

        createdFilm.setId(2L);
        createdFilm.setName("Updated Test Film");
        createdFilm.setDescription("Updated Test Description");
        createdFilm.setReleaseDate(LocalDate.of(2010,5,15));

        mvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdFilm)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void notFoundIdFilmTest() throws Exception {

        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(2000,1,1));
        film.setDuration(100L);

        String responseContent = mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Film createdFilm = objectMapper.readValue(responseContent, Film.class);

        createdFilm.setId(null);
        createdFilm.setName("Updated Test Film");
        createdFilm.setDescription("Updated Test Description");
        createdFilm.setReleaseDate(LocalDate.of(2010,5,15));

        mvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdFilm)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getFilmTest() throws Exception {

        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(2000,1,1));
        film.setDuration(100L);

        mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        mvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }
}
