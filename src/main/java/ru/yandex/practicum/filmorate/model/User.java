package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;

import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    @With
    private final int id;
    @Email(message = "Не корректный email")
    private final String email;
    @Pattern(regexp = "[^\\s]*", message = "Не корректный логин")
    private final String login;
    @With
    private final String name;
    @PastOrPresent(message = "Не корректная дата рождения")
    private final LocalDate birthday;

}
