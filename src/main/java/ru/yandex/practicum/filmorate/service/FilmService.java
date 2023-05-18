package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.ObjectNotFound;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private final FilmStorage filmManager;

    public FilmService(FilmStorage filmManager) {
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
        if (userId <= 0) {
            throw new ObjectNotFound(User.class);
        }
        getById(id).getUsersLike().add(userId);
    }

    public void deleteFilmLike(int id, int userId) {
        if (userId <= 0) {
            throw new ObjectNotFound(User.class);
        }
        getById(id).getUsersLike().remove(userId);
    }

    public List<Film> getTop(int count) {
        List<Film> top = new ArrayList<>(filmManager.getAll());
        top.sort((filmLeft, filmRight) -> filmRight.getUsersLike().size() - filmLeft.getUsersLike().size());
        if (top.size() > count) {
            top = top.subList(0, count);
        }
        return top;
    }
}
