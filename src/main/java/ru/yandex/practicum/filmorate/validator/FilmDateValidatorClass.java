package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Slf4j
public class FilmDateValidatorClass implements ConstraintValidator<FilmDateValidator, LocalDate> {
    private static final LocalDate FIRST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext constraintValidatorContext) {
        if (releaseDate == null || releaseDate.isBefore(FIRST_RELEASE_DATE)) {
            log.debug("дата релиза — не раньше 28 декабря 1895 года");
            return false;
        }
        return true;
    }
}
