package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Rating {
    NO_RATING(0, "NO_RATING", "у фильма нет рейтинга"),
    G(1, "G", "у фильма нет возрастных ограничений"),
    PG(2, "PG", "детям рекомендуется смотреть фильм с родителями"),
    PG13(3, "PG-13", "детям до 13 лет просмотр не желателен"),
    R(4, "R", "лицам до 17 лет просматривать фильм можно только в присутствии взрослого"),
    NC17(5, "NC-17", "лицам до 18 лет просмотр запрещён");

    private final int id;
    private final String name;
    private final String description;

    Rating(int id, String name, String description) {
        this.description = description;
        this.name = name;
        this.id = id;
    }

    @JsonCreator
    public static Rating decode(int id) {
        return Stream.of(Rating.values()).filter(targetEnum -> targetEnum.id == id).findFirst().orElse(null);
    }
}
