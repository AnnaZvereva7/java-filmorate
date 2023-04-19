package ru.yandex.practicum.filmorate.managers;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.exceptions.ValidationException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class InMemoryFilmManager implements Manager<Film>{
    private final Map<Integer, Film> films = new HashMap<>();
    private int lastId=0;

    public Collection<Film> get() {
        return films.values();
    }

    public Film add(Film film) {
        isValid(film);
        if (films.containsKey(film.getId())) {
            throw new ValidationException("фильм с таким id уже есть");
        }
        lastId += 1;
        val newFilm = film.withId(lastId);
        films.put(lastId, newFilm);
        return newFilm;
    }

    public Film update( Film film) {
        isValid(film);
        if (films.containsKey(film.getId())) {
                films.put(film.getId(), film);
                return film;
        } else {
            throw new ValidationException("Фильма с таким id нет");
        }
    }

    public boolean isValid(Film film){
        if (film.getName().isBlank()) {
            log.debug("Название не может быть пустым");
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length()>200) {
            log.debug("Длина описания должна быть <=200 символов");
            throw new ValidationException("Длина описания должна быть <=200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.debug("дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration()<=0) {
            log.debug("Продолжительность фильма должна быть положительной");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        return true;
    }

}
