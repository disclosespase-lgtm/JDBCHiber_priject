package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "name  VARCHAR(100), "+
                "lastName VARCHAR(100),  "+
                "age SMALLINT)";

        try(Connection connection = Util.getConnection()){
            Statement statement = connection.createStatement();{
                statement.executeUpdate(sql);
                System.out.println("Таблица создана :о");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void dropUsersTable()  {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            System.out.println("Таблица была удалена -о-");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES(?, ?, ?)";

        try(Connection connection = Util.getConnection();
            PreparedStatement st = connection.prepareStatement(sql)){
            st.setString(1, name);
            st.setString(2, lastName);
            st.setInt(3, age);
            st.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id =?";

        try(Connection connection = Util.getConnection();
            PreparedStatement st = connection.prepareStatement(sql)){
            st.setLong(1,id);
            st.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try(var connection = Util.getConnection();
            var st = connection.createStatement();
            var resultSet = st.executeQuery(sql)){
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                listUsers.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";

        try(Connection connection = Util.getConnection();
            Statement st = connection.createStatement()){
            st.executeUpdate(sql);
            System.out.println("Все пользователи удалены из таблицы -_-");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
