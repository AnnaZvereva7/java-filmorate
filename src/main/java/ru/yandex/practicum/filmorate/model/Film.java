package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;
import ru.yandex.practicum.filmorate.model.validator.FilmDateValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film {
    @With
    private final int id;
    @NotBlank(message = "Название не может быть пустым")
    private final String name;
    @Size(max = 200, message = "Длина описания должна быть <=200 символов")
    private final String description;
    @FilmDateValidator
    private final LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private final Integer duration;
    private final Set<Integer> usersLike = new HashSet<>();

}
