package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.ValidationException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserManager implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int lastUserId = 0;

    public Collection<User> getList() {
        return users.values();
    }

    public Map<Integer, User> get() {
        return users;
    }

    public User add(User user) {
        if (user.getName().isBlank()) {
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

    public User update(User user) {
        if (users.containsKey(user.getId())) {
            if (user.getName() == null) {
                user = user.withName(user.getLogin());
                log.info("Установлено имя пользователя {}", user.getName());
            }
            users.put(user.getId(), user);
        } else {
            log.debug("Такого пользователя нет");
            throw new ValidationException("Такого пользователя нет");
        }
        return user;
    }

}
