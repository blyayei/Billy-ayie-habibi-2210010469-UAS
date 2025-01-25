package model;

public class Pegawai {
    private int idPegawai;
    private String nama;
    private String nip;
    private String jabatan;
    private String email;
    private String telepon;

    public Pegawai() {}

    public Pegawai(String nama, String nip, String jabatan, String email, String telepon) {
        this.nama = nama;
        this.nip = nip;
        this.jabatan = jabatan;
        this.email = email;
        this.telepon = telepon;
    }

    // Getter
    public int getIdPegawai() { return idPegawai; }
    public String getNama() { return nama; }
    public String getNip() { return nip; }
    public String getJabatan() { return jabatan; }
    public String getEmail() { return email; }
    public String getTelepon() { return telepon; }

    // Setter
    public void setIdPegawai(int idPegawai) { this.idPegawai = idPegawai; }
    public void setNama(String nama) { this.nama = nama; }
    public void setNip(String nip) { this.nip = nip; }
    public void setJabatan(String jabatan) { this.jabatan = jabatan; }
    public void setEmail(String email) { this.email = email; }
    public void setTelepon(String telepon) { this.telepon = telepon; }
}