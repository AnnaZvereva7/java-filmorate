package ru.yandex.practicum.filmorate.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.managers.InMemoryFilmManager;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@Component
public class FilmController {
    private final InMemoryFilmManager filmManager;

    public FilmController(InMemoryFilmManager filmManager) {
        this.filmManager = filmManager;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmManager.get();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmManager.add(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmManager.update(film);
    }

}
