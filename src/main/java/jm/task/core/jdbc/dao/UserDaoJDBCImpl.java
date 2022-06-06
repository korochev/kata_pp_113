package jm.task.core.jdbc.dao;

//import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Connection con = Util.getConnection(); Statement statement = con.createStatement()) {
            String SQL = "CREATE TABLE IF NOT EXISTS pp_11_users " +
                    "(id INTEGER AUTO_INCREMENT, " +
                    " firstname VARCHAR(50) NOT NULL, " +
                    " lastname VARCHAR (50) NOT NULL, " +
                    " age INTEGER NOT NULL, " +
                    " PRIMARY KEY (id))";

            statement.executeUpdate(SQL);
            System.out.println("Table successfully created...");
        } catch (Exception e) {
            System.out.println("The table is already exists");
        }
    }

    public void dropUsersTable() {
        try(Connection con = Util.getConnection(); Statement statement = con.createStatement()) {
            String SQL = "DROP TABLE IF EXISTS pp_11_users";
            statement.executeUpdate(SQL);
            System.out.println("Table successfully removed...");
        } catch (Exception e) {
            System.out.println("The table has been removed");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection con = Util.getConnection(); PreparedStatement statement = con.prepareStatement("INSERT INTO " +
                "pp_11_users(firstname, lastname, age) values(?,?,?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("The user whose name - " + name + " has been added to the database.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection con = Util.getConnection(); PreparedStatement statement = con.prepareStatement("DELETE FROM " +
                "pp_11_users WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("Remove record.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection con = Util.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM pp_11_users")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                int age = resultSet.getInt(4);

                User user = new User(id, name, lastName, (byte) age);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Connection con = Util.getConnection(); Statement statement = con.createStatement()) {
            String SQL = "TRUNCATE TABLE pp_11_users";
            statement.executeUpdate(SQL);
            System.out.println("Remove all records.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
