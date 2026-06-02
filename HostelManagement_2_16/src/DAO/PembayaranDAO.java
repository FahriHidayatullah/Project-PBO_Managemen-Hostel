/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Pembayaran;
import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PembayaranDAO {

    // Method untuk insert pembayaran
    public boolean insert(Pembayaran p) {
        String sql = "INSERT INTO pembayaran (id_reservasi, jumlah_bayar, metode_pembayaran, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getIdReservasi());
            stmt.setBigDecimal(2, p.getJumlahBayar());
            stmt.setString(3, p.getMetodePembayaran());
            stmt.setString(4, p.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insert pembayaran: " + e.getMessage());
            return false;
        }
    }

    // Method untuk mendapatkan semua pembayaran (INI YANG HILANG!)
    public List<Pembayaran> getAll() {
        List<Pembayaran> list = new ArrayList<>();
        String sql = "SELECT * FROM pembayaran ORDER BY tanggal_bayar DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pembayaran p = new Pembayaran();
                p.setId(rs.getInt("id"));
                p.setIdReservasi(rs.getInt("id_reservasi"));
                p.setJumlahBayar(rs.getBigDecimal("jumlah_bayar"));
                p.setMetodePembayaran(rs.getString("metode_pembayaran"));
                p.setTanggalBayar(rs.getTimestamp("tanggal_bayar"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error get all pembayaran: " + e.getMessage());
        }
        return list;
    }

    // Method untuk mendapatkan pembayaran berdasarkan ID reservasi
    public List<Pembayaran> getPembayaranByReservasi(int idReservasi) {
        List<Pembayaran> list = new ArrayList<>();
        String sql = "SELECT * FROM pembayaran WHERE id_reservasi=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReservasi);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pembayaran p = new Pembayaran();
                p.setId(rs.getInt("id"));
                p.setIdReservasi(rs.getInt("id_reservasi"));
                p.setJumlahBayar(rs.getBigDecimal("jumlah_bayar"));
                p.setMetodePembayaran(rs.getString("metode_pembayaran"));
                p.setTanggalBayar(rs.getTimestamp("tanggal_bayar"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error get pembayaran by reservasi: " + e.getMessage());
        }
        return list;
    }

    // Method untuk update status pembayaran
    public boolean updateStatusPembayaran(int id, String status) {
        String sql = "UPDATE pembayaran SET status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update status pembayaran: " + e.getMessage());
            return false;
        }
    }

    // Method baru untuk update status pembayaran dan reservasi sekaligus
    public boolean updateStatusPembayaranDanReservasi(int idPembayaran, String statusPembayaran, int idReservasi,
            String statusReservasi) {
        String sqlPembayaran = "UPDATE pembayaran SET status=? WHERE id=?";
        String sqlReservasi = "UPDATE reservasi SET status=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Update pembayaran
            try (PreparedStatement stmtPembayaran = conn.prepareStatement(sqlPembayaran)) {
                stmtPembayaran.setString(1, statusPembayaran);
                stmtPembayaran.setInt(2, idPembayaran);
                stmtPembayaran.executeUpdate();
            }

            // Update reservasi
            try (PreparedStatement stmtReservasi = conn.prepareStatement(sqlReservasi)) {
                stmtReservasi.setString(1, statusReservasi);
                stmtReservasi.setInt(2, idReservasi);
                stmtReservasi.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error update status pembayaran dan reservasi: " + e.getMessage());
            return false;
        }
    }
}