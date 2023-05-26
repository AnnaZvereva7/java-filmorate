package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    public UserService(@Qualifier("UserDbStorage") UserStorage userManager) {
        this.userStorage = userManager;
    }

    public User getById(Integer id) {
        return userStorage.getById(id);
    }

    public User add(User user) {
        return userStorage.add(rename(user));
    }

    public User update(User user) {
        return userStorage.update(rename(user));
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public void addFriends(Integer id, Integer friendId) {
        userStorage.saveFriendship(id, friendId);
    }

    public void remove(int id, int friendId) {
        userStorage.removeFriendship(id, friendId);
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        User user = getById(id);
        User otherUser = getById(otherId);
        return user.getFriendsId().stream()
                .filter(it -> otherUser.getFriendsId().contains(it))
                .map(this::getById)
                .collect(Collectors.toList());
    }

    public Collection<User> getListOfFriends(Integer id) {
        Collection<User> friends = new TreeSet<>();
        if (getById(id).getFriendsId().size() != 0) {
            for (Integer idOfFriend : getById(id).getFriendsId()) {
                friends.add(getById(idOfFriend));
            }
        }
        return friends;
    }

    private User rename(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user = user.rename(user);
        }
        return user;
    }

}
