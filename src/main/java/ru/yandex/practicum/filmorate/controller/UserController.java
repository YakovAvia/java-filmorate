package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;


@RestController
public class UserController {


    private UserStorage userStorage;

    UserService userService;

    @Autowired
    public UserController(UserService userService, UserStorage userStorage) {
        this.userService = userService;
        this.userStorage = userStorage;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userStorage.createUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        return userStorage.updateUser(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addFriend(id, friendId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users")
    public void deleteUser(@RequestBody User user) {
        userStorage.deleteUser(user);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userStorage.getUsers();
    }

    @GetMapping("/users/{id}/friends")
    public Set<User> getFriends(@PathVariable Long id) {
        return userService.getFriendsUser(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Set<User> getFriendsCommon(@PathVariable Long otherId, @PathVariable Long id) {
        return userService.getMutualFriends(otherId, id);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
