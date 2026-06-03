/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Kamar;
import config.DatabaseConnection;
import java.sql.*;

public class KamarDAO extends BaseDAO<Kamar> {

    @Override
    protected String getTableName() {
        return "kamar";
    }

    @Override
    protected Kamar mapResultSet(ResultSet rs) throws SQLException {
        Kamar kamar = new Kamar();
        kamar.setId(rs.getInt("id"));
        kamar.setNomorKamar(rs.getString("nomor_kamar"));
        kamar.setTipe(rs.getString("tipe"));
        kamar.setHargaPerMalam(rs.getBigDecimal("harga_per_malam"));
        kamar.setStatus(rs.getString("status"));
        kamar.setFasilitas(rs.getString("fasilitas"));
        return kamar;
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO kamar (nomor_kamar, tipe, harga_per_malam, status, fasilitas) VALUES (?,?,?,?,?)";
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, Kamar kamar) throws SQLException {
        stmt.setString(1, kamar.getNomorKamar());
        stmt.setString(2, kamar.getTipe());
        stmt.setBigDecimal(3, kamar.getHargaPerMalam());
        stmt.setString(4, kamar.getStatus());
        stmt.setString(5, kamar.getFasilitas());
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE kamar SET nomor_kamar=?, tipe=?, harga_per_malam=?, status=?, fasilitas=? WHERE id=?";
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, Kamar kamar) throws SQLException {
        stmt.setString(1, kamar.getNomorKamar());
        stmt.setString(2, kamar.getTipe());
        stmt.setBigDecimal(3, kamar.getHargaPerMalam());
        stmt.setString(4, kamar.getStatus());
        stmt.setString(5, kamar.getFasilitas());
        stmt.setInt(6, kamar.getId());
    }

    // Method khusus (tidak di-generic)
    public boolean updateKamarStatus(int idKamar, String status) {
        String sql = "UPDATE kamar SET status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, idKamar);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update kamar status: " + e.getMessage());
            return false;
        }
    }

    public boolean hasActiveReservasi(int idKamar) {
        String sql = "SELECT COUNT(*) as count FROM reservasi WHERE id_kamar=? AND status='Check-In'";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idKamar);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking active reservasi: " + e.getMessage());
        }
        return false;
    }

    public boolean canChangeKamarStatus(int idKamar) {
        String sql = "SELECT status FROM kamar WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idKamar);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("status");
                    if ("Terisi".equalsIgnoreCase(status)) {
                        return !hasActiveReservasi(idKamar);
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error validating kamar status change: " + e.getMessage());
        }
        return false;
    }
}
