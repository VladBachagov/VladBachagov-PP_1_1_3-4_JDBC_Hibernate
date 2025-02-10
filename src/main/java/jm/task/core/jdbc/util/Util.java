package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

  private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
  private static final String USERNAME = "vladroot";
  private static final String PASSWORD = "vladroot";

  static {
    try {
      Class.forName(DB_DRIVER);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Ошибка загрузки JDBC-драйвера!", e);
    }
  }

  public static Connection getConnection() {
    try {
      Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      System.out.println("Connection OK");
      return connection;
    } catch (SQLException e) {
      System.err.println("Ошибка подключения к БД: " + e.getMessage());
      throw new RuntimeException("Не удалось установить соединение с БД", e);
    }
  }
}