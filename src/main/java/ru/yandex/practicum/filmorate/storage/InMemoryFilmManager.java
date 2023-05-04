package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.exceptions.ValidationException;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmManager implements FilmStorage {

    private final Map<Integer, Film> films = new TreeMap<>();
    private int lastId = 0;

    @Override
    public Map<Integer, Film> get() {
        return films;
    }

    public Film add(Film film) {
        if (films.containsKey(film.getId())) {
            throw new ValidationException("фильм с таким id уже есть");
        }
        lastId += 1;
        film = film.withId(lastId);
        films.put(lastId, film);
        return film;
    }

    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new ValidationException("Фильма с таким id нет");
        }
    }
}
