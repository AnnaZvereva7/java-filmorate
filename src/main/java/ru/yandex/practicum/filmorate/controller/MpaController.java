package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFound;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {
    @GetMapping("/{id}")
    public Rating getById(@PathVariable int id) {
        if (Rating.decode(id) == null) {
            throw new ObjectNotFound(Rating.class);
        }
        return Rating.decode(id);
    }

    @GetMapping
    public List<Rating> getAll() {
        return Stream.of(Rating.values()).filter(r -> r.getId() != 0).collect(Collectors.toList());
    }
}
