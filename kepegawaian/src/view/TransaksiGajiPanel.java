package view;

import dao.PegawaiDAO;
import dao.TransaksiGajiDAO;
import model.Pegawai;
import model.TransaksiGaji;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TransaksiGajiPanel extends JPanel {
    private JComboBox<String> cbPegawai;
    private JTextField txtGajiPokok, txtTunjangan;
    private JTable tabelTransaksi;
    private JButton btnTambah, btnHapus;
    private PegawaiDAO pegawaiDAO;
    private TransaksiGajiDAO transaksiGajiDAO;

    public TransaksiGajiPanel() {
        pegawaiDAO = new PegawaiDAO();
        transaksiGajiDAO = new TransaksiGajiDAO();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel Input
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        
        // Combo Pegawai
        inputPanel.add(new JLabel("Pegawai:"));
        cbPegawai = new JComboBox<>();
        muatDataPegawai();
        inputPanel.add(cbPegawai);

        // Gaji Pokok
        inputPanel.add(new JLabel("Gaji Pokok:"));
        txtGajiPokok = new JTextField();
        inputPanel.add(txtGajiPokok);

        // Tunjangan
        inputPanel.add(new JLabel("Tunjangan:"));
        txtTunjangan = new JTextField();
        inputPanel.add(txtTunjangan);

        // Tombol Aksi
        JPanel buttonPanel = new JPanel();
        btnTambah = new JButton("Tambah Transaksi");
        btnHapus = new JButton("Hapus Transaksi");
        buttonPanel.add(btnTambah);
        buttonPanel.add(btnHapus);

        // Tabel Transaksi
        String[] kolom = {"ID", "Pegawai", "Tanggal", "Gaji Pokok", "Tunjangan", "Total Gaji"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0);
        tabelTransaksi = new JTable(model);

        // Layout
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(tabelTransaksi), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        btnTambah.addActionListener(e -> tambahTransaksi());
        btnHapus.addActionListener(e -> hapusTransaksi());

        cbPegawai.addActionListener(e -> muatTransaksiPegawai());
    }

    private void muatDataPegawai() {
        cbPegawai.removeAllItems();
        List<Pegawai> daftarPegawai = pegawaiDAO.getDaftarPegawai();
        for (Pegawai p : daftarPegawai) {
            cbPegawai.addItem(p.getNama() + " - " + p.getNip());
        }
    }

    private void muatTransaksiPegawai() {
        if (cbPegawai.getSelectedItem() == null) return;

        String selectedPegawai = cbPegawai.getSelectedItem().toString();
        String nip = selectedPegawai.split(" - ")[1];

        List<Pegawai> daftarPegawai = pegawaiDAO.getDaftarPegawai();
        Pegawai pegawai = daftarPegawai.stream()
            .filter(p -> p.getNip().equals(nip))
            .findFirst()
            .orElse(null);

        if (pegawai != null) {
            DefaultTableModel model = (DefaultTableModel) tabelTransaksi.getModel();
            model.setRowCount(0);

            List<TransaksiGaji> daftarTransaksi = transaksiGajiDAO.getDaftarTransaksiGaji(pegawai.getIdPegawai());
            for (TransaksiGaji t : daftarTransaksi) {
                model.addRow(new Object[]{
                    t.getIdTransaksi(),
                    pegawai.getNama(),
                    t.getTanggal(),
                    t.getGajiPokok(),
                    t.getTunjangan(),
                    t.getTotalGaji()
                });
            }
        }
    }

    private void tambahTransaksi() {
        try {
            String selectedPegawai = cbPegawai.getSelectedItem().toString();
            String nip = selectedPegawai.split(" - ")[1];

            List<Pegawai> daftarPegawai = pegawaiDAO.getDaftarPegawai();
            Pegawai pegawai = daftarPegawai.stream()
                .filter(p -> p.getNip().equals(nip))
                .findFirst()
                .orElse(null);

            if (pegawai != null) {
                BigDecimal gajiPokok = new BigDecimal(txtGajiPokok.getText());
                BigDecimal tunjangan = new BigDecimal(txtTunjangan.getText());

                TransaksiGaji transaksi = new TransaksiGaji(
                    pegawai.getIdPegawai(), 
                    LocalDate.now(), 
                    gajiPokok, 
                    tunjangan
                );

                transaksiGajiDAO.tambahTransaksiGaji(transaksi);
                muatTransaksiPegawai();
                resetForm();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input gaji harus angka!");
        }
    }

    private void hapusTransaksi() {
        int selectedRow = tabelTransaksi.getSelectedRow();
        if (selectedRow != -1) {
            int idTransaksi = (int) tabelTransaksi.getValueAt(selectedRow, 0);
            
            int konfirmasi = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin menghapus transaksi ini?", 
                "Konfirmasi Hapus", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (konfirmasi == JOptionPane.YES_OPTION) {
                transaksiGajiDAO.hapusTransaksiGaji(idTransaksi);
                muatTransaksiPegawai();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih transaksi yang akan dihapus");
        }
    }

    private void resetForm() {
        txtGajiPokok.setText("");
        txtTunjangan.setText("");
    }
}