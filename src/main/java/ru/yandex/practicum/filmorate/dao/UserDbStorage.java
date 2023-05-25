package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundInDB;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("UserDbStorage")
@Repository
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getById(int id) {
        String sqlQuery = "SELECT * FROM users WHERE id = ?";
        User user = jdbcTemplate.queryForObject(sqlQuery, this::mapUserWithoutFriends, id);
        return addUsersFriendsList(user, id);
    }

    private User mapUserWithoutFriends(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("user_name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }

    private User addUsersFriendsList(User user, int id) {
        SqlRowSet userFriends = jdbcTemplate
                .queryForRowSet("SELECT user_id AS id FROM friendship " +
                        "WHERE friend_id = ? AND friendship_status='CONFIRMED' " +
                        "UNION SELECT friend_id FROM friendship " +
                        "WHERE user_id = ?", id, id); // AND friendship_status='CONFIRMED'", id, id);
        while (userFriends.next()) {
            user.getFriendsId().add(userFriends.getInt("id"));
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        String sqlQuery = "SELECT * FROM users ";
        return jdbcTemplate.query(sqlQuery, this::mapUserWithoutFriends);
    }

    @Override
    public User add(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", user.getEmail())
                .addValue("login", user.getLogin())
                .addValue("user_name", user.getName())
                .addValue("birthday", user.getBirthday());
        int id = simpleJdbcInsert.executeAndReturnKey(params).intValue();
        return user.withId(id);
    }

    @Override
    public User update(User user) {
        int result = jdbcTemplate.update("UPDATE users SET email =?, login=?, user_name=?, birthday=? WHERE id=?",
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        if (result > 0) {
            return user;
        } else {
            throw new NotFoundInDB("Пользователь не обновлен");
        }
    }

    @Override
    public void saveFriendship(int id, int friendId) {
        int result;
        SqlRowSet friendship = jdbcTemplate.queryForRowSet("SELECT * FROM friendship "
                + "WHERE user_id IN (?, ?) AND friend_id IN (?,?)", id, friendId, id, friendId);
        if (!friendship.next()) {
            result = jdbcTemplate.update("INSERT INTO friendship (user_id, friend_id, friendship_status) "
                    + "VALUES (?,?,'FOR_CONFIRMATION')", id, friendId);
        } else {
            result = jdbcTemplate.update("UPDATE friendship SET friendship_status = 'CONFIRMED' "
                    + "WHERE user_id=? AND friend_id=?", friendId, id);
        }
        if (result <= 0) {
            throw new NotFoundInDB("Пользователь не найден");
        }
    }

    @Override
    public void removeFriendship(int id, int friendId) {
        int result = 0;
        SqlRowSet friendship = jdbcTemplate.queryForRowSet("SELECT * FROM friendship "
                + "WHERE user_id IN (?, ?) AND friend_id IN (?,?)", id, friendId, id, friendId);
        if (friendship.next()) {
            if (friendship.getString("friendship_status").equals("CONFIRMED")) {
                result = jdbcTemplate.update("UPDATE friendship "
                        + "SET user_id=?, friend_id=?, friendship_status='FOR_CONFIRMATION' "
                        + "WHERE (user_id AND friend_id) IN (?,?)", friendId, id, friendId, id);
            } else {
                result = jdbcTemplate.update("DELETE FROM friendship WHERE user_id=? and friend_id=?", id, friendId);
            }
        }
        if (result <= 0) {
            throw new NotFoundInDB("Объекты для удаления не найдены");
        }
    }
}
