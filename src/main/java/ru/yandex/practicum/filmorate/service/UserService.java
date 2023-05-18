package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.ObjectNotFound;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userManager;

    public UserService(UserStorage userManager) {
        this.userManager = userManager;
    }

    public User getById(Integer id) {
        return userManager.getUserById(id);
    }

    public User add(User user) {
        return userManager.add(rename(user));
    }

    public User update(User user) {
        return userManager.update(rename(user));
    }

    public List<User> getAll() {
        return userManager.getAll();
    }

    public void addFriends(Integer id, Integer friendId) {
        User user = getById(id);
        User userToAdd = getById(friendId);
        if (user.getFriendsId().add(friendId)) {
            userToAdd.getFriendsId().add(id);
        } else {
            throw new KeyAlreadyExistsException("Уже есть такой друг");
        }
    }

    public void remove(Integer id, Integer friendId) {
        User user = getById(id);
        User userToDelete = getById(friendId);
        if (user.getFriendsId().remove(friendId)) {
            userToDelete.getFriendsId().remove(id);
        } else {
            throw new ObjectNotFound(User.class);
        }
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        User user1 = getById(id);
        User user2 = getById(otherId);
        return user1.getFriendsId().stream()
                .filter(it -> user2.getFriendsId().contains(it))
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
