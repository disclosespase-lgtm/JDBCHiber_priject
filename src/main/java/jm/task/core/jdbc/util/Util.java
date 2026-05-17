package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

@Slf4j
public class Util {

    private static final String URL = "jdbc:postgresql://localhost:5432/myBD";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "200420083anbooi";

    //Hibernate
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration()
                        .setProperty(Environment.DRIVER, "org.postgresql.Driver")
                        .setProperty(Environment.URL, URL)
                        .setProperty(Environment.USER, USERNAME)
                        .setProperty(Environment.PASS, PASSWORD)
                        .setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect")
                        .setProperty(Environment.SHOW_SQL, "false")
                        .setProperty(Environment.HBM2DDL_AUTO, "update")
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
            } catch (RuntimeException e) {
                log.error("Ошибка подключения к БД", e);
                throw e;
            }
        }
        return sessionFactory;
    }
}