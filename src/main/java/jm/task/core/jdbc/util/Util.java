package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    private static final String URL = "jdbc:postgresql://localhost:5432/myBD";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "200420083anbooi";

    //JDBC
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            System.out.println("Соединение установлено :о");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка соединения с БД :(");
        }
        return connection;
    }
}