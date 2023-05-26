package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Genre {
    COMEDY(1, "Комедия"),
    DRAMA(2, "Драма"),
    CARTOON(3, "Мультфильм"),
    THRILLER(4, "Триллер"),
    DOCUMENTARY(5, "Документальный"),
    ACTION(6, "Боевик");

    private final String name;
    private final int id;

    Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonCreator
    public static Genre decode(int id) {
        return Stream.of(Genre.values()).filter(targetEnum -> targetEnum.id == id).findFirst().orElse(null);
    }
}
