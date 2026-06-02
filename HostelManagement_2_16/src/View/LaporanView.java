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
    private JLabel lblTotal;
    
    public LaporanView() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        setBackground(new Color(245, 235, 220));
        
        JLabel lblTitle = new JLabel("REKAPAN PENDAPATAN AMARTA JIWA HOSTEL");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(62, 35, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Panel Filter
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 150, 120)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni",
                         "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        cbBulan = new JComboBox<>(bulan);
        cbTahun = new JComboBox<>(new String[]{"2023", "2024", "2025", "2026"});
        
        styleComboBox(cbBulan);
        styleComboBox(cbTahun);
        
        JButton btnTampilkan = createStyledButton("Tampilkan", new Color(62, 35, 18));
        JButton btnRefresh = createStyledButton("Refresh", new Color(100, 70, 45));
        
        filterPanel.add(createLabel("Bulan:"));
        filterPanel.add(cbBulan);
        filterPanel.add(createLabel("Tahun:"));
        filterPanel.add(cbTahun);
        filterPanel.add(btnTampilkan);
        filterPanel.add(btnRefresh);
        
        // Tabel
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
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 150, 120)));
        
        // Panel Total
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
        
        // Susun Layout
        JPanel northPanel = new JPanel(new BorderLayout(0, 12));
        northPanel.setBackground(new Color(245, 235, 220));
        northPanel.add(lblTitle, BorderLayout.NORTH);
        northPanel.add(filterPanel, BorderLayout.CENTER);
        
        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(totalPanel, BorderLayout.SOUTH);
        
        // Event
        btnTampilkan.addActionListener(e -> loadReport());
        btnRefresh.addActionListener(e -> loadReport());
        
        // Set default ke bulan saat ini
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cbBulan.setSelectedIndex(cal.get(java.util.Calendar.MONTH));
        cbTahun.setSelectedItem(String.valueOf(cal.get(java.util.Calendar.YEAR)));
        
        loadReport();
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
        return btn;
    }
    
    private void loadReport() {
        int bulan = cbBulan.getSelectedIndex() + 1;
        String tahun = cbTahun.getSelectedItem().toString();
        
        model.setRowCount(0);
        double total = 0;
        int no = 1;
        
        String sql = "SELECT r.id, t.nama, k.nomor_kamar, " +
                     "r.tanggal_check_in, r.tanggal_check_out, " +
                     "r.jumlah_malam, r.total_harga " +
                     "FROM reservasi r " +
                     "JOIN tamu t ON r.id_tamu = t.id " +
                     "JOIN kamar k ON r.id_kamar = k.id " +
                     "WHERE MONTH(r.tanggal_check_out) = ? " +
                     "AND YEAR(r.tanggal_check_out) = ? " +
                     "AND r.status = 'Check-Out' " +
                     "ORDER BY r.tanggal_check_out DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bulan);
            stmt.setInt(2, Integer.parseInt(tahun));
            ResultSet rs = stmt.executeQuery();
            
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            
            while (rs.next()) {
                double harga = rs.getDouble("total_harga");
                total += harga;
                
                model.addRow(new Object[]{
                    no++,
                    rs.getString("nama"),
                    rs.getString("nomor_kamar"),
                    rs.getDate("tanggal_check_in"),
                    rs.getDate("tanggal_check_out"),
                    rs.getInt("jumlah_malam"),
                    format.format(harga)
                });
            }
            
            if (model.getRowCount() == 0) {
                model.addRow(new Object[]{"-", "Tidak ada data", "-", "-", "-", "-", "-"});
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        lblTotal.setText(format.format(total));
    }
}