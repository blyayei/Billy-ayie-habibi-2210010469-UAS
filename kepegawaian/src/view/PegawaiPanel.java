package view;

import dao.PegawaiDAO;
import model.Pegawai;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PegawaiPanel extends JPanel {
    private JTable tabelPegawai;
    private JTextField txtNama, txtNIP, txtJabatan, txtEmail, txtTelepon;
    private JButton btnTambah, btnEdit, btnHapus;
    private PegawaiDAO pegawaiDAO;

    public PegawaiPanel() {
        pegawaiDAO = new PegawaiDAO();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Form Input
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.add(new JLabel("Nama:"));
        formPanel.add(txtNama = new JTextField());
        formPanel.add(new JLabel("NIP:"));
        formPanel.add(txtNIP = new JTextField());
        formPanel.add(new JLabel("Jabatan:"));
        formPanel.add(txtJabatan = new JTextField());
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail = new JTextField());
        formPanel.add(new JLabel("Telepon:"));
        formPanel.add(txtTelepon = new JTextField());

        // Tombol Aksi
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnTambah = new JButton("Tambah"));
        buttonPanel.add(btnEdit = new JButton("Edit"));
        buttonPanel.add(btnHapus = new JButton("Hapus"));

        // Tabel Pegawai
        String[] kolom = {"ID", "Nama", "NIP", "Jabatan", "Email", "Telepon"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0);
        tabelPegawai = new JTable(model);

        // Tambahkan komponen ke panel
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(tabelPegawai), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Muat data pegawai
        muatDataPegawai();

        // Event Listeners
        btnTambah.addActionListener(e -> tambahPegawai());
        btnEdit.addActionListener(e -> editPegawai());
        btnHapus.addActionListener(e -> hapusPegawai());

        // Listener untuk memilih baris di tabel
        tabelPegawai.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tabelPegawai.getSelectedRow();
                if (selectedRow != -1) {
                    txtNama.setText(tabelPegawai.getValueAt(selectedRow, 1).toString());
                    txtNIP.setText(tabelPegawai.getValueAt(selectedRow, 2).toString());
                    txtJabatan.setText(tabelPegawai.getValueAt(selectedRow, 3).toString());
                    txtEmail.setText(tabelPegawai.getValueAt(selectedRow, 4).toString());
                    txtTelepon.setText(tabelPegawai.getValueAt(selectedRow, 5).toString());
                }
            }
        });
    }

    private void muatDataPegawai() {
        DefaultTableModel model = (DefaultTableModel) tabelPegawai.getModel();
        model.setRowCount(0);
        
        List<Pegawai> daftarPegawai = pegawaiDAO.getDaftarPegawai();
        for (Pegawai p : daftarPegawai) {
            model.addRow(new Object[]{
                p.getIdPegawai(), 
                p.getNama(), 
                p.getNip(), 
                p.getJabatan(), 
                p.getEmail(), 
                p.getTelepon()
            });
        }
    }

    private void tambahPegawai() {
        Pegawai pegawai = new Pegawai(
            txtNama.getText(),
            txtNIP.getText(),
            txtJabatan.getText(),
            txtEmail.getText(),
            txtTelepon.getText()
        );
        
        pegawaiDAO.tambahPegawai(pegawai);
        muatDataPegawai();
        resetForm();
    }

    private void editPegawai() {
        int selectedRow = tabelPegawai.getSelectedRow();
        if (selectedRow != -1) {
            int idPegawai = (int) tabelPegawai.getValueAt(selectedRow, 0);
            
            Pegawai pegawai = new Pegawai(
                txtNama.getText(),
                txtNIP.getText(),
                txtJabatan.getText(),
                txtEmail.getText(),
                txtTelepon.getText()
            );
            pegawai.setIdPegawai(idPegawai);
            
            pegawaiDAO.updatePegawai(pegawai);
            muatDataPegawai();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih pegawai yang akan diedit");
        }
    }

    private void hapusPegawai() {
        int selectedRow = tabelPegawai.getSelectedRow();
        if (selectedRow != -1) {
            int idPegawai = (int) tabelPegawai.getValueAt(selectedRow, 0);
            
            int konfirmasi = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin menghapus pegawai ini?", 
                "Konfirmasi Hapus", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (konfirmasi == JOptionPane.YES_OPTION) {
                pegawaiDAO.hapusPegawai(idPegawai);
                muatDataPegawai();
                resetForm();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih pegawai yang akan dihapus");
        }
    }

    private void resetForm() {
        txtNama.setText("");
        txtNIP.setText("");
        txtJabatan.setText("");
        txtEmail.setText("");
        txtTelepon.setText("");
    }
}