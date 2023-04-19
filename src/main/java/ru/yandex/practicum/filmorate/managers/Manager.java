package ru.yandex.practicum.filmorate.managers;

import java.util.Collection;

public interface Manager<T> {
    Collection<T> get();
    T add(T t);
    T update(T t);
    boolean isValid(T t);

}
