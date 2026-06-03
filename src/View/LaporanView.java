/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import config.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;

public class LaporanView extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> cbBulan, cbTahun;
    private JLabel lblTotal, lblLoading;
    private JButton btnTampilkan, btnRefresh;

    public LaporanView() {
        initUI();
        loadReportAsync();
    }

    private void initUI() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        setBackground(new Color(245, 235, 220));

        // Title
        JLabel lblTitle = new JLabel("REKAPAN PENDAPATAN AMARTA JIWA HOSTEL");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(62, 35, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 150, 120)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        cbBulan = new JComboBox<>(bulan);
        cbTahun = new JComboBox<>(new String[]{"2023", "2024", "2025", "2026", "2027"});

        styleComboBox(cbBulan);
        styleComboBox(cbTahun);

        btnTampilkan = createStyledButton("Tampilkan", new Color(62, 35, 18));
        btnRefresh = createStyledButton("Refresh", new Color(100, 70, 45));

        filterPanel.add(createLabel("Bulan:"));
        filterPanel.add(cbBulan);
        filterPanel.add(createLabel("Tahun:"));
        filterPanel.add(cbTahun);
        filterPanel.add(btnTampilkan);
        filterPanel.add(btnRefresh);

        // Table
        String[] cols = {"No", "Tamu", "Kamar", "Check-In", "Check-Out", "Malam", "Total"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setRowHeight(32);
        table.setBackground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(62, 35, 18));
        table.getTableHeader().setForeground(Color.DARK_GRAY);
        table.getTableHeader().setReorderingAllowed(false);

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(60);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 150, 120)));

        // Loading label
        lblLoading = new JLabel("Loading laporan...", SwingConstants.CENTER);
        lblLoading.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblLoading.setForeground(new Color(100, 70, 45));
        lblLoading.setVisible(false);

        // Total Panel
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(new Color(245, 235, 220));
        totalPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JLabel lblTotalLabel = new JLabel("Total Pendapatan: ");
        lblTotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalLabel.setForeground(new Color(62, 35, 18));

        lblTotal = new JLabel("Rp 0");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTotal.setForeground(new Color(40, 100, 40));

        totalPanel.add(lblTotalLabel);
        totalPanel.add(lblTotal);

        // Loading panel
        JPanel loadingPanel = new JPanel(new BorderLayout());
        loadingPanel.setBackground(new Color(245, 235, 220));
        loadingPanel.add(lblLoading, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 235, 220));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(loadingPanel, BorderLayout.SOUTH);

        JPanel northPanel = new JPanel(new BorderLayout(0, 12));
        northPanel.setBackground(new Color(245, 235, 220));
        northPanel.add(lblTitle, BorderLayout.NORTH);
        northPanel.add(filterPanel, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(totalPanel, BorderLayout.SOUTH);

        // Events
        btnTampilkan.addActionListener(e -> loadReportAsync());
        btnRefresh.addActionListener(e -> loadReportAsync());

        // Set default ke bulan saat ini
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cbBulan.setSelectedIndex(cal.get(java.util.Calendar.MONTH));
        cbTahun.setSelectedItem(String.valueOf(cal.get(java.util.Calendar.YEAR)));
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(new Color(62, 35, 18));
        return label;
    }

    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 150, 120)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }

    // MULTITHREADING: Load report dengan SwingWorker
    private void loadReportAsync() {
        int bulan = cbBulan.getSelectedIndex() + 1;
        String tahun = cbTahun.getSelectedItem().toString();

        lblLoading.setVisible(true);
        lblLoading.setText("Memuat laporan untuk " + cbBulan.getSelectedItem() + " " + tahun + "...");
        btnTampilkan.setEnabled(false);
        btnRefresh.setEnabled(false);
        model.setRowCount(0);
        lblTotal.setText("Loading...");

        SwingWorker<java.util.List<Object[]>, Void> worker = new SwingWorker<>() {
            private double total = 0;

            @Override
            protected java.util.List<Object[]> doInBackground() throws Exception {
                java.util.List<Object[]> data = new java.util.ArrayList<>();

                String sql = "SELECT r.id, t.nama, k.nomor_kamar, "
                        + "r.tanggal_check_in, r.tanggal_check_out, "
                        + "r.jumlah_malam, r.total_harga "
                        + "FROM reservasi r "
                        + "JOIN tamu t ON r.id_tamu = t.id "
                        + "JOIN kamar k ON r.id_kamar = k.id "
                        + "WHERE MONTH(r.tanggal_check_out) = ? "
                        + "AND YEAR(r.tanggal_check_out) = ? "
                        + "AND r.status = 'Check-Out' "
                        + "ORDER BY r.tanggal_check_out DESC";

                try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setInt(1, bulan);
                    stmt.setInt(2, Integer.parseInt(tahun));
                    ResultSet rs = stmt.executeQuery();

                    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                    int no = 1;

                    while (rs.next()) {
                        double harga = rs.getDouble("total_harga");
                        total += harga;

                        data.add(new Object[]{
                            no++,
                            rs.getString("nama"),
                            rs.getString("nomor_kamar"),
                            rs.getDate("tanggal_check_in"),
                            rs.getDate("tanggal_check_out"),
                            rs.getInt("jumlah_malam"),
                            format.format(harga)
                        });
                    }
                }
                return data;
            }

            @Override
            protected void done() {
                try {
                    java.util.List<Object[]> data = get();
                    model.setRowCount(0);

                    if (data.isEmpty()) {
                        model.addRow(new Object[]{"-", "Tidak ada data", "-", "-", "-", "-", "-"});
                        lblTotal.setText("Rp 0");
                    } else {
                        for (Object[] row : data) {
                            model.addRow(row);
                        }
                        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                        lblTotal.setText(format.format(total));
                    }

                    int rowCount = model.getRowCount();
                    lblLoading.setText("Menampilkan " + (data.isEmpty() ? 0 : data.size()) + " data");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(LaporanView.this,
                            "Gagal memuat laporan: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    lblTotal.setText("Error");
                } finally {
                    // Hide loading after 1 second
                    Timer timer = new Timer(1000, e -> {
                        lblLoading.setVisible(false);
                    });
                    timer.setRepeats(false);
                    timer.start();

                    btnTampilkan.setEnabled(true);
                    btnRefresh.setEnabled(true);
                }
            }
        };
        worker.execute();
    }
}
