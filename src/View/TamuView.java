package View;

import Controller.TamuController;
import Model.Tamu;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;

public class TamuView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtNama, txtKtp, txtTelp, txtAlamat, txtTglLahir;
    private TamuController ctrl;
    
    public TamuView() {
        ctrl = new TamuController();
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        setBackground(new Color(245, 235, 220));
        
        JLabel lblTitle = new JLabel("DATA TAMU AMARTA JIWA HOSTEL");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(62, 35, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        String[] cols = {"ID", "Nama", "No KTP", "Telepon", "Alamat", "Tgl Lahir"};
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
        
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 12, 12));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 150, 120)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        txtId = new JTextField();
        txtNama = new JTextField();
        txtKtp = new JTextField();
        txtTelp = new JTextField();
        txtAlamat = new JTextField();
        txtTglLahir = new JTextField();
        
        styleTextField(txtId);
        styleTextField(txtNama);
        styleTextField(txtKtp);
        styleTextField(txtTelp);
        styleTextField(txtAlamat);
        styleTextField(txtTglLahir);
        
        formPanel.add(createLabel("ID:"));
        formPanel.add(txtId);
        formPanel.add(createLabel("Nama:"));
        formPanel.add(txtNama);
        formPanel.add(createLabel("No KTP:"));
        formPanel.add(txtKtp);
        formPanel.add(createLabel("Telepon:"));
        formPanel.add(txtTelp);
        formPanel.add(createLabel("Alamat:"));
        formPanel.add(txtAlamat);
        formPanel.add(createLabel("Tgl Lahir (YYYY-MM-DD):"));
        formPanel.add(txtTglLahir);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        btnPanel.setBackground(new Color(245, 235, 220));
        
        JButton btnAdd = createStyledButton("Tambah", new Color(62, 35, 18));
        JButton btnEdit = createStyledButton("Edit", new Color(80, 55, 35));
        JButton btnDelete = createStyledButton("Hapus", new Color(180, 70, 50));
        JButton btnRefresh = createStyledButton("Refresh", new Color(100, 70, 45));
        
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
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
        btnEdit.addActionListener(e -> update());
        btnDelete.addActionListener(e -> delete());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtNama.setText(model.getValueAt(row, 1).toString());
                txtKtp.setText(model.getValueAt(row, 2).toString());
                txtTelp.setText(model.getValueAt(row, 3).toString());
                txtAlamat.setText(model.getValueAt(row, 4).toString());
                txtTglLahir.setText(model.getValueAt(row, 5).toString());
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
        for (Tamu t : ctrl.getAllTamu()) {
            model.addRow(new Object[]{t.getId(), t.getNama(), t.getNoKtp(), t.getNoTelepon(), t.getAlamat(), t.getTanggalLahir()});
        }
    }
    
    private void save() {
        Tamu t = new Tamu();
        t.setNama(txtNama.getText());
        t.setNoKtp(txtKtp.getText());
        t.setNoTelepon(txtTelp.getText());
        t.setAlamat(txtAlamat.getText());
        t.setTanggalLahir(Date.valueOf(txtTglLahir.getText()));
        
        if (ctrl.insertTamu(t)) {
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan");
            loadTable();
            clearForm();
        }
    }
    
    private void update() {
        Tamu t = new Tamu();
        t.setId(Integer.parseInt(txtId.getText()));
        t.setNama(txtNama.getText());
        t.setNoKtp(txtKtp.getText());
        t.setNoTelepon(txtTelp.getText());
        t.setAlamat(txtAlamat.getText());
        t.setTanggalLahir(Date.valueOf(txtTglLahir.getText()));
        
        if (ctrl.updateTamu(t)) {
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate");
            loadTable();
            clearForm();
        }
    }
    
    private void delete() {
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (ctrl.deleteTamu(Integer.parseInt(txtId.getText()))) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                loadTable();
                clearForm();
            }
        }
    }
    
    private void clearForm() {
        txtId.setText("");
        txtNama.setText("");
        txtKtp.setText("");
        txtTelp.setText("");
        txtAlamat.setText("");
        txtTglLahir.setText("");
    }
}