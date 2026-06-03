/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Reservasi;
import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservasiDAO extends BaseDAO<Reservasi> {

    @Override
    protected String getTableName() {
        return "reservasi";
    }

    @Override
    protected Reservasi mapResultSet(ResultSet rs) throws SQLException {
        Reservasi r = new Reservasi();
        r.setId(rs.getInt("id"));
        r.setIdTamu(rs.getInt("id_tamu"));
        r.setIdKamar(rs.getInt("id_kamar"));
        r.setTanggalCheckIn(rs.getDate("tanggal_check_in"));
        r.setTanggalCheckOut(rs.getDate("tanggal_check_out"));
        r.setJumlahMalam(rs.getInt("jumlah_malam"));
        r.setTotalHarga(rs.getBigDecimal("total_harga"));
        r.setStatus(rs.getString("status"));
        r.setTanggalBooking(rs.getTimestamp("tanggal_booking"));
        // Untuk join fields (optional)
        try {
            r.setNamaTamu(rs.getString("nama_tamu"));
        } catch (SQLException e) {
        }
        try {
            r.setNomorKamar(rs.getString("nomor_kamar"));
        } catch (SQLException e) {
        }
        return r;
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO reservasi (id_tamu, id_kamar, tanggal_check_in, tanggal_check_out, jumlah_malam, total_harga, status) VALUES (?,?,?,?,?,?,?)";
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, Reservasi r) throws SQLException {
        stmt.setInt(1, r.getIdTamu());
        stmt.setInt(2, r.getIdKamar());
        stmt.setDate(3, r.getTanggalCheckIn());
        stmt.setDate(4, r.getTanggalCheckOut());
        stmt.setInt(5, r.getJumlahMalam());
        stmt.setBigDecimal(6, r.getTotalHarga());
        stmt.setString(7, r.getStatus());
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE reservasi SET id_tamu=?, id_kamar=?, tanggal_check_in=?, tanggal_check_out=?, jumlah_malam=?, total_harga=?, status=? WHERE id=?";
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, Reservasi r) throws SQLException {
        stmt.setInt(1, r.getIdTamu());
        stmt.setInt(2, r.getIdKamar());
        stmt.setDate(3, r.getTanggalCheckIn());
        stmt.setDate(4, r.getTanggalCheckOut());
        stmt.setInt(5, r.getJumlahMalam());
        stmt.setBigDecimal(6, r.getTotalHarga());
        stmt.setString(7, r.getStatus());
        stmt.setInt(8, r.getId());
    }

    // Override getAll untuk join query
    @Override
    public List<Reservasi> getAll() {
        List<Reservasi> list = new ArrayList<>();
        String sql = "SELECT r.*, t.nama as nama_tamu, k.nomor_kamar FROM reservasi r "
                + "JOIN tamu t ON r.id_tamu=t.id JOIN kamar k ON r.id_kamar=k.id "
                + "ORDER BY r.tanggal_booking DESC";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Override getById untuk join query
    @Override
    public Reservasi getById(int id) {
        String sql = "SELECT r.*, t.nama as nama_tamu, k.nomor_kamar FROM reservasi r "
                + "JOIN tamu t ON r.id_tamu=t.id JOIN kamar k ON r.id_kamar=k.id WHERE r.id=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method khusus
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE reservasi SET status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatusByReservasi(int idReservasi, String status) {
        return updateStatus(idReservasi, status);
    }

    public boolean updateStatusReservasiDanKamar(int idReservasi, String statusReservasi, int idKamar, String statusKamar) {
        String sqlReservasi = "UPDATE reservasi SET status=? WHERE id=?";
        String sqlKamar = "UPDATE kamar SET status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtReservasi = conn.prepareStatement(sqlReservasi)) {
                stmtReservasi.setString(1, statusReservasi);
                stmtReservasi.setInt(2, idReservasi);
                stmtReservasi.executeUpdate();
            }
            try (PreparedStatement stmtKamar = conn.prepareStatement(sqlKamar)) {
                stmtKamar.setString(1, statusKamar);
                stmtKamar.setInt(2, idKamar);
                stmtKamar.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Reservasi getActiveReservasiByKamar(int idKamar) {
        String sql = "SELECT r.*, t.nama as nama_tamu, k.nomor_kamar FROM reservasi r "
                + "JOIN tamu t ON r.id_tamu=t.id JOIN kamar k ON r.id_kamar=k.id "
                + "WHERE r.id_kamar=? AND r.status='Check-In'";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idKamar);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean hasActiveReservasi(int idKamar) {
        String sql = "SELECT COUNT(*) as count FROM reservasi WHERE id_kamar=? AND status='Check-In'";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idKamar);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
