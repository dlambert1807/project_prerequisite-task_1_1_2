package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    static final String QUERY = "SELECT id, name, lastName, age FROM Users";
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try(Connection conn = Util.getConnection();
            Statement stmt = conn.createStatement();) {
            String sql = "CREATE TABLE IF NOT EXISTS USERS" +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " name VARCHAR(255), " +
                    " lastName VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            // System.out.println("Created table successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
            // System.out.println("Users table already exists.");
        }
    }

    public void dropUsersTable() {
        try(Connection conn = Util.getConnection();
            Statement stmt = conn.createStatement();) {
            String sql = "DROP TABLE IF EXISTS USERS";
            stmt.executeUpdate(sql);
            // System.out.println("Dropped table successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
            // System.out.println("Users table does not exists.");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection conn = Util.getConnection()) {
            // System.out.println("Inserting records...");
            String sql = "INSERT INTO USERS(name, lastName, age) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);
            pstmt.addBatch();
            pstmt.executeBatch();
            pstmt.close();
            // System.out.println("Records inserted into table.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection conn = Util.getConnection();
            Statement stmt = conn.createStatement();) {
            String sql = "DELETE FROM Users " +
                    "WHERE id = " + (id);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection conn = Util.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);) {
            while(rs.next()) {
                User nextUser = new User(rs.getString("name"), rs.getString("lastName"), (byte) rs.getInt("age"));
                nextUser.setId(rs.getLong("id"));
                users.add(nextUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Connection conn = Util.getConnection();
            Statement stmt = conn.createStatement();) {
            String sql = "DELETE FROM Users";
            stmt.executeUpdate(sql);
            // System.out.println("Table cleared.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
