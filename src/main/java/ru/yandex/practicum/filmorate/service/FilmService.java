package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Service
public class FilmService {

    private final FilmStorage filmStorage;

    public FilmService(@Qualifier("FilmDbStorage") FilmStorage filmManager) {
        this.filmStorage = filmManager;
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film add(Film film) {
        if (film == null) {
            throw new NullPointerException("Передан пустой фильм");
        }
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film getById(int id) {
        return filmStorage.getById(id);
    }

    public void addLike(int id, int userId) {
        filmStorage.addLike(id, userId);
    }

    public void deleteFilmLike(int id, int userId) {
        filmStorage.deleteFilmLike(id, userId);
    }

    public List<Film> getTop(int count) {
        return filmStorage.getTop(count);
    }
}
