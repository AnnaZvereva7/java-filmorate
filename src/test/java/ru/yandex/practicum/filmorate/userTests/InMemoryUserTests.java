package ru.yandex.practicum.filmorate.userTests;

import ru.yandex.practicum.filmorate.managers.InMemoryUserManager;

public class InMemoryUserTests extends UserTests<InMemoryUserManager>{
    protected InMemoryUserTests() {
        super(new InMemoryUserManager());
    }
}
