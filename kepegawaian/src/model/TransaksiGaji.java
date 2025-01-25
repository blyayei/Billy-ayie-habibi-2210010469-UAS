package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransaksiGaji {
    private int idTransaksi;
    private int idPegawai;
    private LocalDate tanggal;
    private BigDecimal gajiPokok;
    private BigDecimal tunjangan;
    private BigDecimal totalGaji;

    public TransaksiGaji() {}

    public TransaksiGaji(int idPegawai, LocalDate tanggal, 
                         BigDecimal gajiPokok, BigDecimal tunjangan) {
        this.idPegawai = idPegawai;
        this.tanggal = tanggal;
        this.gajiPokok = gajiPokok;
        this.tunjangan = tunjangan;
        this.totalGaji = gajiPokok.add(tunjangan);
    }

    // Getter
    public int getIdTransaksi() { return idTransaksi; }
    public int getIdPegawai() { return idPegawai; }
    public LocalDate getTanggal() { return tanggal; }
    public BigDecimal getGajiPokok() { return gajiPokok; }
    public BigDecimal getTunjangan() { return tunjangan; }
    public BigDecimal getTotalGaji() { return totalGaji; }

    // Setter
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }
    public void setIdPegawai(int idPegawai) { this.idPegawai = idPegawai; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
    public void setGajiPokok(BigDecimal gajiPokok) { this.gajiPokok = gajiPokok; }
    public void setTunjangan(BigDecimal tunjangan) { this.tunjangan = tunjangan; }
    public void setTotalGaji(BigDecimal totalGaji) { this.totalGaji = totalGaji; }
}