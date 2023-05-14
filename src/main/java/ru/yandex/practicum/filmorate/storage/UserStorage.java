package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

public interface UserStorage {

    Collection<User> getList();

    User getUserById (int id);
    Map<Integer, User> get();

    User add(User user);

    User update(User user);

}
