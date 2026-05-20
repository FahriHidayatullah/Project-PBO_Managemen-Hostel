package View;

import Controller.ReservasiController;
import Model.Reservasi;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;

public class ReservasiView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtIdTamu, txtIdKamar, txtIn, txtOut, txtTotal;
    private JComboBox<String> cbStatus;
    private ReservasiController ctrl;

    public ReservasiView() {
        ctrl = new ReservasiController();
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        setBackground(new Color(245, 235, 220));

        JLabel lblTitle = new JLabel("RESERVASI AMARTA JIWA HOSTEL");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(62, 35, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        String[] cols = {"ID", "ID Tamu", "Tamu", "ID Kamar", "Kamar", "Check-In", "Check-Out", "Malam", "Total", "Status"};
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
        
        table.getColumnModel().getColumn(1).setMinWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(0);
        table.getColumnModel().getColumn(3).setMinWidth(0);
        table.getColumnModel().getColumn(3).setMaxWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 150, 120)));

        JPanel formPanel = new JPanel(new GridLayout(4, 4, 12, 12));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 150, 120)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        txtId = new JTextField();
        txtId.setEnabled(false);
        txtIdTamu = new JTextField();
        txtIdKamar = new JTextField();
        txtIn = new JTextField();
        txtOut = new JTextField();
        txtTotal = new JTextField();
        cbStatus = new JComboBox<>(new String[]{"Booking", "Check-In", "Check-Out"});

        styleTextField(txtId);
        styleTextField(txtIdTamu);
        styleTextField(txtIdKamar);
        styleTextField(txtIn);
        styleTextField(txtOut);
        styleTextField(txtTotal);
        styleComboBox(cbStatus);

        formPanel.add(createLabel("ID:"));
        formPanel.add(txtId);
        formPanel.add(createLabel("ID Tamu:"));
        formPanel.add(txtIdTamu);
        formPanel.add(createLabel("ID Kamar:"));
        formPanel.add(txtIdKamar);
        formPanel.add(createLabel("Check-In (YYYY-MM-DD):"));
        formPanel.add(txtIn);
        formPanel.add(createLabel("Check-Out (YYYY-MM-DD):"));
        formPanel.add(txtOut);
        formPanel.add(createLabel("Total Harga:"));
        formPanel.add(txtTotal);
        formPanel.add(createLabel("Status:"));
        formPanel.add(cbStatus);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnPanel.setBackground(new Color(245, 235, 220));
        
        JButton btnAdd = createStyledButton("Booking", new Color(62, 35, 18));
        JButton btnUpdate = createStyledButton("Update", new Color(80, 55, 35));
        JButton btnDelete = createStyledButton("Hapus", new Color(180, 70, 50));
        JButton btnCheckIn = createStyledButton("Check-In", new Color(40, 100, 60));
        JButton btnCheckOut = createStyledButton("Check-Out", new Color(180, 120, 50));
        JButton btnRefresh = createStyledButton("Refresh", new Color(100, 70, 45));

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnCheckIn);
        btnPanel.add(btnCheckOut);
        btnPanel.add(btnRefresh);

        add(lblTitle, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 12));
        bottomPanel.setBackground(new Color(245, 235, 220));
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadTable());
        btnAdd.addActionListener(e -> save());
        btnUpdate.addActionListener(e -> update());
        btnDelete.addActionListener(e -> delete());
        btnCheckIn.addActionListener(e -> updateStatus("Check-In"));
        btnCheckOut.addActionListener(e -> updateStatus("Check-Out"));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtIdTamu.setText(model.getValueAt(row, 1).toString());
                txtIdKamar.setText(model.getValueAt(row, 3).toString());
                txtIn.setText(model.getValueAt(row, 5).toString());
                txtOut.setText(model.getValueAt(row, 6).toString());
                String totalStr = model.getValueAt(row, 8).toString().replace("Rp ", "").replace(".", "").trim();
                txtTotal.setText(totalStr);
                cbStatus.setSelectedItem(model.getValueAt(row, 9).toString());
            }
        });

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
        for (Reservasi r : ctrl.getAllReservasi()) {
            model.addRow(new Object[]{
                r.getId(), r.getIdTamu(), r.getNamaTamu(), r.getIdKamar(), r.getNomorKamar(),
                r.getTanggalCheckIn(), r.getTanggalCheckOut(), r.getJumlahMalam(),
                "Rp " + r.getTotalHarga(), r.getStatus()
            });
        }
    }

    private void save() {
        try {
            // Validasi form tidak kosong
            if (txtIdTamu.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Tamu harus diisi!");
                return;
            }
            if (txtIdKamar.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Kamar harus diisi!");
                return;
            }
            if (txtIn.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tanggal Check-In harus diisi!");
                return;
            }
            if (txtOut.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tanggal Check-Out harus diisi!");
                return;
            }
            if (txtTotal.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Total Harga harus diisi!");
                return;
            }
            
            Reservasi r = buildReservasiFromForm();
            r.setStatus("Booking");
            if (ctrl.addReservasi(r)) {
                JOptionPane.showMessageDialog(this, "Booking berhasil!");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan reservasi. Cek data dan koneksi.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID atau angka tidak valid! Error: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Format tanggal salah! Gunakan format YYYY-MM-DD. Error: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Data tidak valid: " + ex.getMessage());
        }
    }

    private void update() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih reservasi untuk diupdate.");
            return;
        }
        try {
            // Validasi form tidak kosong
            if (txtIdTamu.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Tamu harus diisi!");
                return;
            }
            if (txtIdKamar.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Kamar harus diisi!");
                return;
            }
            if (txtIn.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tanggal Check-In harus diisi!");
                return;
            }
            if (txtOut.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tanggal Check-Out harus diisi!");
                return;
            }
            if (txtTotal.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Total Harga harus diisi!");
                return;
            }
            
            Reservasi r = buildReservasiFromForm();
            r.setId(Integer.parseInt(txtId.getText()));
            if (ctrl.updateReservasi(r)) {
                JOptionPane.showMessageDialog(this, "Reservasi berhasil diupdate.");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal update reservasi.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID atau angka tidak valid! Error: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Format tanggal salah! Gunakan format YYYY-MM-DD. Error: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Data update tidak valid: " + ex.getMessage());
        }
    }

    private void delete() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih reservasi untuk dihapus.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus reservasi ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        if (ctrl.deleteReservasi(Integer.parseInt(txtId.getText()))) {
            JOptionPane.showMessageDialog(this, "Reservasi berhasil dihapus.");
            loadTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menghapus reservasi.");
        }
    }

    private Reservasi buildReservasiFromForm() {
        Reservasi r = new Reservasi();
        
        try {
            r.setIdTamu(Integer.parseInt(txtIdTamu.getText().trim()));
            r.setIdKamar(Integer.parseInt(txtIdKamar.getText().trim()));
            
            Date checkIn = Date.valueOf(txtIn.getText().trim());
            Date checkOut = Date.valueOf(txtOut.getText().trim());
            
            if (checkOut.before(checkIn) || checkOut.equals(checkIn)) {
                throw new IllegalArgumentException("Tanggal Check-Out harus setelah Check-In");
            }
            
            r.setTanggalCheckIn(checkIn);
            r.setTanggalCheckOut(checkOut);

            long diff = checkOut.getTime() - checkIn.getTime();
            int days = (int) (diff / (1000 * 60 * 60 * 24));
            r.setJumlahMalam(days);
            
            r.setTotalHarga(new BigDecimal(txtTotal.getText().trim()));
            r.setStatus(cbStatus.getSelectedItem().toString());
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("ID harus berupa angka: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
        
        return r;
    }

    private void updateStatus(String status) {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu!");
            return;
        }
        
        if (txtIdKamar.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data kamar tidak ditemukan!");
            return;
        }
        
        try {
            int idReservasi = Integer.parseInt(txtId.getText());
            int idKamar = Integer.parseInt(txtIdKamar.getText());
            
            // Tentukan status kamar berdasarkan status reservasi
            String statusKamar = "";
            if ("Check-In".equals(status)) {
                statusKamar = "Terisi";
            } else if ("Check-Out".equals(status)) {
                statusKamar = "Tersedia";
            } else if ("Booking".equals(status)) {
                statusKamar = "Tersedia";
            }
            
            // Update reservasi dan kamar status bersamaan
            if (ctrl.updateStatusReservasiDanKamar(idReservasi, status, idKamar, statusKamar)) {
                JOptionPane.showMessageDialog(this, "Status diubah menjadi " + status + ". Kamar sekarang: " + statusKamar);
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengubah status.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Data tidak valid!");
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtIdTamu.setText("");
        txtIdKamar.setText("");
        txtIn.setText("");
        txtOut.setText("");
        txtTotal.setText("");
        cbStatus.setSelectedIndex(0);
    }
}