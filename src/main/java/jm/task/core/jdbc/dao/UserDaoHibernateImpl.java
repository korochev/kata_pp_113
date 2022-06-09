package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS pp_11_users " +
                "(id INTEGER AUTO_INCREMENT, " +
                " firstname VARCHAR(50) NOT NULL, " +
                " lastname VARCHAR (50) NOT NULL, " +
                " age INTEGER NOT NULL, " +
                " PRIMARY KEY (id))";
        Consumer <Session> res = s -> s.createSQLQuery(SQL).executeUpdate();
        inTransaction(res);
        System.out.println("Table successfully created...");
    }

    @Override
    public void dropUsersTable() {
        Consumer <Session> res = s -> s.createSQLQuery("DROP TABLE IF EXISTS pp_11_users").executeUpdate();
        inTransaction(res);
        System.out.println("Table successfully removed...");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Consumer <Session> res = s -> s.save(new User(name, lastName, age));
        inTransaction(res);
        System.out.println("The user whose name - " + name + " has been added to the database.");
    }

    @Override
    public void removeUserById(long id) {
        Consumer <Session> res = s -> {
            User itemFromDb = s.get(User.class, id);
            s.remove(itemFromDb);
        };
        inTransaction(res);
        System.out.println("Remove record.");
    }

    @Override
    public List<User> getAllUsers() {
        Function<Session, List<User>> res = s -> s.createQuery("From User").list();
        return inTransaction(res);
    }

    @Override
    public void cleanUsersTable() {
        Consumer <Session> res = s -> s.createQuery("delete from User").executeUpdate();
        inTransaction(res);
        System.out.println("Remove all records.");
    }

    private <R> R inTransaction(Function<Session, R> func) {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            R result = func.apply(session);
            tr.commit();
            return result;
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            throw e;
        }
    }

    private <T> void inTransaction(Consumer<Session> cons) {
        inTransaction(session -> {
            cons.accept(session);
            return null;
        });
    }

}
