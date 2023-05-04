package ru.yandex.practicum.filmorate.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@Service
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> getFilms() {

        return filmService.getFilmManager().get().values();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.getFilmManager().add(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.getFilmManager().update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addFilmLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteFilmLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getTopFilms(@RequestParam(required = false) Integer count) {
        if (count == null) {
            count = 10;
        }
        return filmService.getTop(count);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        return filmService.getFilmById(id);
    }

}
