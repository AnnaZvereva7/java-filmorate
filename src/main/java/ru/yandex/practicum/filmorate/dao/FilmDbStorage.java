package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundInDB;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("FilmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film getById(int id) {
        String sqlQuery = "SELECT * FROM film WHERE id = ?";
        Film film = jdbcTemplate.queryForObject(sqlQuery, this::mapFilm, id);
        film = addGenresToFilm(film, id);
        return addLikesToFilm(film, id);
    }

    private Film mapFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("film_name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa(Rating.valueOf(resultSet.getString("rating_name")))
                .build();
    }


    private Film addGenresToFilm(Film film, int id) {
        SqlRowSet filmGenres = jdbcTemplate
                .queryForRowSet("SELECT DISTINCT genre_id FROM film_genre " +
                        "WHERE film_id = ?", id);
        while (filmGenres.next()) {
            film.getGenres().add(Genre.decode(filmGenres.getInt("genre_id")));
        }
        return film;
    }

    private Film addLikesToFilm(Film film, int id) {
        SqlRowSet filmLikes = jdbcTemplate
                .queryForRowSet("SELECT user_id FROM likes WHERE film_id = ?", id);
        while (filmLikes.next()) {
            film.getUsersLike().add(filmLikes.getInt("user_id"));
        }
        return film;
    }

    @Override
    public void addLike(int id, int userId) {
        SqlRowSet filmLikes = jdbcTemplate
                .queryForRowSet("SELECT like_id FROM likes WHERE film_id = ? AND user_id=?", id, userId);
        if (!filmLikes.next()) {
            int result = jdbcTemplate.update("INSERT INTO likes (film_id, user_id) VALUES (?,?)", id, userId);
            if (result <= 0) {
                throw new NotFoundInDB("Пользователь или фильм не найдены");
            }
        }
    }

    @Override
    public void deleteFilmLike(int id, int userId) {
        int result = jdbcTemplate.update("DELETE FROM likes WHERE film_id=? AND user_id=?", id, userId);
        if (result <= 0) {
            throw new NotFoundInDB("Объекты для удаления не найдены");
        }
    }

    @Override
    public List<Film> getAll() {
        String sqlQuery = "SELECT * FROM film ";
        List<Film> films = jdbcTemplate.query(sqlQuery, this::mapFilm);
        for (Film film : films) {
            film = addGenresToFilm(film, film.getId());
        }
        return films;
    }

    @Override
    public Film add(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert
                .withTableName("film")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("film_name", film.getName())
                .addValue("description", film.getDescription())
                .addValue("release_date", film.getReleaseDate())
                .addValue("duration", film.getDuration())
                .addValue("rating_name", film.getMpa() != null ? film.getMpa().toString() : "NO_RATING");
        int id = simpleJdbcInsert.executeAndReturnKey(params).intValue();
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update("INSERT INTO film_genre (film_id, genre_id) VALUES (?,?)", id, genre.getId());
        }
        return getById(id);
    }

    @Override
    public Film update(Film film) {
        int result = 0;
        if (film.getMpa() != null) {
            result = jdbcTemplate.update("UPDATE film " +
                            "SET film_name=?, description=?, release_date=?, duration=?, rating_name=? " +
                            "WHERE id=?", film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                    film.getMpa().toString(), film.getId());
        } else {
            result = jdbcTemplate.update("UPDATE film " +
                            "SET film_name=?, description=?, release_date=?, duration=? " +
                            "WHERE id=?", film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                    film.getId());
        }
        if (result > 0) {
            jdbcTemplate.update("DELETE FROM film_genre WHERE film_id=?", film.getId());
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update("INSERT INTO film_genre (film_id, genre_id) VALUES (?,?)", film.getId(), genre.getId());
            }
        } else {
            throw new NotFoundInDB("Фильм не обновлен");
        }
        return getById(film.getId());
    }

    public List<Film> getTop(int count) {
        String sqlQuery = "SELECT id, film_name, description, release_date, duration, rating_name, COUNT(like_id) " +
                "FROM film left join likes on film.id=likes.film_id " +
                "GROUP BY id, film_name, description, release_date, duration, rating_name " +
                "ORDER BY COUNT(like_id) DESC " +
                "LIMIT ?";
        List<Film> films = new ArrayList<>();
        films = jdbcTemplate.query(sqlQuery, this::mapFilm, count);
        for (Film film : films) {
            film = addGenresToFilm(film, film.getId());
        }
        return films;
    }

}
