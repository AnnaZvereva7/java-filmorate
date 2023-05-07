package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserManager;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

@Service
@Component
public class UserService {
    private final InMemoryUserManager userManager;

    public UserService(InMemoryUserManager userManager) {
        this.userManager = userManager;
    }

    public User addUser(User user) {
        return userManager.add(user);
    }

    public User updateUser(User user) {
        return userManager.update(user);
    }

    public Collection<User> getUsers() {
        return userManager.getList();
    }

    public void addFriends(Integer id, Integer friendId) {
        User user = getUserById(id);
        User userToAdd = getUserById(friendId);
        if (userToAdd.getFriendsId().contains(id)) {
            throw new KeyAlreadyExistsException("Уже есть такой друг");
        } else {
            user.getFriendsId().add(friendId);
            userToAdd.getFriendsId().add(id);
        }
    }

    public void removeFriends(Integer id, Integer friendId) {
        User user = getUserById(id);
        User userToDelete = getUserById(friendId);
        if (user.getFriendsId().contains(friendId)) {
            user.getFriendsId().remove(friendId);
            userToDelete.getFriendsId().remove(id);
        } else {
            throw new NullPointerException("Такого пользователя нет в друзьях");
        }
    }

    public Collection<User> getCommonFriends(Integer id, Integer otherId) {
        User user1 = getUserById(id);
        User user2 = getUserById(otherId);
        Collection<User> commonFriends = new ArrayList<>();
        if (!user1.getFriendsId().isEmpty()) {
            for (Integer idOfFriend : user1.getFriendsId()) {
                if (user2.getFriendsId().contains(idOfFriend)) {
                    commonFriends.add(getUserById(idOfFriend));
                }
            }
        }
        return commonFriends;
    }

    public Collection<User> getListOfFriends(Integer id) {
        if (getUserById(id).getFriendsId().size() == 0) {
            throw new NullPointerException("Список друзей пользователя пуст");
        } else {
            Collection<User> friends = new TreeSet<>();
            for (Integer idOfFriend : getUserById(id).getFriendsId()) {
                friends.add(getUserById(idOfFriend));
            }
            return friends;
        }
    }

    public User getUserById(Integer id) {
        if (userManager.get().containsKey(id)) {
            return userManager.get().get(id);
        } else {
            throw new NullPointerException("Пользователя с таким id нет");
        }
    }

}