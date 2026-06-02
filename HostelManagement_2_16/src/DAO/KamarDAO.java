/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Kamar;
import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KamarDAO {

    public List<Kamar> getAllKamar() {
        List<Kamar> list = new ArrayList<>();
        String sql = "SELECT * FROM kamar ORDER BY nomor_kamar";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Kamar kamar = new Kamar();
                kamar.setId(rs.getInt("id"));
                kamar.setNomorKamar(rs.getString("nomor_kamar"));
                kamar.setTipe(rs.getString("tipe"));
                kamar.setHargaPerMalam(rs.getBigDecimal("harga_per_malam"));
                kamar.setStatus(rs.getString("status"));
                kamar.setFasilitas(rs.getString("fasilitas"));
                list.add(kamar);
            }
        } catch (SQLException e) {
            System.err.println("Error get kamar: " + e.getMessage());
        }
        return list;
    }

    public boolean insertKamar(Kamar kamar) {
        String sql = "INSERT INTO kamar (nomor_kamar, tipe, harga_per_malam, status, fasilitas) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kamar.getNomorKamar());
            stmt.setString(2, kamar.getTipe());
            stmt.setBigDecimal(3, kamar.getHargaPerMalam());
            stmt.setString(4, kamar.getStatus());
            stmt.setString(5, kamar.getFasilitas());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insert kamar: " + e.getMessage());
            return false;
        }
    }

    public boolean updateKamar(Kamar kamar) {
        String sql = "UPDATE kamar SET tipe=?, harga_per_malam=?, status=?, fasilitas=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kamar.getTipe());
            stmt.setBigDecimal(2, kamar.getHargaPerMalam());
            stmt.setString(3, kamar.getStatus());
            stmt.setString(4, kamar.getFasilitas());
            stmt.setInt(5, kamar.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update kamar: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteKamar(int id) {
        String sql = "DELETE FROM kamar WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error delete kamar: " + e.getMessage());
            return false;
        }
    }

    public boolean updateKamarStatus(int idKamar, String status) {
        String sql = "UPDATE kamar SET status=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, idKamar);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update kamar status: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cek apakah ada reservasi dengan status "Check-In" pada kamar tertentu
     * Return: true jika ada reservasi aktif (Check-In), false jika tidak ada
     */
    public boolean hasActiveReservasi(int idKamar) {
        String sql = "SELECT COUNT(*) as count FROM reservasi WHERE id_kamar=? AND status='Check-In'";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

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

    /**
     * Validasi apakah kamar dapat diubah statusnya
     * Kamar tidak boleh diubah jika status = "terisi" dan ada reservasi aktif (Check-In)
     */
    public boolean canChangeKamarStatus(int idKamar) {
        String sql = "SELECT status FROM kamar WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idKamar);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("status");
                    // Jika status "terisi" cek apakah ada reservasi aktif
                    if ("terisi".equalsIgnoreCase(status)) {
                        return !hasActiveReservasi(idKamar);
                    }
                    // Status lain (tersedia, maintenance) boleh diubah
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error validating kamar status change: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get Kamar berdasarkan ID
     */
    public Kamar getKamarById(int id) {
        String sql = "SELECT * FROM kamar WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Kamar kamar = new Kamar();
                    kamar.setId(rs.getInt("id"));
                    kamar.setNomorKamar(rs.getString("nomor_kamar"));
                    kamar.setTipe(rs.getString("tipe"));
                    kamar.setHargaPerMalam(rs.getBigDecimal("harga_per_malam"));
                    kamar.setStatus(rs.getString("status"));
                    kamar.setFasilitas(rs.getString("fasilitas"));
                    return kamar;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error get kamar by id: " + e.getMessage());
        }
        return null;
    }
}