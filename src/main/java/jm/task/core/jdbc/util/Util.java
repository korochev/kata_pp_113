package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/javakata";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {

            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    }

}
