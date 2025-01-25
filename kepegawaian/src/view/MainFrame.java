package view;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Aplikasi Kepegawaian");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Data Pegawai", new PegawaiPanel());
        tabbedPane.addTab("Transaksi Gaji", new TransaksiGajiPanel());
        tabbedPane.addTab("Laporan", new LaporanPanel());

        add(tabbedPane);
    }
}