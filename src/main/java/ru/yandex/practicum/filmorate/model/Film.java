package ru.yandex.practicum.filmorate.model;


import lombok.Data;
import lombok.With;

import java.time.LocalDate;

@Data
public class Film {
    @With
    private final int id;
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final Integer duration;

}
