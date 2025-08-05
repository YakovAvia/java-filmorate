package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@ToString(exclude = "friends")
public class User {
    @EqualsAndHashCode.Include
    private Long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

    @EqualsAndHashCode.Exclude
    private Set<User> friends;

    public User() {
        this.friends = new HashSet<>();
    }
}
