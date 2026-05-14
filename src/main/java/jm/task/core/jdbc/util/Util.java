package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class Util {

    private static final String URL = "jdbc:postgresql://localhost:5432/myBD";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "200420083anbooi";

    //JDBC
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("Соединение установлено");
        } catch (SQLException e) {
            log.error("Ошибка соединения с БД " + e);
            throw new IllegalStateException(e);
        }
        return connection;
    }

    //Hibernate
    private static SessionFactory sessionFactory;

    static {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "org.postgresql.Driver");
                properties.put(Environment.URL, URL);
                properties.put(Environment.USER, USERNAME);
                properties.put(Environment.PASS, PASSWORD);


                properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
                properties.put(Environment.SHOW_SQL, "false");
                properties.put(Environment.HBM2DDL_AUTO, "update");

                sessionFactory = new Configuration()
                        .setProperties(properties)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}