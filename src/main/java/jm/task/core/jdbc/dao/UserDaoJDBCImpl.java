package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }


    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "name  VARCHAR(100), " +
                "last_name VARCHAR(100),  " +
                "age SMALLINT)";

        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            {
                statement.executeUpdate(sql);
                log.info("Таблица создана");
            }
        } catch (SQLException e) {
            log.error("Ошибка при создании таблицы " + e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            log.info("Таблица была удалена");
        } catch (SQLException e) {
            log.error("Ошибка при удалении таблицы " + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES(?, ?, ?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, name);
            st.setString(2, lastName);
            st.setInt(3, age);
            st.executeUpdate();
            log.info("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            log.error("Сохранение пользорвателя не удалось " + e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка при удалении пользователя " + e);
        }
    }

    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        String sql = "SELECT id, name, last_name, age FROM users";

        try (var connection = Util.getConnection();
             var st = connection.createStatement();
             var resultSet = st.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));

                listUsers.add(user);
            }
        } catch (SQLException e) {
            log.error("Ошибка при получении всех пользователей " + e);
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";

        try (Connection connection = Util.getConnection();
             Statement st = connection.createStatement()) {
            st.executeUpdate(sql);
            log.info("Все пользователи удалены из таблицы");
        } catch (SQLException e) {
            log.error("Ошибка при очистке таблицы " + e);
        }
    }
}
