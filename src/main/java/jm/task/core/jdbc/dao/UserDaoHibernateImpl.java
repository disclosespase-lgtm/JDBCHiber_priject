package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import java.util.List;

@Slf4j
public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGSERIAL PRIMARY KEY," +
                "name VARCHAR(100)," +
                "last_name VARCHAR(100), " +
                "age SMALLINT) ";

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
            log.info("Таблица создана");
        } catch (HibernateException e) {
            log.error("Ошибка при создании таблицы ", e);
        }
    }


    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try(Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
            log.info("Таблица удалена");
        } catch (HibernateException e) {
            log.error("Ошибка при удалении таблицы ", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            User user = new User(name,lastName,age);
            session.save(user);
            session.getTransaction().commit();
            log.info("User с именем " + name + " добавлен в базу данных");
        }catch (HibernateException e) {
            log.error("Сохранение пользорвателя не удалось ", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            User user = session.get(User.class,id);
            if (user!=null){session.remove(user);}
            session.getTransaction().commit();
           log.info("Пользователь с id: " + id + " удален");
        } catch (HibernateException e) {
            log.error("Ошибка при удалении пользователя ", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = Util.getSessionFactory().openSession()){
            return session.createQuery("FROM User",User.class).list();
        } catch (HibernateException e) {
            log.error("Ошибка при получении всех пользователей ", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
            log.info("Все пользователи удалены из таблицы");
        }catch (HibernateException e) {
            log.error("Ошибка при очистке таблицы ", e);
        }
    }
}
