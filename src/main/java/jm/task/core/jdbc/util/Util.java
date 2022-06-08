package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private static SessionFactory sessionFactory;
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/javakata";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {

        Class.forName(JDBC_DRIVER);
        return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration();
                config.setProperty("hibernate.connection.driver_class", JDBC_DRIVER);
                config.setProperty("hibernate.connection.url", DATABASE_URL);
                config.setProperty("hibernate.connection.username", USER);
                config.setProperty("hibernate.connection.password", PASSWORD);
                config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                config.addAnnotatedClass(User.class);
                sessionFactory = config.buildSessionFactory();
            } catch (Exception e) {
                System.out.println("Failed to establish connection to the database;");
            }
        }
        return sessionFactory;
    }

}
