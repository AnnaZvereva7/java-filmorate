package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFound;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    @GetMapping("/{id}")
    public Genre getById(@PathVariable int id) {
        if (Genre.decode(id) == null) {
            throw new ObjectNotFound(Genre.class);
        }
        return Genre.decode(id);
    }

    @GetMapping
    public List<Genre> getAll() {
        return List.of(Genre.values());
    }
}
