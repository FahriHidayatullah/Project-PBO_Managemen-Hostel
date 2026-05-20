package View;

import Controller.LoginController;
import Model.User;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JTextField txtNama;
    private JButton btnAction;
    private JButton btnToggleMode;
    private JLabel lblError;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JLabel lblNama;
    private boolean registerMode = false;
    private LoginController controller;

    public LoginView() {
        controller = new LoginController();

        setTitle("Amarta Jiwa Hostel Management System");
        setSize(450, 580);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        // Hapus setUndecorated(true) agar jendela normal dengan border

        // Main Panel dengan gradient
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(62, 35, 18), 0, h, new Color(40, 22, 10));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title
        lblTitle = new JLabel("AMARTA JIWA MANAGEMENT SYSTEM");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(255, 215, 150));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        lblSubtitle = new JLabel("Silakan login untuk melanjutkan");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitle.setForeground(new Color(220, 180, 120));
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);

        // Fields dengan background putih
        txtUser = createStyledField();
        txtPass = createStyledPasswordField();
        txtNama = createStyledField();
        txtNama.setVisible(false);

        lblNama = new JLabel("Nama Lengkap:");
        lblNama.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblNama.setForeground(new Color(220, 180, 120));
        lblNama.setVisible(false);

        // Button Login/Register
        btnAction = createStyledButton("LOGIN", new Color(210, 140, 60), Color.WHITE);
        
        // Toggle Button
        btnToggleMode = new JButton("Belum punya akun? DAFTAR");
        btnToggleMode.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnToggleMode.setForeground(new Color(255, 200, 100));
        btnToggleMode.setContentAreaFilled(false);
        btnToggleMode.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnToggleMode.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Error Label
        lblError = new JLabel(" ");
        lblError.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblError.setForeground(new Color(255, 120, 100));
        lblError.setHorizontalAlignment(SwingConstants.CENTER);

        // Layout
        mainPanel.add(lblTitle, gbc);
        mainPanel.add(Box.createVerticalStrut(5), gbc);
        mainPanel.add(lblSubtitle, gbc);
        mainPanel.add(Box.createVerticalStrut(35), gbc);

        mainPanel.add(createLabelWithIcon("Username:"), gbc);
        mainPanel.add(txtUser, gbc);
        mainPanel.add(createLabelWithIcon("Password:"), gbc);
        mainPanel.add(txtPass, gbc);
        mainPanel.add(lblNama, gbc);
        mainPanel.add(txtNama, gbc);
        mainPanel.add(Box.createVerticalStrut(25), gbc);
        mainPanel.add(btnAction, gbc);
        mainPanel.add(Box.createVerticalStrut(12), gbc);
        mainPanel.add(btnToggleMode, gbc);
        mainPanel.add(Box.createVerticalStrut(12), gbc);
        mainPanel.add(lblError, gbc);

        add(mainPanel);

        btnAction.addActionListener(e -> handleAction());
        btnToggleMode.addActionListener(e -> toggleMode());
        txtPass.addActionListener(e -> handleAction());
    }

    private JTextField createStyledField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.BLACK);
        field.setBackground(Color.WHITE);
        field.setCaretColor(Color.BLACK);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 150, 120), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.BLACK);
        field.setBackground(Color.WHITE);
        field.setCaretColor(Color.BLACK);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 150, 120), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private JLabel createLabelWithIcon(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(220, 180, 120));
        return label;
    }

    private void toggleMode() {
        registerMode = !registerMode;
        if (registerMode) {
            lblTitle.setText("REGISTRASI AKUN");
            lblSubtitle.setText("Buat akun untuk mengakses sistem");
            btnAction.setText("REGISTER");
            btnAction.setBackground(new Color(200, 130, 50));
            btnToggleMode.setText("Sudah punya akun? LOGIN");
            lblNama.setVisible(true);
            txtNama.setVisible(true);
            lblError.setText(" ");
            clearForm();
        } else {
            lblTitle.setText("HOSTEL MANAGEMENT SYSTEM");
            lblSubtitle.setText("Silakan login untuk melanjutkan");
            btnAction.setText("LOGIN");
            btnAction.setBackground(new Color(210, 140, 60));
            btnToggleMode.setText("Belum punya akun? DAFTAR");
            lblNama.setVisible(false);
            txtNama.setVisible(false);
            lblError.setText(" ");
            clearForm();
        }
    }

    private void handleAction() {
        if (registerMode) {
            register();
        } else {
            login();
        }
    }

    private void login() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Username dan password harus diisi!");
            return;
        }

        btnAction.setText("LOADING...");
        btnAction.setEnabled(false);

        try {
            User user = controller.login(username, password);
            if (user != null) {
                new MainFrame(user).setVisible(true);
                dispose();
            } else {
                lblError.setText("Username atau password salah!");
            }
        } catch (Exception ex) {
            lblError.setText("Gagal login, cek koneksi database.");
            ex.printStackTrace();
        } finally {
            btnAction.setText("LOGIN");
            btnAction.setEnabled(true);
            txtPass.setText("");
        }
    }

    private void register() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword());
        String nama = txtNama.getText().trim();

        if (username.isEmpty() || password.isEmpty() || nama.isEmpty()) {
            lblError.setText("Semua kolom harus diisi untuk registrasi!");
            return;
        }
        if (!controller.isUsernameAvailable(username)) {
            lblError.setText("Username sudah digunakan, pilih yang lain.");
            return;
        }

        btnAction.setText("LOADING...");
        btnAction.setEnabled(false);

        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setNamaLengkap(nama);
            if (controller.register(user)) {
                JOptionPane.showMessageDialog(this, "Registrasi berhasil! Silakan login.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                toggleMode();
            } else {
                lblError.setText("Gagal registrasi. Coba lagi.");
            }
        } catch (Exception ex) {
            lblError.setText("Gagal registrasi, cek koneksi database.");
            ex.printStackTrace();
        } finally {
            btnAction.setText("REGISTER");
            btnAction.setEnabled(true);
            txtPass.setText("");
        }
    }

    private void clearForm() {
        txtUser.setText("");
        txtPass.setText("");
        txtNama.setText("");
        lblError.setText(" ");
    }
}