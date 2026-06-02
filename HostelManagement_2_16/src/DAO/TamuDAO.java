/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Tamu;
import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TamuDAO {
    public List<Tamu> getAll() {
        List<Tamu> list = new ArrayList<>();
        String sql = "SELECT * FROM tamu ORDER BY nama";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tamu t = new Tamu();
                t.setId(rs.getInt("id"));
                t.setNama(rs.getString("nama"));
                t.setNoKtp(rs.getString("no_ktp"));
                t.setNoTelepon(rs.getString("no_telepon"));
                t.setAlamat(rs.getString("alamat"));
                t.setTanggalLahir(rs.getDate("tanggal_lahir"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Tamu t) {
        String sql = "INSERT INTO tamu (nama, no_ktp, no_telepon, alamat, tanggal_lahir) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, t.getNama());
            stmt.setString(2, t.getNoKtp());
            stmt.setString(3, t.getNoTelepon());
            stmt.setString(4, t.getAlamat());
            stmt.setDate(5, t.getTanggalLahir());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Tamu t) {
        String sql = "UPDATE tamu SET nama=?, no_ktp=?, no_telepon=?, alamat=?, tanggal_lahir=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, t.getNama());
            stmt.setString(2, t.getNoKtp());
            stmt.setString(3, t.getNoTelepon());
            stmt.setString(4, t.getAlamat());
            stmt.setDate(5, t.getTanggalLahir());
            stmt.setInt(6, t.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM tamu WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}