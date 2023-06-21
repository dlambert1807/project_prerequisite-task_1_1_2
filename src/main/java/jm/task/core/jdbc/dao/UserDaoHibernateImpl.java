package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.transaction.Transactional;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory factory;
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS USERS" +
            "(id INTEGER not NULL AUTO_INCREMENT, " +
            " name VARCHAR(255), " +
            " lastName VARCHAR(255), " +
            " age INTEGER, " +
            " PRIMARY KEY ( id ))";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS USERS";


    public UserDaoHibernateImpl() {
        factory = Util.buildFactory();
    }

    @Override
    @Transactional
    public void createUsersTable() {
        try(Session session = factory.getCurrentSession()) {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE).executeUpdate();
            tx.commit();
        }  catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void dropUsersTable() {
        try(Session session = factory.getCurrentSession()) {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery(DROP_TABLE).executeUpdate();
            tx.commit();
        }  catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = factory.getCurrentSession()) {
            Transaction tx = session.beginTransaction();
            User newUser = new User(name, lastName, age);
            session.save(newUser);
            tx.commit();
        }  catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        try(Session session = factory.getCurrentSession()) {
            Transaction tx = session.beginTransaction();
            int result = session.createQuery("DELETE User WHERE ID = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            if (result > 0) {
                System.out.println("User with ID: " + id + " deleted.");
            } else {
                System.out.println("User with ID: " + id + " does not exist.");
            }
            tx.commit();
        }  catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        List<User> userList = null;
        try(Session session = factory.getCurrentSession()) {
            Transaction tx = session.beginTransaction();
            userList = session.createQuery("FROM User", User.class).getResultList();
            tx.commit();
        }  catch (HibernateException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    @Transactional
    public void cleanUsersTable() {
        try(Session session = factory.getCurrentSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            tx.commit();
        }  catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
