package dao;

import connection.DatabaseConnection;
import model.TransaksiGaji;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransaksiGajiDAO {
    public void tambahTransaksiGaji(TransaksiGaji transaksi) {
        String sql = "INSERT INTO transaksi_gaji (id_pegawai, tanggal, gaji_pokok, tunjangan, total_gaji) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, transaksi.getIdPegawai());
            pstmt.setDate(2, Date.valueOf(transaksi.getTanggal()));
            pstmt.setBigDecimal(3, transaksi.getGajiPokok());
            pstmt.setBigDecimal(4, transaksi.getTunjangan());
            pstmt.setBigDecimal(5, transaksi.getTotalGaji());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TransaksiGaji> getDaftarTransaksiGaji(int idPegawai) {
        List<TransaksiGaji> daftarTransaksi = new ArrayList<>();
        String sql = "SELECT * FROM transaksi_gaji WHERE id_pegawai=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idPegawai);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TransaksiGaji transaksi = new TransaksiGaji();
                    transaksi.setIdTransaksi(rs.getInt("id_transaksi"));
                    transaksi.setIdPegawai(rs.getInt("id_pegawai"));
                    transaksi.setTanggal(rs.getDate("tanggal").toLocalDate());
                    transaksi.setGajiPokok(rs.getBigDecimal("gaji_pokok"));
                    transaksi.setTunjangan(rs.getBigDecimal("tunjangan"));
                    transaksi.setTotalGaji(rs.getBigDecimal("total_gaji"));
                    
                    daftarTransaksi.add(transaksi);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return daftarTransaksi;
    }

    public void hapusTransaksiGaji(int idTransaksi) {
        String sql = "DELETE FROM transaksi_gaji WHERE id_transaksi=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idTransaksi);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
