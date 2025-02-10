package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl implements UserDao {

  public UserDaoHibernateImpl() {

  }


  @Override
  public void createUsersTable() {
    String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(255),
                lastName VARCHAR(255),
                age TINYINT
            )
        """;
    try (Session session = Util.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.createNativeQuery(sql).executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      System.err.println("Ошибка при создании таблицы: " + e.getMessage());
    }
  }

  @Override
  public void dropUsersTable() {
    String sql = "DROP TABLE IF EXISTS users";
    try (Session session = Util.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.createNativeQuery(sql).executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      System.err.println("Ошибка при удалении таблицы: " + e.getMessage());
    }
  }

  @Override
  public void saveUser(String name, String lastName, byte age) {
    try (Session session = Util.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      User user = new User(name, lastName, age);
      session.save(user);
      transaction.commit();
      System.out.println("User с именем — " + name + " добавлен в базу данных");
    } catch (Exception e) {
      System.err.println("Ошибка при добавлении пользователя: " + e.getMessage());
    }
  }

  @Override
  public void removeUserById(long id) {
    try (Session session = Util.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      User user = session.get(User.class, id);
      if (user != null) {
        session.delete(user);
      }
      transaction.commit();
    } catch (Exception e) {
      System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
    }
  }

  @Override
  public List<User> getAllUsers() {

    try (Session session = Util.getSessionFactory().openSession()) {
      return session.createQuery("FROM User", User.class).list();
    } catch (Exception e) {
      System.err.println("Ошибка при получении пользователей: " + e.getMessage());
      return List.of();
    }
  }

  @Override
  public void cleanUsersTable() {
    try (Session session = Util.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.createQuery("DELETE FROM User").executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      System.err.println("Ошибка при очистке таблицы: " + e.getMessage());
    }
  }
}
