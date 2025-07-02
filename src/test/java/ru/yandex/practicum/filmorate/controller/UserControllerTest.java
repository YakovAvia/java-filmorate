package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void userCreateControllerTest() throws Exception {

        User user = new User();
        user.setEmail("test@test.com");
        user.setLogin("test");
        user.setName("test");
        user.setBirthday(LocalDate.of(2002,12,6));

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@test.com"));

    }

    @Test
    public void userEmailTest() throws Exception {

        User user = new User();
        user.setEmail("testtest.com");
        user.setLogin("test");
        user.setName("test");
        user.setBirthday(LocalDate.of(2002,12,6));

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void userLoginTest() throws Exception {

        User user = new User();
        user.setEmail("test@test.com");
        user.setLogin(null);
        user.setName("test");
        user.setBirthday(LocalDate.of(2002,12,6));

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void userNameTest() throws Exception {

        User user = new User();
        user.setEmail("test@test.com");
        user.setLogin("test");
        user.setName(null);
        user.setBirthday(LocalDate.of(2002,12,6));

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

    }

    @Test
    public void userBirthdayTest() throws Exception {

        User user = new User();
        user.setEmail("test@test.com");
        user.setLogin("test");
        user.setName("test");
        user.setBirthday(LocalDate.of(2026,12,6));

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void userUpdateControllerTest() throws Exception {

        User user = new User();
        user.setEmail("test@test.com");
        user.setLogin("test");
        user.setName("test");
        user.setBirthday(LocalDate.of(2002,12,6));

        String responseContent = mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        User createdUser = objectMapper.readValue(responseContent, User.class);

        createdUser.setEmail("t@test.com");
        mvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("t@test.com"));

    }

    @Test
    public void userUpdateIdControllerTest() throws Exception {

        User user = new User();
        user.setEmail("test@test.com");
        user.setLogin("test");
        user.setName("test");
        user.setBirthday(LocalDate.of(2002,12,6));

        String responseContent = mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        User createdUser = objectMapper.readValue(responseContent, User.class);

        createdUser.setId(null);
        createdUser.setEmail("t@test.com");

        mvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdUser)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void userUpdateUserControllerTest() throws Exception {

        User user = new User();
        user.setEmail("test@test.com");
        user.setLogin("test");
        user.setName("test");
        user.setBirthday(LocalDate.of(2002,12,6));

        String responseContent = mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        User createdUser = objectMapper.readValue(responseContent, User.class);

        createdUser.setId(4L);
        createdUser.setEmail("t@test.com");

        mvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdUser)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void userGetAllControllerTest() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }
}
