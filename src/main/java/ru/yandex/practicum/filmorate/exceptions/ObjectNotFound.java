package ru.yandex.practicum.filmorate.exceptions;

public class ObjectNotFound extends NullPointerException {
    public ObjectNotFound(Class obj) {
        super(obj.toString() + " с таким id не найден");
    }
}
