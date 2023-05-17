package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    List<Film> getFilms();

    Film add(Film film);

    Film update(Film film);

    Film getFilmById(int id);
}
