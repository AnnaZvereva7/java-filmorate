package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.managers.InMemoryUserManager;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;


@RestController
@RequestMapping("/users")
public class UserController {
    InMemoryUserManager userManager = new InMemoryUserManager();

    @GetMapping
    public Collection<User> getUsers() {
        return userManager.get();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userManager.add(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userManager.update(user);
    }


}
