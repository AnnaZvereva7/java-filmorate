package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.ObjectNotFound;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userManager;

    public UserService(UserStorage userManager) {
        this.userManager = userManager;
    }

    public User addUser(User user) {
        return userManager.add(renameUser(user));
    }

    private User renameUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user = user.rename(user);
        }
        return user;
    }

    public User updateUser(User user) {
        return userManager.update(renameUser(user));
    }

    public Collection<User> getUsers() {
        return userManager.getList();
    }

    public void addFriends(Integer id, Integer friendId) {
        User user = getUserById(id);
        User userToAdd = getUserById(friendId);
        if (user.getFriendsId().add(friendId)) {
            userToAdd.getFriendsId().add(id);
        } else {
            throw new KeyAlreadyExistsException("Уже есть такой друг");
        }
    }

    public void removeFriends(Integer id, Integer friendId) {
        User user = getUserById(id);
        User userToDelete = getUserById(friendId);
        if (user.getFriendsId().remove(friendId)) {
            userToDelete.getFriendsId().remove(id);
        } else {
            throw new ObjectNotFound(User.class);
        }
    }

    public Collection<User> getCommonFriends(Integer id, Integer otherId) {
        User user1 = getUserById(id);
        User user2 = getUserById(otherId);
        return user1.getFriendsId().stream()
                .filter(it -> user2.getFriendsId().contains(it))
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    public Collection<User> getListOfFriends(Integer id) {
        Collection<User> friends = new TreeSet<>();
        if (getUserById(id).getFriendsId().size() != 0) {
            for (Integer idOfFriend : getUserById(id).getFriendsId()) {
                friends.add(getUserById(idOfFriend));
            }
        }
        return friends;
    }

    public User getUserById(Integer id) {
        return userManager.getUserById(id);
    }

}
