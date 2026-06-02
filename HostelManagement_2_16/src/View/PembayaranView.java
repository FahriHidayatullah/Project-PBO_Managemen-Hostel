package View;

import Controller.PembayaranController;
import Controller.ReservasiController;
import Model.Pembayaran;
import Model.Reservasi;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;

public class PembayaranView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtIdRes, txtJumlah;
    private JComboBox<String> cbMetode;
    private PembayaranController ctrl;
    private ReservasiController ctrlReservasi;
    
    public PembayaranView() {
        ctrl = new PembayaranController();
        ctrlReservasi = new ReservasiController();
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        setBackground(new Color(245, 235, 220));
        
        JLabel lblTitle = new JLabel("DATA PEMBAYARAN AMARTA JIWA HOSTEL");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(62, 35, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        String[] cols = {"ID", "ID Reservasi", "Jumlah", "Metode", "Tanggal", "Status"};
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
        table.setSelectionBackground(new Color(210, 180, 140));
        table.getTableHeader().setBackground(new Color(62, 35, 18));
        table.getTableHeader().setForeground(Color.DARK_GRAY);
        table.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 150, 120)));
        
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 150, 120)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        txtIdRes = new JTextField(12);
        txtJumlah = new JTextField(15);
        cbMetode = new JComboBox<>(new String[]{"Cash", "Transfer Bank", "Kartu Kredit"});
        
        styleTextField(txtIdRes);
        styleTextField(txtJumlah);
        styleComboBox(cbMetode);
        
        JButton btnBayar = createStyledButton("Proses Bayar", new Color(62, 35, 18));
        JButton btnRefresh = createStyledButton("Refresh", new Color(100, 70, 45));
        
        formPanel.add(createLabel("ID Reservasi:"));
        formPanel.add(txtIdRes);
        formPanel.add(createLabel("Jumlah:"));
        formPanel.add(txtJumlah);
        formPanel.add(createLabel("Metode:"));
        formPanel.add(cbMetode);
        formPanel.add(btnBayar);
        formPanel.add(btnRefresh);
        
        add(lblTitle, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
        
        btnRefresh.addActionListener(e -> loadTable());
        btnBayar.addActionListener(e -> processPayment());
        
        loadTable();
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(new Color(62, 35, 18));
        return label;
    }
    
    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 150, 120)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }
    
    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setBackground(Color.WHITE);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private void loadTable() {
        model.setRowCount(0);
        for (Pembayaran p : ctrl.getAllPembayaran()) {
            model.addRow(new Object[]{
                p.getId(), p.getIdReservasi(), "Rp " + p.getJumlahBayar(),
                p.getMetodePembayaran(), p.getTanggalBayar(), p.getStatus()
            });
        }
    }
    
    private void processPayment() {
        if (txtIdRes.getText().isEmpty() || txtJumlah.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID Reservasi dan Jumlah harus diisi!");
            return;
        }
        
        try {
            int idReservasi = Integer.parseInt(txtIdRes.getText());
            
            // Fetch reservasi untuk mendapatkan idKamar
            Reservasi res = ctrlReservasi.getReservasiById(idReservasi);
            if (res == null) {
                JOptionPane.showMessageDialog(this, "Reservasi tidak ditemukan!");
                return;
            }
            
            Pembayaran p = new Pembayaran();
            p.setIdReservasi(idReservasi);
            p.setJumlahBayar(new BigDecimal(txtJumlah.getText()));
            p.setMetodePembayaran(cbMetode.getSelectedItem().toString());
            p.setStatus("Lunas");
            
            if (ctrl.insertPembayaran(p)) {
                // Update status reservasi menjadi Check-Out dan kamar menjadi Tersedia ketika pembayaran lunas
                ctrlReservasi.updateStatusReservasiDanKamar(idReservasi, "Check-Out", res.getIdKamar(), "Tersedia");
                
                JOptionPane.showMessageDialog(this, "Pembayaran berhasil! Reservasi dan kamar status diperbarui.");
                loadTable();
                txtIdRes.setText("");
                txtJumlah.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Pembayaran gagal!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Format angka tidak valid!");
        }
    }
}