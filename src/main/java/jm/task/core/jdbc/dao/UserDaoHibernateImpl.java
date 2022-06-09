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
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            String SQL = "CREATE TABLE IF NOT EXISTS pp_11_users " +
                    "(id INTEGER AUTO_INCREMENT, " +
                    " firstname VARCHAR(50) NOT NULL, " +
                    " lastname VARCHAR (50) NOT NULL, " +
                    " age INTEGER NOT NULL, " +
                    " PRIMARY KEY (id))";
            tr = session.beginTransaction();
            session.createSQLQuery(SQL).executeUpdate();
            tr.commit();
            System.out.println("Table successfully created...");
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            throw e;
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS pp_11_users").executeUpdate();
            tr.commit();
            System.out.println("Table successfully removed...");
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            throw e;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tr.commit();
            System.out.println("The user whose name - " + name + " has been added to the database.");
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            User itemFromDb = session.get(User.class, id);
            session.remove(itemFromDb);
            tr.commit();
            System.out.println("Remove record.");
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction tr = null;
        List<User> list;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            list = session.createQuery("From User").list();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            throw e;
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createQuery("delete from jm.task.core.jdbc.model.User").executeUpdate();
            tr.commit();
            System.out.println("Remove all records.");
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            throw e;
        }
    }
}
