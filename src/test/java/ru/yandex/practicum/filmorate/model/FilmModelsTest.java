package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FilmModelsTest {

    InMemoryFilmStorage storage;

    @BeforeEach
    public void setUp() {
        storage = new InMemoryFilmStorage();
    }

    @Test
    void testValidationNameIsNull() {
        Film film = new Film();
        film.setName(null);
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120L);

        assertThrows(ValidationException.class, () -> storage.validationFilms(film));
    }

    @Test
    void testValidationNameIsEmpty() {
        Film film = new Film();
        film.setName("");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120L);

        assertThrows(ValidationException.class, () -> storage.validationFilms(film));
    }

    @Test
    void testValidationDescriptionTooLong() {
        Film film = new Film();
        film.setName("Test");
        film.setDescription("A".repeat(200));
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120L);
        film.setDescription("A".repeat(201));

        assertThrows(ValidationException.class, () -> storage.validationFilms(film));
    }

    @Test
    void testValidationReleaseDateBefore1895() {
        Film film = new Film();
        film.setName("Test");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(1800, 1, 1));
        film.setDuration(100L);

        assertThrows(ValidationException.class, () -> storage.validationFilms(film));
    }

    @Test
    void testValidationDurationNegative() {
        Film film = new Film();
        film.setName("Test");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(-10L);

        assertThrows(ValidationException.class, () -> storage.validationFilms(film));
    }

    @Test
    void testValidationDurationNull() {
        Film film = new Film();
        film.setName("Test");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(null);

        assertThrows(ValidationException.class, () -> storage.validationFilms(film));
    }

    @Test
    void testValidationCorrectData() {
        Film film = new Film();
        film.setName("Valid Name");
        film.setDescription("Valid description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(100L);

        assertDoesNotThrow(() -> storage.validationFilms(film));
    }
}
