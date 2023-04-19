package ru.yandex.practicum.filmorate.filmTests;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.managers.Manager;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.exceptions.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class FilmTests<T extends Manager> {
    T manager;

    protected FilmTests(T manager) {
        this.manager = manager;
    }


    @Test
    public void validationOfName() {
        Film film1 = new Film(1, "", "description", LocalDate.of(2000, 12, 11), 100);
        Film film2 = new Film(2, " ", "description", LocalDate.of(2000, 12, 11), 100);
        Film film3 = new Film(3, "3", "description", LocalDate.of(2000, 12, 11), 100);
        final ValidationException exception = assertThrows(ValidationException.class, () -> manager.isValid(film1));
        assertEquals("Название не может быть пустым", exception.getMessage());
        final ValidationException exception2 = assertThrows(ValidationException.class, () -> manager.isValid(film2));
        assertEquals("Название не может быть пустым", exception2.getMessage());
        assertEquals(true, manager.isValid(film3));
    }

    @Test
    public void validationOfDescription() {
        Film film1 = new Film(1, "name", "descriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescription",
                LocalDate.of(2000, 12, 11), 100);
        Film film2 = new Film(1, "name", "descriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptiodescriptio",
                LocalDate.of(2000, 12, 11), 100);
        final ValidationException exception = assertThrows(ValidationException.class, () -> manager.isValid(film1));
        assertEquals("Длина описания должна быть <=200 символов", exception.getMessage());
        assertEquals(true, manager.isValid(film2));
    }

    @Test
    public void validationOfDate() {
        Film film1 = new Film(1, "name", "description", LocalDate.of(1895, 12, 27), 100);
        Film film2 = new Film(2, "name", "description", LocalDate.of(1895, 12, 28), 100);
        final ValidationException exception = assertThrows(ValidationException.class, () -> manager.isValid(film1));
        assertEquals("дата релиза — не раньше 28 декабря 1895 года", exception.getMessage());
        assertEquals(true, manager.isValid(film2));
    }

    @Test
    public void validationOfDuration() {
        Film film1 = new Film(1, "name", "description", LocalDate.of(1895, 12, 29), 0);
        Film film2 = new Film(2, "name", "description", LocalDate.of(1895, 12, 29), 1);
        final ValidationException exception = assertThrows(ValidationException.class, () -> manager.isValid(film1));
        assertEquals("Продолжительность фильма должна быть положительной", exception.getMessage());
        assertEquals(true, manager.isValid(film2));
    }
}
