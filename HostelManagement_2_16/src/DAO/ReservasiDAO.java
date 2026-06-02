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

public class ReservasiDAO {
    public List<Reservasi> getAll() {
        List<Reservasi> list = new ArrayList<>();
        String sql = "SELECT r.*, t.nama as nama_tamu, k.nomor_kamar FROM reservasi r JOIN tamu t ON r.id_tamu=t.id JOIN kamar k ON r.id_kamar=k.id ORDER BY r.tanggal_booking DESC";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("ReservasiDAO.getAll: database connection failed");
                return list;
            }
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Reservasi r = new Reservasi();
                    r.setId(rs.getInt("id"));
                    r.setIdTamu(rs.getInt("id_tamu"));
                    r.setIdKamar(rs.getInt("id_kamar"));
                    r.setNamaTamu(rs.getString("nama_tamu"));
                    r.setNomorKamar(rs.getString("nomor_kamar"));
                    r.setTanggalCheckIn(rs.getDate("tanggal_check_in"));
                    r.setTanggalCheckOut(rs.getDate("tanggal_check_out"));
                    r.setJumlahMalam(rs.getInt("jumlah_malam"));
                    r.setTotalHarga(rs.getBigDecimal("total_harga"));
                    r.setStatus(rs.getString("status"));
                    list.add(r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Reservasi getById(int id) {
        String sql = "SELECT r.*, t.nama as nama_tamu, k.nomor_kamar FROM reservasi r JOIN tamu t ON r.id_tamu=t.id JOIN kamar k ON r.id_kamar=k.id WHERE r.id=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("ReservasiDAO.getById: database connection failed");
                return null;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Reservasi r = new Reservasi();
                        r.setId(rs.getInt("id"));
                        r.setIdTamu(rs.getInt("id_tamu"));
                        r.setIdKamar(rs.getInt("id_kamar"));
                        r.setNamaTamu(rs.getString("nama_tamu"));
                        r.setNomorKamar(rs.getString("nomor_kamar"));
                        r.setTanggalCheckIn(rs.getDate("tanggal_check_in"));
                        r.setTanggalCheckOut(rs.getDate("tanggal_check_out"));
                        r.setJumlahMalam(rs.getInt("jumlah_malam"));
                        r.setTotalHarga(rs.getBigDecimal("total_harga"));
                        r.setStatus(rs.getString("status"));
                        return r;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Reservasi r) {
        String sql = "INSERT INTO reservasi (id_tamu, id_kamar, tanggal_check_in, tanggal_check_out, jumlah_malam, total_harga, status) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("ReservasiDAO.insert: database connection failed");
                return false;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, r.getIdTamu());
                stmt.setInt(2, r.getIdKamar());
                stmt.setDate(3, r.getTanggalCheckIn());
                stmt.setDate(4, r.getTanggalCheckOut());
                stmt.setInt(5, r.getJumlahMalam());
                stmt.setBigDecimal(6, r.getTotalHarga());
                stmt.setString(7, r.getStatus());
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Reservasi r) {
        String sql = "UPDATE reservasi SET id_tamu=?, id_kamar=?, tanggal_check_in=?, tanggal_check_out=?, jumlah_malam=?, total_harga=?, status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("ReservasiDAO.update: database connection failed");
                return false;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, r.getIdTamu());
                stmt.setInt(2, r.getIdKamar());
                stmt.setDate(3, r.getTanggalCheckIn());
                stmt.setDate(4, r.getTanggalCheckOut());
                stmt.setInt(5, r.getJumlahMalam());
                stmt.setBigDecimal(6, r.getTotalHarga());
                stmt.setString(7, r.getStatus());
                stmt.setInt(8, r.getId());
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM reservasi WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("ReservasiDAO.delete: database connection failed");
                return false;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE reservasi SET status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("ReservasiDAO.updateStatus: database connection failed");
                return false;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, status);
                stmt.setInt(2, id);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatusByReservasi(int idReservasi, String status) {
        String sql = "UPDATE reservasi SET status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("ReservasiDAO.updateStatusByReservasi: database connection failed");
                return false;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, status);
                stmt.setInt(2, idReservasi);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatusReservasiDanKamar(int idReservasi, String statusReservasi, int idKamar,
            String statusKamar) {
        String sqlReservasi = "UPDATE reservasi SET status=? WHERE id=?";
        String sqlKamar = "UPDATE kamar SET status=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("ReservasiDAO.updateStatusReservasiDanKamar: database connection failed");
                return false;
            }

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

    /**
     * Get reservasi aktif (Check-In) berdasarkan ID kamar
     */
    public Reservasi getActiveReservasiByKamar(int idKamar) {
        String sql = "SELECT r.*, t.nama as nama_tamu, k.nomor_kamar FROM reservasi r " +
                    "JOIN tamu t ON r.id_tamu=t.id " +
                    "JOIN kamar k ON r.id_kamar=k.id " +
                    "WHERE r.id_kamar=? AND r.status='Check-In'";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("ReservasiDAO.getActiveReservasiByKamar: database connection failed");
                return null;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idKamar);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Reservasi r = new Reservasi();
                        r.setId(rs.getInt("id"));
                        r.setIdTamu(rs.getInt("id_tamu"));
                        r.setIdKamar(rs.getInt("id_kamar"));
                        r.setNamaTamu(rs.getString("nama_tamu"));
                        r.setNomorKamar(rs.getString("nomor_kamar"));
                        r.setTanggalCheckIn(rs.getDate("tanggal_check_in"));
                        r.setTanggalCheckOut(rs.getDate("tanggal_check_out"));
                        r.setJumlahMalam(rs.getInt("jumlah_malam"));
                        r.setTotalHarga(rs.getBigDecimal("total_harga"));
                        r.setStatus(rs.getString("status"));
                        return r;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Check apakah ada reservasi aktif (Check-In) pada kamar tertentu
     */
    public boolean hasActiveReservasi(int idKamar) {
        String sql = "SELECT COUNT(*) as count FROM reservasi WHERE id_kamar=? AND status='Check-In'";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("ReservasiDAO.hasActiveReservasi: database connection failed");
                return false;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idKamar);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("count") > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}