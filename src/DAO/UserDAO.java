/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.User;
import config.DatabaseConnection;
import java.sql.*;

public class UserDAO extends BaseDAO<User> {

    @Override
    protected String getTableName() {
        return "users";
    }

    @Override
    protected User mapResultSet(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setNamaLengkap(rs.getString("nama_lengkap"));
        return u;
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO users (username, password, nama_lengkap) VALUES (?,?,?)";
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getNamaLengkap());
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE users SET username=?, password=?, nama_lengkap=? WHERE id=?";
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getNamaLengkap());
        stmt.setInt(4, user.getId());
    }

    // ========== METHOD KHUSUS ==========
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(User user) {
        // Langsung panggil method insert dari BaseDAO
        return insert(user);
    }

    public boolean isUsernameAvailable(String username) {
        String sql = "SELECT id FROM users WHERE username=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return !rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
