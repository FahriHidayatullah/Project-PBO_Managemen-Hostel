/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.User;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainFrame(User user) {
        setTitle("Hotel Management System");
        setSize(1300, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Sidebar dengan gradient
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(45, 28, 18), 0, getHeight(), new Color(30, 18, 10));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, 0));

        // Logo
        JLabel lblLogo = new JLabel("Amarta Jiwa Hostel");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblLogo.setForeground(new Color(255, 215, 150));
        lblLogo.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // User Panel
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBackground(new Color(60, 40, 25));
        userPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        userPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblNama = new JLabel(user.getNamaLengkap());
        lblNama.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNama.setForeground(Color.WHITE);

        JLabel lblRole = new JLabel("Administrator");
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblRole.setForeground(new Color(200, 160, 110));

        userPanel.add(Box.createVerticalStrut(5));
        userPanel.add(lblNama);
        userPanel.add(lblRole);

        // Menu buttons
        JButton btnDashboard = createMenuButton("DASHBOARD");
        JButton btnKamar = createMenuButton("KAMAR");
        JButton btnTamu = createMenuButton("TAMU");
        JButton btnReservasi = createMenuButton("RESERVASI");
        JButton btnPembayaran = createMenuButton("PEMBAYARAN");
        JButton btnLaporan = createMenuButton("REKAP PENDAPATAN");

        sidebar.add(lblLogo);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(userPanel);
        sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(btnDashboard);
        sidebar.add(btnKamar);
        sidebar.add(btnTamu);
        sidebar.add(btnReservasi);
        sidebar.add(btnPembayaran);
        sidebar.add(btnLaporan);
        sidebar.add(Box.createVerticalGlue());

        JButton btnLogout = createMenuButton("LOGOUT");
        btnLogout.setBackground(new Color(180, 70, 50));
        sidebar.add(btnLogout);
        sidebar.add(Box.createVerticalStrut(20));

        // Content Panel dengan background
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(245, 235, 220));

        contentPanel.add(new DashboardView(user), "DASHBOARD");
        contentPanel.add(new KamarView(), "KAMAR");
        contentPanel.add(new TamuView(), "TAMU");
        contentPanel.add(new ReservasiView(), "RESERVASI");
        contentPanel.add(new PembayaranView(), "PEMBAYARAN");
        contentPanel.add(new LaporanView(), "LAPORAN");

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // Events
        btnDashboard.addActionListener(e -> cardLayout.show(contentPanel, "DASHBOARD"));
        btnKamar.addActionListener(e -> cardLayout.show(contentPanel, "KAMAR"));
        btnTamu.addActionListener(e -> cardLayout.show(contentPanel, "TAMU"));
        btnReservasi.addActionListener(e -> cardLayout.show(contentPanel, "RESERVASI"));
        btnPembayaran.addActionListener(e -> cardLayout.show(contentPanel, "PEMBAYARAN"));
        btnLaporan.addActionListener(e -> cardLayout.show(contentPanel, "LAPORAN"));

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginView().setVisible(true);
                dispose();
            }
        });

        cardLayout.show(contentPanel, "DASHBOARD");
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(55, 35, 22));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(250, 45));
        btn.setPreferredSize(new Dimension(250, 45));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(80, 55, 35));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(55, 35, 22));
            }
        });
        return btn;
    }
}
