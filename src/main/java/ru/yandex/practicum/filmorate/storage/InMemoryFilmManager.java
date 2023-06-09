package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.exceptions.ObjectNotFound;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Component
public class InMemoryFilmManager implements FilmStorage {

    private final Map<Integer, Film> films = new TreeMap<>();
    private int lastId = 0;

    @Override
    public List<Film> getAll() {
        return List.copyOf(films.values());
    }

    public Film add(Film film) {
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
            throw new ObjectNotFound(Film.class);
        }
    }

    public Film getById(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new ObjectNotFound(Film.class);
        }
    }
}
