package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.With;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Slf4j
public class User implements Comparable<User> {
    @With
    private final int id;
    @Email(message = "Не корректный email")
    @NotEmpty
    private final String email;
    @Pattern(regexp = "[^\\s]*", message = "Не корректный логин")
    @NotEmpty(message = "Не корректный логин")
    private final String login;
    @With
    private final String name;
    @PastOrPresent(message = "Не корректная дата рождения")
    @NotNull(message = "Не корректная дата рождения")
    private final LocalDate birthday;
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private final Set<Integer> friendsId = new HashSet<>();

    public int compareTo(User user) {
        return user.name.compareTo(this.name);
    }

    public User rename(User user) {
        user = user.withName(user.getLogin());
        log.debug("Установлено имя пользователя {}", user.getName());
        return user;
    }

}
