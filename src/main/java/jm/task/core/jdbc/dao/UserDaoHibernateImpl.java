package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            String SQL = "CREATE TABLE IF NOT EXISTS pp_11_users " +
                    "(id INTEGER AUTO_INCREMENT, " +
                    " firstname VARCHAR(50) NOT NULL, " +
                    " lastname VARCHAR (50) NOT NULL, " +
                    " age INTEGER NOT NULL, " +
                    " PRIMARY KEY (id))";
            Transaction tr = session.beginTransaction();
            session.createSQLQuery(SQL).executeUpdate();
            tr.commit();
            System.out.println("Table successfully created...");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS pp_11_users").executeUpdate();
            tr.commit();
            System.out.println("Table successfully removed...");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tr.commit();
            System.out.println("The user whose name - " + name + " has been added to the database.");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            User itemFromDb = session.get(User.class, id);
            session.remove(itemFromDb);
            tr.commit();
            System.out.println("Remove record.");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return Util.getSessionFactory().openSession().createQuery("From User").list();
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from jm.task.core.jdbc.model.User").executeUpdate();
            tr.commit();
            System.out.println("Remove all records.");
        }
    }
}
