/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> implements IDAO<T> {

    protected abstract String getTableName();

    protected abstract T mapResultSet(ResultSet rs) throws SQLException;

    protected abstract String getInsertQuery();

    protected abstract void setInsertParams(PreparedStatement stmt, T entity) throws SQLException;

    protected abstract String getUpdateQuery();

    protected abstract void setUpdateParams(PreparedStatement stmt, T entity) throws SQLException;

    @Override
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getAll " + getTableName() + ": " + e.getMessage());
        }
        return list;
    }

    @Override
    public T getById(int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getById " + getTableName() + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean insert(T entity) {
        String sql = getInsertQuery();
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            setInsertParams(stmt, entity);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insert " + getTableName() + ": " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(T entity) {
        String sql = getUpdateQuery();
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            setUpdateParams(stmt, entity);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update " + getTableName() + ": " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error delete " + getTableName() + ": " + e.getMessage());
            return false;
        }
    }
}
