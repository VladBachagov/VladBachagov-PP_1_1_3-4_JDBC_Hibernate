package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Util {

  private static final SessionFactory sessionFactory = buildSessionFactory();

  private static SessionFactory buildSessionFactory() {
    try {
      Configuration configuration = new Configuration();
      configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
      configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mydbtest");
      configuration.setProperty("hibernate.connection.username", "vladroot");
      configuration.setProperty("hibernate.connection.password", "vladroot");
      configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
      configuration.setProperty("hibernate.show_sql", "true");
      configuration.setProperty("hibernate.hbm2ddl.auto", "none");
      configuration.addAnnotatedClass(User.class);

      ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
          .applySettings(configuration.getProperties()).build();

      return configuration.buildSessionFactory(serviceRegistry);
    } catch (Exception e) {
      System.err.println("Ошибка при создании SessionFactory: " + e.getMessage());
      throw new ExceptionInInitializerError(e);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }
}