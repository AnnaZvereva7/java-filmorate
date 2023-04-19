package ru.yandex.practicum.filmorate.userTests;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.managers.Manager;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class UserTests <T extends Manager>{
    T manager;

    protected UserTests(T manager) {
        this.manager=manager;
    }

    @Test
    public void validationOfEmail() {
        User user1 = new User(1, "", "login", "name", LocalDate.of(2023, 04, 18));
        User user2 = new User(2, "email", "login", "name", LocalDate.of(2023, 04, 18));
        User user3 = new User(3, "email@email", "login", "name", LocalDate.of(2023, 04, 18));
        final ValidationException exception = assertThrows(ValidationException.class, () -> manager.isValid(user1));
        assertEquals("Не корректный email", exception.getMessage());
        final ValidationException exception2 = assertThrows(ValidationException.class, () -> manager.isValid(user2));
        assertEquals("Не корректный email", exception2.getMessage());
        assertEquals(true, manager.isValid(user3));
    }

    @Test
    public void validationOfLogin() {
        User user1 = new User(1, "email@email", "", "name", LocalDate.of(2023, 04, 18));
        User user2 = new User(2, "email@email", "lo gin", "name", LocalDate.of(2023, 04, 18));
        User user3 = new User(3, "email@email", "login", "name", LocalDate.of(2023, 04, 18));
        final ValidationException exception = assertThrows(ValidationException.class, () -> manager.isValid(user1));
        assertEquals("Не корректный логин", exception.getMessage());
        final ValidationException exception2 = assertThrows(ValidationException.class, () -> manager.isValid(user2));
        assertEquals("Не корректный логин", exception2.getMessage());
        assertEquals(true, manager.isValid(user3));
    }

    @Test
    public void validationOfBirthday() {
        User user1 = new User(1, "email@email", "login", "name", LocalDate.of(2023, 04, 19));
        User user2 = new User(2, "email@email", "login", "name", LocalDate.now().plusDays(1));
        final ValidationException exception = assertThrows(ValidationException.class, () -> manager.isValid(user2));
        assertEquals("Не корректная дата рождения", exception.getMessage());
        assertEquals(true, manager.isValid(user1));
    }
}
