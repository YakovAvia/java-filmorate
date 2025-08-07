package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;

@Service
public class FilmService {


    private final FilmStorage filmStorage;

    private final UserStorage userStorage;

    @Autowired
    public FilmService(UserStorage userStorage, FilmStorage storage) {
        this.userStorage = userStorage;
        this.filmStorage = storage;
    }

    public void likeFilm(Long filmId, Long userId) {
        Film film = filmStorage.getFilmList()
                .stream()
                .filter(f -> f.getId().equals(filmId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм не найден!"));

        User user = userStorage.getUsers()
                .stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден!"));
        if (film.getUsers().contains(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь уже поставил лайк!");
        }
        long currentLikes = film.getLike() != null ? film.getLike() : 0L;
        film.setLike(currentLikes + 1);
        film.getUsers().add(user);
    }

    public void deleteLikeFilm(Long filmId, Long userId) {
        Film film = filmStorage.getFilmList().stream()
                .filter(film1 -> film1.getId().equals(filmId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Фильм не найден!"));

        User user = userStorage.getUsers().stream()
                .filter(user1 -> user1.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));

        if (!film.getUsers().contains(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь не лайкал фильм!");
        }
        long currentLikes = film.getLike() != null ? film.getLike() : 0L;
        film.setLike(currentLikes - 1);
        film.getUsers().remove(user);

    }

    public List<Film> getFilmList(Integer count) {
        if (count == null) {
            count = 10;
        }
        return filmStorage.getFilmList()
                .stream()
                .sorted(Comparator.comparing(Film::getLike, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(count)
                .toList();
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public void deleteFilm(Film film) {
        filmStorage.deleteFilm(film);
    }

    public List<Film> getFilmList() {
        return filmStorage.getFilmList();
    }
}
