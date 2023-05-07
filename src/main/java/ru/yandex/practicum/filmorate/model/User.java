package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class User implements Comparable<User> {
    @With
    private final int id;
    @Email(message = "Не корректный email")
    @NotEmpty
    private final String email;
    @Pattern(regexp = "[^\\s]*", message = "Не корректный логин")
    @NotEmpty(message = "Не корректный логин")
    private final String login;
    @With
    private final String name;
    @PastOrPresent(message = "Не корректная дата рождения")
    @NotNull(message = "Не корректная дата рождения")
    private final LocalDate birthday;
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private final Set<Integer> friendsId = new HashSet<>();

    public int compareTo(User user) {
        return user.name.compareTo(this.name);
    }

}
