/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.User;
import config.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;

public class DashboardView extends JPanel {

    private JLabel lblTotalKamar, lblKamarTersedia, lblTotalTamu, lblPendapatan;
    private JTable recentTable;
    private DefaultTableModel recentModel;
    private User user;

    public DashboardView(User user) {
        this.user = user;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        setBackground(new Color(245, 235, 220));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 235, 220));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel lblTitle = new JLabel("DASHBOARD AMARTA JIWA HOSTEL MANAGEMENT SYSTEM");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(62, 35, 18));

        JLabel lblWelcome = new JLabel("Selamat datang kembali, " + user.getNamaLengkap() + "!");
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblWelcome.setForeground(new Color(100, 70, 45));

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(lblWelcome, BorderLayout.EAST);

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setBackground(new Color(245, 235, 220));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 25, 0));

        lblTotalKamar = createStatCard(statsPanel, "Total Kamar", "Loading...");
        lblKamarTersedia = createStatCard(statsPanel, "Kamar Tersedia", "Loading...");
        lblTotalTamu = createStatCard(statsPanel, "Total Tamu", "Loading...");
        lblPendapatan = createStatCard(statsPanel, "Pendapatan Bulan Ini", "Loading...");

        // Recent Activities Table
        String[] cols = { "Jam", "Aktivitas", "Tamu", "Status" };
        recentModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        recentTable = new JTable(recentModel);
        recentTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        recentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        recentTable.setRowHeight(35);
        recentTable.setBackground(Color.WHITE);
        recentTable.setSelectionBackground(new Color(210, 180, 140));
        recentTable.getTableHeader().setBackground(new Color(62, 35, 18));
        recentTable.getTableHeader().setForeground(Color.DARK_GRAY);
        recentTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(recentTable);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 150, 120), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        scroll.setBackground(Color.WHITE);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(245, 235, 220));
        JLabel lblRecent = new JLabel("Aktivitas Terbaru");
        lblRecent.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblRecent.setForeground(new Color(62, 35, 18));
        lblRecent.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        tablePanel.add(lblRecent, BorderLayout.NORTH);
        tablePanel.add(scroll, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);

        // LOAD DATA WITH MULTITHREADING
        loadStatsAsync();
        loadRecentActivitiesAsync();
    }

    private JLabel createStatCard(JPanel parent, String title, String value) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 170, 140), 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTitle.setForeground(new Color(100, 70, 45));
        topPanel.add(lblTitle, BorderLayout.EAST);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblValue.setForeground(new Color(62, 35, 18));

        card.add(topPanel, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        parent.add(card);

        return lblValue;
    }

    // MULTITHREADING: Load stats dengan SwingWorker
    private void loadStatsAsync() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            private int totalKamar, kamarTersedia, totalTamu;
            private double pendapatan;

            @Override
            protected Void doInBackground() throws Exception {
                // Semua operasi database di background thread
                try (Connection conn = DatabaseConnection.getConnection()) {
                    // Total Kamar
                    PreparedStatement ps1 = conn.prepareStatement("SELECT COUNT(*) FROM kamar");
                    ResultSet rs1 = ps1.executeQuery();
                    if (rs1.next()) {
                        totalKamar = rs1.getInt(1);
                    }

                    // Kamar Tersedia
                    PreparedStatement ps2 = conn
                            .prepareStatement("SELECT COUNT(*) FROM kamar WHERE status = 'Tersedia'");
                    ResultSet rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        kamarTersedia = rs2.getInt(1);
                    }

                    // Total Tamu
                    PreparedStatement ps3 = conn.prepareStatement("SELECT COUNT(*) FROM tamu");
                    ResultSet rs3 = ps3.executeQuery();
                    if (rs3.next()) {
                        totalTamu = rs3.getInt(1);
                    }

                    // Pendapatan bulan ini
                    PreparedStatement ps4 = conn.prepareStatement(
                            "SELECT SUM(total_harga) FROM reservasi WHERE MONTH(tanggal_check_out) = MONTH(CURDATE()) AND status = 'Check-Out'");
                    ResultSet rs4 = ps4.executeQuery();
                    if (rs4.next()) {
                        pendapatan = rs4.getDouble(1);
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                // Update UI di EDT
                lblTotalKamar.setText(String.valueOf(totalKamar));
                lblKamarTersedia.setText(String.valueOf(kamarTersedia));
                lblTotalTamu.setText(String.valueOf(totalTamu));
                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                lblPendapatan.setText(format.format(pendapatan));
            }
        };
        worker.execute();
    }

    private void loadRecentActivitiesAsync() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            private java.util.List<Object[]> data = new java.util.ArrayList<>();

            @Override
            protected Void doInBackground() throws Exception {
                try (Connection conn = DatabaseConnection.getConnection();
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(
                                "SELECT DATE_FORMAT(r.tanggal_booking, '%H:%i') as waktu, "
                                        + "CONCAT('Reservasi Kamar ', k.nomor_kamar) as aktivitas, "
                                        + "t.nama as tamu, r.status as status "
                                        + "FROM reservasi r JOIN tamu t ON r.id_tamu = t.id "
                                        + "JOIN kamar k ON r.id_kamar = k.id ORDER BY r.tanggal_booking DESC LIMIT 10")) {
                    while (rs.next()) {
                        data.add(new Object[] {
                                rs.getString("waktu"),
                                rs.getString("aktivitas"),
                                rs.getString("tamu"),
                                rs.getString("status")
                        });
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                recentModel.setRowCount(0);
                for (Object[] row : data) {
                    recentModel.addRow(row);
                }
            }
        };
        worker.execute();
    }
}
