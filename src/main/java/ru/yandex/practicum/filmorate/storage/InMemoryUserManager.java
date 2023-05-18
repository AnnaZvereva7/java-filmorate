package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.ObjectNotFound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserManager implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int lastUserId = 0;

    public List<User> getAll() {
        return List.copyOf(users.values());
    }

    public User getUserById(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new ObjectNotFound(User.class);
        }
    }

    public User add(User user) {
        lastUserId += 1;
        user = user.withId(lastUserId);
        users.put(lastUserId, user);
        log.info("Добавлен пользователь {}", user.toString());
        return user;
    }

    public User update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new ObjectNotFound(User.class);
        }
        return user;
    }

}
