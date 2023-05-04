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

    public InMemoryUserManager getUserManager() {
        return userManager;
    }

    public void addFriends(Integer id, Integer friendId) {
        if (userManager.get().containsKey(id) && userManager.get().containsKey(friendId)) {
            User user = userManager.get().get(id);
            User userToAdd = userManager.get().get(friendId);
            if (userToAdd.getFriendsId().contains(id)) {
                throw new KeyAlreadyExistsException("Уже есть такой друг");
            } else {
                user.getFriendsId().add(friendId);
                userToAdd.getFriendsId().add(id);
            }
        } else {
            throw new NullPointerException("Пользователь с некорректным id");
        }

    }

    public void removeFriends(Integer id, Integer friendId) {
        if (userManager.get().containsKey(id) && userManager.get().containsKey(friendId)) {
            User user = userManager.get().get(id);
            User userToDelete = userManager.get().get(friendId);
            if (user.getFriendsId().contains(friendId)) {
                user.getFriendsId().remove(friendId);
                userToDelete.getFriendsId().remove(id);
            } else {
                throw new NullPointerException("Такого пользователя нет в друзьях");
            }
        } else {
            throw new NullPointerException("Пользователь с некорректным id");
        }

    }

    public Collection<User> getCommonFriends(Integer id, Integer otherId) {
        if (userManager.get().containsKey(id) && userManager.get().containsKey(otherId)) {
            User user1 = userManager.get().get(id);
            User user2 = userManager.get().get(otherId);
            Collection<User> commonFriends = new ArrayList<>();
            if (!user1.getFriendsId().isEmpty()) {
                for (Integer idOfFriend : user1.getFriendsId()) {
                    if (user2.getFriendsId().contains(idOfFriend)) {
                        commonFriends.add(userManager.get().get(idOfFriend));
                    }
                }
            }
            return commonFriends;
        } else {
            throw new NullPointerException("Пользователь с некорректным id");
        }

    }

    public Collection<User> getListOfFriends(Integer id) {
        if (userManager.get().get(id).getFriendsId().size() == 0) {
            throw new NullPointerException("Список друзей пользователя пуст");
        } else {
            Collection<User> friends = new TreeSet<>();
            for (Integer idOfFriend : userManager.get().get(id).getFriendsId()) {
                friends.add(userManager.get().get(idOfFriend));
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
