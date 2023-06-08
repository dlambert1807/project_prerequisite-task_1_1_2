package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory factory;

    public UserDaoHibernateImpl() {
        factory = Util.factoryBuild();
    }

    @Override
    public void createUsersTable() {
        try(Session session = factory.getCurrentSession()) {
            String sql = "CREATE TABLE IF NOT EXISTS USERS" +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " name VARCHAR(255), " +
                    " lastName VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }  catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = factory.getCurrentSession()) {
            String sql = "DROP TABLE IF EXISTS USERS";
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }  catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = factory.getCurrentSession()) {
            Transaction tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
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
            session.createNativeQuery("DELETE FROM USERS WHERE ID = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            tx.commit();
        }  catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        try(Session session = factory.getCurrentSession()) {
            Transaction tx = session.beginTransaction();
            List<User> userList = session.createQuery("FROM User", User.class).getResultList();
            tx.commit();
            return userList;
        }
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
