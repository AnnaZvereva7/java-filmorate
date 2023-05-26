package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFound;
import ru.yandex.practicum.filmorate.model.User;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("InMemoryUserManager")
public class InMemoryUserManager implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int lastUserId = 0;

    public List<User> getAll() {
        return List.copyOf(users.values());
    }

    public User getById(int id) {
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

    public void saveFriendship(int id, int friendId) {
        User user = getById(id);
        User userToAdd = getById(friendId);
        if (user.getFriendsId().add(friendId)) {
            userToAdd.getFriendsId().add(id);
        } else {
            throw new KeyAlreadyExistsException("Уже есть такой друг");
        }
    }

    public void removeFriendship(int id, int friendId) {
        User user = getById(id);
        User userToDelete = getById(friendId);
        if (user.getFriendsId().remove(friendId)) {
            userToDelete.getFriendsId().remove(id);
        } else {
            throw new ObjectNotFound(User.class);
        }
    }

}
