package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import jm.task.core.jdbc.model.User;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class Util {
    // set up a database connection
    private static final String DB_URL = "jdbc:mysql://localhost/TESTDB";
    private static final String USERNAME = "Guest";
    private static final String PASSWORD = "Guest123";


    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    public static SessionFactory buildFactory() {
        try {
            Properties prop = new Properties();
            prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            prop.setProperty("hibernate.connection.url", DB_URL);
            prop.setProperty("hibernate.connection.username", USERNAME);
            prop.setProperty("hibernate.connection.password", PASSWORD);
            prop.setProperty("hibernate.current_session_context_class", "thread");
            return new Configuration()
                    .addPackage("jm.task.core.jdbc")
                    .addProperties(prop)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
