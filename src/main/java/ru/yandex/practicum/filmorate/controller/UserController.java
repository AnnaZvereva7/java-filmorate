package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.managers.InMemoryUserManager;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/users")
@Component
public class UserController {
    InMemoryUserManager userManager;

    public UserController(InMemoryUserManager userManager) {
        this.userManager=userManager;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userManager.get();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return userManager.add(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userManager.update(user);
    }

}
