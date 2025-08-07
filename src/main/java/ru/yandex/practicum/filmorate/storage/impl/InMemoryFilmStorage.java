package ru.yandex.practicum.filmorate.storage.impl;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private static final Logger log = LoggerFactory.getLogger(InMemoryFilmStorage.class);

    private long customIdCounter = 0;

    @Getter
    private final List<Film> filmList = new ArrayList<>();

    @Override
    public Film createFilm(Film film) {
        validationFilms(film);

        Film newFilm = new Film();

        newFilm.setId(++customIdCounter);
        newFilm.setName(film.getName());
        newFilm.setDescription(film.getDescription());
        newFilm.setReleaseDate(film.getReleaseDate());
        newFilm.setDuration(film.getDuration());
        filmList.add(newFilm);

        return newFilm;
    }

    @Override
    public Film updateFilm(Film film) {
        validationFilms(film);

        if (film.getId() == null) {
            throw new NotFoundException("ID фильма не найден!");
        }

        Optional<Film> optionalFilm = filmList.stream()
                .filter(f -> f.getId().equals(film.getId()))
                .findFirst();

        if (optionalFilm.isEmpty()) {
            log.error("Фильм не найден!");
            throw new NotFoundException("Фильм не найден!");
        }

        film.setName(film.getName());
        film.setDescription(film.getDescription());
        film.setReleaseDate(film.getReleaseDate());
        film.setDuration(film.getDuration());

        return film;
    }

    @Override
    public void deleteFilm(Film film) {
        filmList.remove(film);
    }

    public void validationFilms(Film film) {

        if (film.getName() == null || film.getName().isEmpty()) {
            log.error("Название не может быть пустым");
            throw new ValidationException("Название не может быть пустым");
        }

        if (film.getDescription() == null || film.getDescription().length() >= 200) {
            log.error("Длинна не может быть больше 200 символов");
            throw new ValidationException("Длинна не может быть больше 200 символов");
        }

        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.error("Дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }

        if (film.getDuration() == null || film.getDuration() < 0) {
            log.error("Продолжительность фильма должна быть больше нуля");
            throw new ValidationException("Продолжительность фильма должна быть больше нуля");
        }
    }
}
