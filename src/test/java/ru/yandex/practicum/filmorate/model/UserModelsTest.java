package ru.yandex.practicum.filmorate.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserModelsTest {

    InMemoryUserStorage userStorage;

    @BeforeEach
    void setUp() {
        userStorage = new InMemoryUserStorage();
    }

    @Test
    void testValidEmailUser() {

        User user = new User();
        user.setId(1L);
        user.setEmail(null);
        user.setLogin("TestLogin");
        user.setName("Test Name");
        user.setBirthday(LocalDate.of(2002, 12, 6));

        assertThrows(ValidationException.class, () -> userStorage.validationUsers(user));

    }

    @Test
    void testValidLoginUser() {

        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.ru");
        user.setLogin("Test Login");
        user.setName("Test Name");
        user.setBirthday(LocalDate.of(2002, 12, 6));

        assertThrows(ValidationException.class, () -> userStorage.validationUsers(user));

    }

    @Test
    void testValidDateBirthdayUser() {

        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.ru");
        user.setLogin("TestLogin");
        user.setName("Test Name");
        user.setBirthday(LocalDate.of(2032, 12, 6));

        assertThrows(ValidationException.class, () -> userStorage.validationUsers(user));

    }

}
