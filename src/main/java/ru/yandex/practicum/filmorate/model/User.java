package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.With;

import java.time.LocalDate;

@Data
public class User {
    @With private final int id;
    private final String email;
    private final String login;
    @With private final String name;
    private final LocalDate birthday;
}
