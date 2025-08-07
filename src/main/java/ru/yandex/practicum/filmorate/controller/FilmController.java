package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
public class FilmController {


    FilmService service;

    @Autowired
    public FilmController(FilmService service) {
        this.service = service;
    }

    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film) {
        return service.createFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        return service.updateFilm(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void likeFilm(@PathVariable Long id, @PathVariable Long userId) {
        service.likeFilm(id, userId);
    }

    @DeleteMapping("/films")
    public void deleteFilm(@RequestBody Film film) {
        service.deleteFilm(film);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLikeFilm(@PathVariable Long id, @PathVariable Long userId) {
        service.deleteLikeFilm(id, userId);
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return service.getFilmList();
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam Integer count) {
        return service.getFilmList(count);
    }
}
