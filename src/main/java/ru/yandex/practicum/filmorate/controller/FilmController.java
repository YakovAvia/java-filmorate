package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    private long id = 0;

    private final List<Film> filmList = new ArrayList<>();

    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film) {

        Film newFilm = new Film();

        newFilm.setId(++id);

        if (film.getName() != null && !film.getName().isEmpty()) {
            newFilm.setName(film.getName());
        } else {
            log.error("Название не может быть пустым");
            throw new ValidationException("Название не может быть пустым");
        }

        if (film.getDescription().length() >= 200) {
            log.error("Длинна не может быть больше 200 символов");
            throw new ValidationException("Длинна не может быть больше 200 символов");
        } else {
            newFilm.setDescription(film.getDescription());
        }

        if (!film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            newFilm.setReleaseDate(film.getReleaseDate());
        } else {
            log.error("Дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }

        if (film.getDuration() > 0) {
            newFilm.setDuration(film.getDuration());
        } else {
            log.error("Продолжительность фильма должна быть больше нуля");
            throw new ValidationException("Продолжительность фильма должна быть больше нуля");
        }

        filmList.add(newFilm);

        return newFilm;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {

        if (film.getId() == null) {
            throw new ValidationException("ID фильма не может быть null");
        }

        Optional<Film> optionalFilm = filmList.stream()
                .filter(f -> f.getId().equals(film.getId()))
                .findFirst();

        if (optionalFilm.isEmpty()) {
            log.error("Фильм не найден!");
            throw new ValidationException("Фильм не найден!");
        }

        Film existingFilm = optionalFilm.get();
        existingFilm.setName(film.getName());
        existingFilm.setDescription(film.getDescription());
        existingFilm.setReleaseDate(film.getReleaseDate());
        existingFilm.setDuration(film.getDuration());

        return existingFilm;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return filmList;
    }

}
