package jm.task.core.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jm.task.core.jdbc.model.User;

import java.util.List;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {

  public UserDaoJDBCImpl() {

  }

  @Override
  public void createUsersTable() {
    String sql = "CREATE TABLE IF NOT EXISTS users (" +
        "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
        "name VARCHAR(255), " +
        "lastName VARCHAR(255), " +
        "age TINYINT)";
    try (Connection connection = Util.getConnection();
        Statement statement = connection.createStatement()) {
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      System.err.println("Ошибка при создании таблицы: " + e.getMessage());
    }
  }

  @Override
  public void dropUsersTable() {
    String sql = "DROP TABLE IF EXISTS users";
    try (Connection connection = Util.getConnection();
        Statement statement = connection.createStatement()) {
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      System.err.println("Ошибка при удалении таблицы: " + e.getMessage());
    }
  }

  @Override
  public void saveUser(String name, String lastName, byte age) {
    String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
    try (Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, name);
      preparedStatement.setString(2, lastName);
      preparedStatement.setByte(3, age);
      preparedStatement.executeUpdate();
      System.out.println("User с именем — " + name + " добавлен в базу данных");
    } catch (SQLException e) {
      System.err.println("Ошибка при добавлении пользователя: " + e.getMessage());
    }
  }

  @Override
  public void removeUserById(long id) {
    String sql = "DELETE FROM users WHERE id = ?";
    try (Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setLong(1, id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
    }
  }

  @Override
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM users";
    try (Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      while (resultSet.next()) {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setLastName(resultSet.getString("lastName"));
        user.setAge(resultSet.getByte("age"));
        users.add(user);
      }
    } catch (SQLException e) {
      System.err.println("Ошибка при получении пользователей: " + e.getMessage());
    }
    return users;
  }

  @Override
  public void cleanUsersTable() {
    String sql = "TRUNCATE TABLE users";
    try (Connection connection = Util.getConnection();
        Statement statement = connection.createStatement()) {
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      System.err.println("Ошибка при очистке таблицы: " + e.getMessage());
    }
  }
}
