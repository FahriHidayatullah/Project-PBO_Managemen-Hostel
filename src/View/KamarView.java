/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.KamarController;
import Model.Kamar;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;

public class KamarView extends JPanel {

    // Komponen UI
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtNomor, txtTipe, txtHarga, txtFasilitas;
    private JComboBox<String> cbStatus;
    private JButton btnAdd, btnEdit, btnDelete, btnChangeStatus, btnRefresh;
    private JLabel lblLoading;

    // Controller
    private KamarController ctrl;

    // Constructor
    public KamarView() {
        ctrl = new KamarController();
        initUI();
        loadTableAsync(); // Load dengan multithreading
    }

    /**
     * Inisialisasi semua komponen UI
     */
    private void initUI() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        setBackground(new Color(245, 235, 220));

        // ==================== HEADER / TITLE ====================
        JLabel lblTitle = new JLabel("KETERSEDIAAN KAMAR AMARTA JIWA HOSTEL");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(62, 35, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // ==================== TABLE ====================
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

        // Set lebar kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 150, 120)));

        // Loading label (awalnya hidden)
        lblLoading = new JLabel("Loading data...", SwingConstants.CENTER);
        lblLoading.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblLoading.setForeground(new Color(100, 70, 45));
        lblLoading.setVisible(false);

        // ==================== FORM PANEL ====================
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 12, 12));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 150, 120)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Inisialisasi text fields
        txtId = new JTextField();
        txtNomor = new JTextField();
        txtTipe = new JTextField();
        txtHarga = new JTextField();
        txtFasilitas = new JTextField();
        cbStatus = new JComboBox<>(new String[]{"Tersedia", "Terisi", "Maintenance"});

        // Set ID field tidak bisa diedit
        txtId.setEnabled(false);
        txtId.setBackground(new Color(240, 240, 240));

        // Styling komponen
        styleTextField(txtId);
        styleTextField(txtNomor);
        styleTextField(txtTipe);
        styleTextField(txtHarga);
        styleTextField(txtFasilitas);
        styleComboBox(cbStatus);

        // Add ke form panel
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

        // ==================== BUTTON PANEL ====================
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        btnPanel.setBackground(new Color(245, 235, 220));

        btnAdd = createStyledButton("Tambah", new Color(62, 35, 18));
        btnEdit = createStyledButton("Edit", new Color(80, 55, 35));
        btnDelete = createStyledButton("Hapus", new Color(180, 70, 50));
        btnChangeStatus = createStyledButton("Ubah Status", new Color(120, 80, 40));
        btnRefresh = createStyledButton("Refresh", new Color(100, 70, 45));

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnChangeStatus);
        btnPanel.add(btnRefresh);

        // ==================== LOADING PANEL ====================
        JPanel loadingPanel = new JPanel(new BorderLayout());
        loadingPanel.setBackground(new Color(245, 235, 220));
        loadingPanel.add(lblLoading, BorderLayout.CENTER);

        // ==================== SUSUN LAYOUT ====================
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 235, 220));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(loadingPanel, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 12));
        bottomPanel.setBackground(new Color(245, 235, 220));
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        add(lblTitle, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // ==================== EVENT HANDLER ====================
        setupEventHandlers();
    }

    /**
     * Setup semua event handler
     */
    private void setupEventHandlers() {
        // Button events
        btnRefresh.addActionListener(e -> loadTableAsync());
        btnAdd.addActionListener(e -> saveKamar());
        btnEdit.addActionListener(e -> updateKamar());
        btnDelete.addActionListener(e -> deleteKamar());
        btnChangeStatus.addActionListener(e -> changeStatus());

        // Table selection event
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtNomor.setText(model.getValueAt(row, 1).toString());
                txtTipe.setText(model.getValueAt(row, 2).toString());
                String harga = model.getValueAt(row, 3).toString()
                        .replace("Rp", "").replace(".", "").trim();
                txtHarga.setText(harga);
                cbStatus.setSelectedItem(model.getValueAt(row, 4).toString());
                txtFasilitas.setText(model.getValueAt(row, 5).toString());

                // Enable/disable buttons based on status
                String status = model.getValueAt(row, 4).toString();
                if ("Terisi".equals(status)) {
                    btnEdit.setEnabled(false);
                    btnDelete.setEnabled(false);
                } else {
                    btnEdit.setEnabled(true);
                    btnDelete.setEnabled(true);
                }
            }
        });
    }

    /**
     * Helper: membuat label dengan styling
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(new Color(62, 35, 18));
        return label;
    }

    /**
     * Helper: styling text field
     */
    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 150, 120)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }

    /**
     * Helper: styling combo box
     */
    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 150, 120)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    /**
     * Helper: membuat button dengan styling
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
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

    /**
     * LOAD TABLE DENGAN MULTITHREADING (SwingWorker) Method ini menggantikan
     * loadTable() biasa
     */
    private void loadTableAsync() {
        // Tampilkan loading indicator
        lblLoading.setVisible(true);
        btnRefresh.setEnabled(false);
        model.setRowCount(0);

        // SwingWorker untuk background thread
        SwingWorker<java.util.List<Object[]>, Void> worker = new SwingWorker<>() {

            @Override
            protected java.util.List<Object[]> doInBackground() throws Exception {
                // Proses ini berjalan di BACKGROUND THREAD (bukan EDT)
                java.util.List<Object[]> data = new java.util.ArrayList<>();

                try {
                    for (Kamar k : ctrl.getAllKamar()) {
                        Object[] row = new Object[]{
                            k.getId(),
                            k.getNomorKamar(),
                            k.getTipe(),
                            "Rp " + k.getHargaPerMalam(),
                            k.getStatus(),
                            k.getFasilitas()
                        };
                        data.add(row);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }

                return data;
            }

            @Override
            protected void done() {
                // Update UI di EDT (Event Dispatch Thread)
                try {
                    java.util.List<Object[]> data = get();
                    model.setRowCount(0);

                    for (Object[] row : data) {
                        model.addRow(row);
                    }

                    if (data.isEmpty()) {
                        model.addRow(new Object[]{"-", "Tidak ada data", "-", "-", "-", "-"});
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(KamarView.this,
                            "Gagal memuat data: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    lblLoading.setVisible(false);
                    btnRefresh.setEnabled(true);
                }
            }
        };

        // Jalankan worker
        worker.execute();
    }

    /**
     * SAVE / INSERT kamar baru
     */
    private void saveKamar() {
        // Validasi input
        if (!validateInput()) {
            return;
        }

        try {
            Kamar k = new Kamar();
            k.setNomorKamar(txtNomor.getText().trim());
            k.setTipe(txtTipe.getText().trim());
            k.setHargaPerMalam(new BigDecimal(txtHarga.getText().trim()));
            k.setStatus(cbStatus.getSelectedItem().toString());
            k.setFasilitas(txtFasilitas.getText().trim());

            // Disable button selama proses
            btnAdd.setEnabled(false);

            if (ctrl.insertKamar(k)) {
                JOptionPane.showMessageDialog(this, "Data kamar berhasil ditambahkan!");
                loadTableAsync(); // Reload dengan thread
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan data kamar!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka yang valid!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            btnAdd.setEnabled(true);
        }
    }

    /**
     * UPDATE kamar yang sudah dipilih
     */
    private void updateKamar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data kamar yang akan diupdate terlebih dahulu!");
            return;
        }

        if (!validateInput()) {
            return;
        }

        try {
            Kamar k = new Kamar();
            k.setId(Integer.parseInt(txtId.getText()));
            k.setNomorKamar(txtNomor.getText().trim());
            k.setTipe(txtTipe.getText().trim());
            k.setHargaPerMalam(new BigDecimal(txtHarga.getText().trim()));
            k.setStatus(cbStatus.getSelectedItem().toString());
            k.setFasilitas(txtFasilitas.getText().trim());

            btnEdit.setEnabled(false);

            if (ctrl.updateKamar(k)) {
                JOptionPane.showMessageDialog(this, "Data kamar berhasil diupdate!");
                loadTableAsync();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengupdate data kamar!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Format angka tidak valid!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            btnEdit.setEnabled(true);
        }
    }

    /**
     * DELETE kamar yang dipilih
     */
    private void deleteKamar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data kamar yang akan dihapus terlebih dahulu!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus kamar " + txtNomor.getText() + "?\nData yang terkait akan ikut terhapus!",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                btnDelete.setEnabled(false);

                if (ctrl.deleteKamar(Integer.parseInt(txtId.getText()))) {
                    JOptionPane.showMessageDialog(this, "Data kamar berhasil dihapus!");
                    loadTableAsync();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus data kamar!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID tidak valid!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                btnDelete.setEnabled(true);
            }
        }
    }

    /**
     * CHANGE STATUS kamar dengan validasi
     */
    private void changeStatus() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih kamar terlebih dahulu!");
            return;
        }

        try {
            int idKamar = Integer.parseInt(txtId.getText());

            // Ambil status terbaru dari database
            Kamar kamarFromDb = ctrl.getKamarById(idKamar);
            if (kamarFromDb == null) {
                JOptionPane.showMessageDialog(this, "Kamar tidak ditemukan di database.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String currentStatus = kamarFromDb.getStatus();

            // Cek apakah kamar sedang terisi dengan reservasi aktif
            if ("Terisi".equalsIgnoreCase(currentStatus) && ctrl.hasActiveReservasi(idKamar)) {
                JOptionPane.showMessageDialog(this,
                        "Kamar sedang terisi dengan reservasi aktif.\n"
                        + "Lakukan Check-Out terlebih dahulu sebelum mengubah status.",
                        "Tidak Bisa Mengubah Status",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Tentukan status baru
            String newStatus;
            if ("Terisi".equalsIgnoreCase(currentStatus)) {
                newStatus = "Tersedia";
            } else if ("Tersedia".equalsIgnoreCase(currentStatus)) {
                Object[] options = {"Maintenance", "Batal"};
                int choice = JOptionPane.showOptionDialog(this,
                        "Ubah status dari Tersedia menjadi Maintenance?",
                        "Ubah Status",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);

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

            btnChangeStatus.setEnabled(false);

            // Update status dengan validasi
            if (ctrl.updateKamarStatusWithValidation(idKamar, newStatus)) {
                JOptionPane.showMessageDialog(this,
                        "Status kamar berhasil diubah menjadi: " + newStatus);
                loadTableAsync();
                clearForm();
            } else {
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
        } finally {
            btnChangeStatus.setEnabled(true);
        }
    }

    /**
     * VALIDASI input form
     */
    private boolean validateInput() {
        if (txtNomor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nomor Kamar harus diisi!");
            txtNomor.requestFocus();
            return false;
        }

        if (txtTipe.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tipe Kamar harus diisi!");
            txtTipe.requestFocus();
            return false;
        }

        if (txtHarga.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harga per Malam harus diisi!");
            txtHarga.requestFocus();
            return false;
        }

        try {
            double harga = Double.parseDouble(txtHarga.getText().trim());
            if (harga <= 0) {
                JOptionPane.showMessageDialog(this, "Harga harus lebih dari 0!");
                txtHarga.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka yang valid!");
            txtHarga.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * CLEAR form
     */
    private void clearForm() {
        txtId.setText("");
        txtNomor.setText("");
        txtTipe.setText("");
        txtHarga.setText("");
        txtFasilitas.setText("");
        cbStatus.setSelectedIndex(0);
        txtNomor.requestFocus();

        // Reset button states
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
    }
}
