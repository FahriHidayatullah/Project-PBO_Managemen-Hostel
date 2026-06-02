package DAO;

import Model.User;
import config.DatabaseConnection;
import java.sql.*;

public class UserDAO {
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("UserDAO.login: database connection failed");
                return null;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        User u = new User();
                        u.setId(rs.getInt("id"));
                        u.setUsername(rs.getString("username"));
                        u.setNamaLengkap(rs.getString("nama_lengkap"));
                        return u;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(User user) {
        String sql = "INSERT INTO users (username, password, nama_lengkap) VALUES (?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("UserDAO.register: database connection failed");
                return false;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getNamaLengkap());
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUsernameAvailable(String username) {
        String sql = "SELECT id FROM users WHERE username=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("UserDAO.isUsernameAvailable: database connection failed");
                return false;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    return !rs.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}