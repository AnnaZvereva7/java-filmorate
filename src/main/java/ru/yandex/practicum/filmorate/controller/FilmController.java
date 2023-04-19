package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.managers.InMemoryFilmManager;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;


@RestController
@RequestMapping("/films")
public class FilmController {
    InMemoryFilmManager filmManager = new InMemoryFilmManager();

    @GetMapping
    public Collection<Film> getFilms() {
        return filmManager.get();
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        return filmManager.add(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        return filmManager.update(film);
    }

}
