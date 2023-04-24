package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.exceptions.ValidationException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmManager implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int lastId = 0;

    public Collection<Film> get() {
        return films.values();
    }

    public Film add(Film film) {
        if (films.containsKey(film.getId())) {
            throw new ValidationException("фильм с таким id уже есть");
        }
        lastId += 1;
        val newFilm = film.withId(lastId);
        films.put(lastId, newFilm);
        return newFilm;
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
