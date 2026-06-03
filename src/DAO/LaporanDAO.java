/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import config.DatabaseConnection;
import Model.Reservasi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaporanDAO {

    public List<Reservasi> getLaporanBulanan(String bulan, String tahun) {
        List<Reservasi> list = new ArrayList<>();
        String query = "SELECT r.*, t.nama as nama_tamu, k.nomor_kamar "
                + "FROM reservasi r "
                + "JOIN tamu t ON r.id_tamu = t.id "
                + "JOIN kamar k ON r.id_kamar = k.id "
                + "WHERE MONTH(r.tanggal_check_in) = ? AND YEAR(r.tanggal_check_in) = ? "
                + "AND r.status = 'Check-Out'";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(bulan));
            stmt.setInt(2, Integer.parseInt(tahun));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reservasi reservasi = new Reservasi();
                reservasi.setId(rs.getInt("id"));
                reservasi.setNamaTamu(rs.getString("nama_tamu"));
                reservasi.setNomorKamar(rs.getString("nomor_kamar"));
                reservasi.setTanggalCheckIn(rs.getDate("tanggal_check_in"));
                reservasi.setTanggalCheckOut(rs.getDate("tanggal_check_out"));
                reservasi.setTotalHarga(rs.getBigDecimal("total_harga"));
                list.add(reservasi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public double getTotalPendapatan() {
        String query = "SELECT SUM(total_harga) as total FROM reservasi WHERE status = 'Check-Out'";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Reservasi> getLaporanPendapatan() {
        return getLaporanBulanan(String.valueOf(java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1),
                String.valueOf(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)));
    }
}
