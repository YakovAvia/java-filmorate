package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserStorage userStorage;

    public void addFriend(Long userId, Long friendId) {
        List<User> usersList = userStorage.getUsers();
        if (usersList == null) {
            throw new NotFoundException("Список пользователей пуст");
        }

        User users = usersList.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));

        User frendUser = usersList.stream()
                .filter(user -> user.getId().equals(friendId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Дружеский пользователь не найден!"));

        users.getFriends().add(frendUser);
        frendUser.getFriends().add(users);
    }

    public void deleteFriend(Long userId, Long friendId) {

        User users = userStorage.getUsers().stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));

        User frendUser = userStorage.getUsers().stream()
                .filter(user -> user.getId().equals(friendId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Дружеский пользователь не найден!"));

        users.getFriends().remove(frendUser);
        frendUser.getFriends().remove(users);
    }

    public Set<User> getFriendsUser(Long userId) {

        User users = userStorage.getUsers()
                .stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));

        return users.getFriends();
    }

    public Set<User> getMutualFriends(Long userId, Long friendId) {

        User users = userStorage.getUsers()
                .stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElse(null);

        User frendUser = userStorage.getUsers()
                .stream()
                .filter(user -> user.getId().equals(friendId))
                .findFirst()
                .orElse(null);

        if (users == null || frendUser == null) {
            return Collections.emptySet();
        }
        Set<User> friendsUser = new HashSet<>(users.getFriends());
        Set<User> friendsUser1 = new HashSet<>(frendUser.getFriends());
        friendsUser.retainAll(friendsUser1);
        return friendsUser;
    }

    public User getUserById(Long id) {
        return userStorage.getUsers()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public void deleteUser(User user) {
        userStorage.deleteUser(user);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

}
