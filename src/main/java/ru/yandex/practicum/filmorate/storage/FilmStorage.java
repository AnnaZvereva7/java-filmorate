package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    List<Film> getAll();

    Film add(Film film);

    Film update(Film film);

    Film getById(int id);

    void addLike(int id, int userId);

    void deleteFilmLike(int id, int userId);

    List<Film> getTop(int count);
}
