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

    public InMemoryFilmManager getFilmManager() {
        return filmManager;
    }

    public Film getFilmById(Integer id) {
        if (filmManager.get().containsKey(id)) {
            return filmManager.get().get(id);
        } else {
            throw new NullPointerException("Фильма с таким id нет");
        }
    }

    public void addFilmLike(Integer id, Integer userId) {
        if (userId <= 0) {
            throw new NullPointerException("Не корректный id пользователя");
        }
        if (filmManager.get().containsKey(id)) {
            filmManager.get().get(id).getUsersLike().add(userId);
        } else {
            throw new NullPointerException("Такого фильма нет");
        }
    }

    public void deleteFilmLike(Integer id, Integer userId) {
        if (userId <= 0) {
            throw new NullPointerException("Не корректный id пользователя");
        }
        if (filmManager.get().containsKey(id)) {
            filmManager.get().get(id).getUsersLike().remove(userId);
        } else {
            throw new NullPointerException("Такого фильма нет");
        }
    }

    public Collection<Film> getTop(int count) {
        List<Film> top = new ArrayList<>(filmManager.get().values());
        top.sort((filmLeft, filmRight) -> filmRight.getUsersLike().size() - filmLeft.getUsersLike().size());
        if (top.size() > count) {
            top = top.subList(0, count);
        }
        return top;
    }

}
