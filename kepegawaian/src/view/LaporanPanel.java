package view;

import dao.PegawaiDAO;
import dao.TransaksiGajiDAO;
import model.Pegawai;
import model.TransaksiGaji;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class LaporanPanel extends JPanel {
    private JTable tabelLaporan;
    private JButton btnCetakLaporan;
    private PegawaiDAO pegawaiDAO;
    private TransaksiGajiDAO transaksiGajiDAO;

    public LaporanPanel() {
        pegawaiDAO = new PegawaiDAO();
        transaksiGajiDAO = new TransaksiGajiDAO();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Kolom laporan
        String[] kolom = {
            "NIP", "Nama", "Jabatan",
            "Total Gaji", "Total Tunjangan",
            "Rata-rata Gaji Bulanan"
        };
        DefaultTableModel model = new DefaultTableModel(kolom, 0);
        tabelLaporan = new JTable(model);

        // Tombol Cetak
        btnCetakLaporan = new JButton("Cetak Laporan");
        btnCetakLaporan.addActionListener(e -> cetakLaporan());

        // Layout
        add(new JScrollPane(tabelLaporan), BorderLayout.CENTER);
        add(btnCetakLaporan, BorderLayout.SOUTH);

        // Muat data
        muatDataLaporan();
    }

    private void muatDataLaporan() {
        DefaultTableModel model = (DefaultTableModel) tabelLaporan.getModel();
        model.setRowCount(0); // Bersihkan tabel sebelum memuat data baru

        // Ambil daftar pegawai dari DAO
        List<Pegawai> daftarPegawai = pegawaiDAO.getDaftarPegawai();

        for (Pegawai pegawai : daftarPegawai) {
            // Ambil semua transaksi gaji berdasarkan ID pegawai
            List<TransaksiGaji> daftarTransaksi = transaksiGajiDAO.getDaftarTransaksiGaji(pegawai.getIdPegawai());

            if (!daftarTransaksi.isEmpty()) {
                // Hitung total gaji
                BigDecimal totalGaji = daftarTransaksi.stream()
                        .map(TransaksiGaji::getTotalGaji)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                // Hitung total tunjangan
                BigDecimal totalTunjangan = daftarTransaksi.stream()
                        .map(TransaksiGaji::getTunjangan)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                // Hitung rata-rata gaji bulanan
                BigDecimal rataRataGaji = totalGaji.divide(
                        BigDecimal.valueOf(daftarTransaksi.size()),
                        2,
                        RoundingMode.HALF_UP
                );

                // Tambahkan data ke tabel
                model.addRow(new Object[]{
                        pegawai.getNip(),
                        pegawai.getNama(),
                        pegawai.getJabatan(),
                        totalGaji,
                        totalTunjangan,
                        rataRataGaji
                });
            } else {
                // Jika tidak ada transaksi, tambahkan baris dengan nilai nol
                model.addRow(new Object[]{
                        pegawai.getNip(),
                        pegawai.getNama(),
                        pegawai.getJabatan(),
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                });
            }
        }
    }

    private void cetakLaporan() {
        // Implementasi cetak laporan (contoh placeholder)
        JOptionPane.showMessageDialog(this, "Fitur cetak laporan belum diimplementasikan.");
    }
}
