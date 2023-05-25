package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import ru.yandex.practicum.filmorate.validator.FilmDateValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
//@Qualifier("InMemory")
@Builder
public class Film {
    @With
    private final int id;
    @NotBlank(message = "Название не может быть пустым")
    private final String name;
    @Size(max = 200, message = "Длина описания должна быть <=200 символов")
    @NotNull
    private final String description;
    @FilmDateValidator
    private final LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private final int duration;
    @JsonIgnore
    private final Set<Integer> usersLike = new HashSet<>();
    @NotNull
    private final Rating mpa;
    private final List<Genre> genres = new ArrayList<>();
}
