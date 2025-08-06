package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;

import java.util.List;

@RestController
public class FilmController {

    FilmStorage storage;

    FilmService service;

    @Autowired
    public FilmController(FilmService service, InMemoryFilmStorage storage) {
        this.service = service;
        this.storage = storage;
    }

    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film) {
        return storage.createFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        return storage.updateFilm(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void likeFilm(@PathVariable Long id, @PathVariable Long userId) {
        service.likeFilm(id, userId);
    }

    @DeleteMapping("/films")
    public void deleteFilm(@RequestBody Film film) {
        storage.deleteFilm(film);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLikeFilm(@PathVariable Long id, @PathVariable Long userId) {
        service.deleteLikeFilm(id, userId);
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return storage.getFilmList();
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam Integer count) {
        return service.getFilmList(count);
    }
}
