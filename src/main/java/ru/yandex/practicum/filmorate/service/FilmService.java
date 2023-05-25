package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Service
public class FilmService {

    private final FilmStorage filmManager;

    public FilmService(@Qualifier("FilmDbStorage") FilmStorage filmManager) {
        this.filmManager = filmManager;
    }

    public List<Film> getAll() {
        return filmManager.getAll();
    }

    public Film add(Film film) {
        if (film == null) {
            throw new NullPointerException("Передан пустой фильм");
        }
        return filmManager.add(film);
    }

    public Film update(Film film) {
        return filmManager.update(film);
    }

    public Film getById(int id) {
        return filmManager.getById(id);
    }

    public void addLike(int id, int userId) {
        filmManager.addLike(id, userId);
    }

    public void deleteFilmLike(int id, int userId) {
        filmManager.deleteFilmLike(id, userId);
    }

    public List<Film> getTop(int count) {
        return filmManager.getTop(count);
    }
}
