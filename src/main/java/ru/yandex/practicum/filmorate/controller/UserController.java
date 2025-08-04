package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final List<User> users = new ArrayList<>();

    private long customIdCounter = 0;

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {

        validationUsers(user);

        User newUser = new User();
        newUser.setId(++customIdCounter);
        newUser.setEmail(user.getEmail());
        newUser.setLogin(user.getLogin());
        if (user.getName() == null || user.getName().isEmpty()) {
            log.info("Имя пользователя пустое имя будет взято из Login");
            newUser.setName(user.getLogin());
        } else {
            newUser.setName(user.getName());
        }
        newUser.setBirthday(user.getBirthday());
        users.add(newUser);

        return newUser;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {

        if (user.getId() == null) {
            throw new NotFoundException("Id пользователя не найдено!");
        }

        Optional<User> optionalUser = users.stream()
                .filter(user1 -> user1.getId().equals(user.getId()))
                .findFirst();

        if (optionalUser.isEmpty()) {
            log.error("Пользователь не найден!");
            throw new NotFoundException("Пользователь не найден!");
        }

        validationUsers(user);

        User updatedUser = optionalUser.get();
        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setLogin(user.getLogin());
        updatedUser.setBirthday(user.getBirthday());

        return updatedUser;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return users;
    }

    public void validationUsers(User user) {

        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("Email не может быть пустым и должен содержать @!");
            throw new ValidationException("Email не может быть пустым и должен содержать @!");
        }

        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("Login должен быть заполнен и не должен содержать пробелы!");
            throw new ValidationException("Login должен быть заполнен и не должен содержать пробелы!");
        }

        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Дата рождения не может быть больше будущего!");
            throw new ValidationException("Дата рождения не может быть больше будущего!");
        }

    }

}
