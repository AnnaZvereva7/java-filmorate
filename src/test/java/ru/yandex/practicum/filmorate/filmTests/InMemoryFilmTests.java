package ru.yandex.practicum.filmorate.filmTests;

import ru.yandex.practicum.filmorate.managers.InMemoryFilmManager;

public class InMemoryFilmTests extends FilmTests<InMemoryFilmManager> {
    protected InMemoryFilmTests() {
        super(new InMemoryFilmManager());
    }
}
