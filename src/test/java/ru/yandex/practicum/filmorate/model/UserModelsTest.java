package ru.yandex.practicum.filmorate.model;


import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserModelsTest {

   private final UserController userController = new UserController();

    @Test
    void testValidEmailUser() {

       User user = new User();
       user.setId(1L);
       user.setEmail(null);
       user.setLogin("TestLogin");
       user.setName("Test Name");
       user.setBirthday(LocalDate.of(2002,12,6));

       assertThrows(ValidationException.class,() -> userController.validationUsers(user));

   }

    @Test
    void testValidLoginUser() {

        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.ru");
        user.setLogin("Test Login");
        user.setName("Test Name");
        user.setBirthday(LocalDate.of(2002,12,6));

        assertThrows(ValidationException.class,() -> userController.validationUsers(user));

   }

   @Test
   void testValidDateBirthdayUser() {

       User user = new User();
       user.setId(1L);
       user.setEmail("test@mail.ru");
       user.setLogin("TestLogin");
       user.setName("Test Name");
       user.setBirthday(LocalDate.of(2032,12,6));

       assertThrows(ValidationException.class,() -> userController.validationUsers(user));

   }

}
