package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Component
public class FilmService {
    private final InMemoryFilmManager filmManager;

    public FilmService(InMemoryFilmManager filmManager) {
        this.filmManager = filmManager;
    }

    public Collection<Film> getFilms() {
        return filmManager.getFilms().values();
    }

    public Film addFilm(Film film) {
        return filmManager.add(film);
    }

    public Film updateFilm(Film film) {
        return filmManager.update(film);
    }

    public Film getFilmById(Integer id) {
        if (filmManager.getFilms().containsKey(id)) {
            return filmManager.getFilms().get(id);
        } else {
            throw new NullPointerException("Фильма с таким id нет");
        }
    }

    public void addFilmLike(Integer id, Integer userId) {
        if (userId <= 0) {
            throw new NullPointerException("Не корректный id пользователя");
        }
        getFilmById(id).getUsersLike().add(userId);
    }

    public void deleteFilmLike(Integer id, Integer userId) {
        if (userId <= 0) {
            throw new NullPointerException("Не корректный id пользователя");
        }
        getFilmById(id).getUsersLike().remove(userId);
    }

    public Collection<Film> getTop(int count) {
        List<Film> top = new ArrayList<>(filmManager.getFilms().values());
        top.sort((filmLeft, filmRight) -> filmRight.getUsersLike().size() - filmLeft.getUsersLike().size());
        if (top.size() > count) {
            top = top.subList(0, count);
        }
        return top;
    }
}
