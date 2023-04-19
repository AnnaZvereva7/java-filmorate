package ru.yandex.practicum.filmorate.managers;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.ValidationException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class InMemoryUserManager implements Manager<User> {
    private final Map<Integer, User> users = new HashMap<>();
    private int lastUserId=0;

    public Collection<User> get() {
        return users.values();
    }

    public User add(User user) {
        isValid(user);
        if (user.getName() == null) {
            user = user.withName(user.getLogin());
            log.info("Установлено имя пользователя {}", user.getName());
        }
        if (users.containsKey(user.getId())) {
            log.debug("Такой пользователь уже есть");
            throw new ValidationException("Такой пользователь уже есть");
        } else {
            lastUserId += 1;
            user = user.withId(lastUserId);
            users.put(lastUserId, user);
            log.info("Добавлен пользователь {}", user.toString());
        }
        return user;
    }

    public User update (User user) {
        isValid(user);
        if(users.containsKey(user.getId())) {
            if (user.getName()==null) {
                user=user.withName(user.getLogin());
                log.info("Установлено имя пользователя {}", user.getName());
            }
            users.put(user.getId(), user);
        } else {
            log.debug("Такого пользователя нет");
            throw new ValidationException("Такого пользователя нет");
        }
        return user;
    }

    public boolean isValid(User user) {
        if ((user.getEmail().lastIndexOf("@") == -1)||user.getEmail().isBlank()) {
            log.debug("Не корректный email");
            throw new ValidationException("Не корректный email");
        }
        if (user.getLogin().isBlank()||user.getLogin().lastIndexOf(" ")>-1) {
            log.debug("Не корректный логин");
            throw new ValidationException("Не корректный логин");
        }
        if(user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("Не корректная дата рождения");
            throw new ValidationException("Не корректная дата рождения");
        }
        return true;
    }

}
