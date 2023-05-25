package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    List<User> getAll();

    User getById(int id);

    User add(User user);

    User update(User user);

    void saveFriendship(int id, int friendId);

    void removeFriendship(int id, int friendId);

}
