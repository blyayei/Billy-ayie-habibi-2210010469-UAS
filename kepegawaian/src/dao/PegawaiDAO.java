package dao;

import connection.DatabaseConnection;
import model.Pegawai;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PegawaiDAO {
    public void tambahPegawai(Pegawai pegawai) {
        String sql = "INSERT INTO pegawai (nama, nip, jabatan, email, telepon) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, pegawai.getNama());
            pstmt.setString(2, pegawai.getNip());
            pstmt.setString(3, pegawai.getJabatan());
            pstmt.setString(4, pegawai.getEmail());
            pstmt.setString(5, pegawai.getTelepon());
            
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pegawai.setIdPegawai(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pegawai> getDaftarPegawai() {
        List<Pegawai> daftarPegawai = new ArrayList<>();
        String sql = "SELECT * FROM pegawai";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Pegawai pegawai = new Pegawai();
                pegawai.setIdPegawai(rs.getInt("id_pegawai"));
                pegawai.setNama(rs.getString("nama"));
                pegawai.setNip(rs.getString("nip"));
                pegawai.setJabatan(rs.getString("jabatan"));
                pegawai.setEmail(rs.getString("email"));
                pegawai.setTelepon(rs.getString("telepon"));
                
                daftarPegawai.add(pegawai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return daftarPegawai;
    }

    public void updatePegawai(Pegawai pegawai) {
        String sql = "UPDATE pegawai SET nama=?, nip=?, jabatan=?, email=?, telepon=? WHERE id_pegawai=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pegawai.getNama());
            pstmt.setString(2, pegawai.getNip());
            pstmt.setString(3, pegawai.getJabatan());
            pstmt.setString(4, pegawai.getEmail());
            pstmt.setString(5, pegawai.getTelepon());
            pstmt.setInt(6, pegawai.getIdPegawai());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void hapusPegawai(int idPegawai) {
        String sql = "DELETE FROM pegawai WHERE id_pegawai=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idPegawai);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
