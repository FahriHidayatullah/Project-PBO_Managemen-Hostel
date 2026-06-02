package View;

import Controller.KamarController;
import Model.Kamar;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;

public class KamarView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtNomor, txtTipe, txtHarga, txtFasilitas;
    private JComboBox<String> cbStatus;
    private KamarController ctrl;
    
    public KamarView() {
        ctrl = new KamarController();
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        setBackground(new Color(245, 235, 220));
        
        // Title
        JLabel lblTitle = new JLabel("KETERSEDIAAN KAMAR AMARTA JIWA HOSTEL");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(62, 35, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Table
        String[] cols = {"ID", "No Kamar", "Tipe", "Harga/Malam", "Status", "Fasilitas"};
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
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 12, 12));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 150, 120)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        txtId = new JTextField();
        txtNomor = new JTextField();
        txtTipe = new JTextField();
        txtHarga = new JTextField();
        txtFasilitas = new JTextField();
        cbStatus = new JComboBox<>(new String[]{"Tersedia", "Terisi", "Maintenance"});
        
        styleTextField(txtId);
        styleTextField(txtNomor);
        styleTextField(txtTipe);
        styleTextField(txtHarga);
        styleTextField(txtFasilitas);
        styleComboBox(cbStatus);
        
        formPanel.add(createLabel("ID:"));
        formPanel.add(txtId);
        formPanel.add(createLabel("No Kamar:"));
        formPanel.add(txtNomor);
        formPanel.add(createLabel("Tipe:"));
        formPanel.add(txtTipe);
        formPanel.add(createLabel("Harga/Malam:"));
        formPanel.add(txtHarga);
        formPanel.add(createLabel("Status:"));
        formPanel.add(cbStatus);
        formPanel.add(createLabel("Fasilitas:"));
        formPanel.add(txtFasilitas);
        
        // Button Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        btnPanel.setBackground(new Color(245, 235, 220));
        
        JButton btnAdd = createStyledButton("Tambah", new Color(62, 35, 18));
        JButton btnEdit = createStyledButton("Edit", new Color(80, 55, 35));
        JButton btnDelete = createStyledButton("Hapus", new Color(180, 70, 50));
        JButton btnChangeStatus = createStyledButton("Ubah Status", new Color(120, 80, 40));
        JButton btnRefresh = createStyledButton("Refresh", new Color(100, 70, 45));
        
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnChangeStatus);
        btnPanel.add(btnRefresh);
        
        // Layout
        add(lblTitle, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 12));
        bottomPanel.setBackground(new Color(245, 235, 220));
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Events
        btnRefresh.addActionListener(e -> loadTable());
        btnAdd.addActionListener(e -> save());
        btnEdit.addActionListener(e -> update());
        btnDelete.addActionListener(e -> delete());
        btnChangeStatus.addActionListener(e -> changeStatus());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtNomor.setText(model.getValueAt(row, 1).toString());
                txtTipe.setText(model.getValueAt(row, 2).toString());
                String harga = model.getValueAt(row, 3).toString().replace("Rp", "").replace(".", "").trim();
                txtHarga.setText(harga);
                cbStatus.setSelectedItem(model.getValueAt(row, 4).toString());
                txtFasilitas.setText(model.getValueAt(row, 5).toString());
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
        for (Kamar k : ctrl.getAllKamar()) {
            model.addRow(new Object[]{
                k.getId(), k.getNomorKamar(), k.getTipe(),
                "Rp " + k.getHargaPerMalam(), k.getStatus(), k.getFasilitas()
            });
        }
    }
    
    private void save() {
        Kamar k = new Kamar();
        k.setNomorKamar(txtNomor.getText());
        k.setTipe(txtTipe.getText());
        k.setHargaPerMalam(new BigDecimal(txtHarga.getText()));
        k.setStatus(cbStatus.getSelectedItem().toString());
        k.setFasilitas(txtFasilitas.getText());
        
        if (ctrl.insertKamar(k)) {
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan");
            loadTable();
            clearForm();
        }
    }
    
    private void update() {
        Kamar k = new Kamar();
        k.setId(Integer.parseInt(txtId.getText()));
        k.setNomorKamar(txtNomor.getText());
        k.setTipe(txtTipe.getText());
        k.setHargaPerMalam(new BigDecimal(txtHarga.getText()));
        k.setStatus(cbStatus.getSelectedItem().toString());
        k.setFasilitas(txtFasilitas.getText());
        
        if (ctrl.updateKamar(k)) {
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate");
            loadTable();
            clearForm();
        }
    }
    
    private void delete() {
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (ctrl.deleteKamar(Integer.parseInt(txtId.getText()))) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                loadTable();
                clearForm();
            }
        }
    }

    /**
     * Ubah status kamar secara manual (Tersedia <-> Maintenance)
     * Validasi: Kamar yang status "Terisi" tidak bisa diubah sampai di-check-out
     */
    private void changeStatus() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih kamar terlebih dahulu!");
            return;
        }

        try {
            int idKamar = Integer.parseInt(txtId.getText());
            // Ambil status terbaru dari database untuk menghindari kondisi UI tidak sinkron
            Model.Kamar kamarFromDb = ctrl.getKamarById(idKamar);
            if (kamarFromDb == null) {
                JOptionPane.showMessageDialog(this, "Kamar tidak ditemukan di database.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String currentStatus = kamarFromDb.getStatus();

            // Cek apakah kamar sedang terisi dengan reservasi aktif
            if ("Terisi".equalsIgnoreCase(currentStatus) && ctrl.hasActiveReservasi(idKamar)) {
                JOptionPane.showMessageDialog(this, 
                    "Kamar sedang terisi dengan reservasi aktif.\n" +
                    "Lakukan Check-Out terlebih dahulu sebelum mengubah status.", 
                    "Tidak Bisa Mengubah Status", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Jika status "Terisi" tapi tidak ada reservasi aktif, bisa diubah
            String newStatus;
            if ("Terisi".equalsIgnoreCase(currentStatus)) {
                newStatus = "Tersedia";
            } else if ("Tersedia".equalsIgnoreCase(currentStatus)) {
                // Tanyakan user apakah ingin ubah ke Maintenance atau status lain
                Object[] options = {"Maintenance", "Batal"};
                int choice = JOptionPane.showOptionDialog(this,
                    "Ubah status dari Tersedia menjadi Maintenance?",
                    "Ubah Status", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                
                if (choice == 0) {
                    newStatus = "Maintenance";
                } else {
                    return;
                }
            } else if ("Maintenance".equalsIgnoreCase(currentStatus)) {
                newStatus = "Tersedia";
            } else {
                newStatus = currentStatus;
            }

            // Update status dengan validasi
            if (ctrl.updateKamarStatusWithValidation(idKamar, newStatus)) {
                JOptionPane.showMessageDialog(this, 
                    "Status kamar berhasil diubah menjadi: " + newStatus);
                loadTable();
                clearForm();
            } else {
                // Coba identifikasi penyebab umum kegagalan
                if (ctrl.hasActiveReservasi(idKamar)) {
                    JOptionPane.showMessageDialog(this,
                        "Gagal mengubah status karena terdapat reservasi aktif pada kamar ini.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Gagal mengubah status. Periksa log konsol untuk detail lebih lanjut.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: ID kamar tidak valid!");
        }
    }
    
    private void clearForm() {
        txtId.setText("");
        txtNomor.setText("");
        txtTipe.setText("");
        txtHarga.setText("");
        txtFasilitas.setText("");
        cbStatus.setSelectedIndex(0);
    }
}