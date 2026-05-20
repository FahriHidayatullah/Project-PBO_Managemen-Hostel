/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;

public class Kamar {
    private int id;
    private String nomorKamar;
    private String tipe;
    private BigDecimal hargaPerMalam;
    private String status;
    private String fasilitas;
    
    public Kamar() {}
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomorKamar() { return nomorKamar; }
    public void setNomorKamar(String nomorKamar) { this.nomorKamar = nomorKamar; }
    public String getTipe() { return tipe; }
    public void setTipe(String tipe) { this.tipe = tipe; }
    public BigDecimal getHargaPerMalam() { return hargaPerMalam; }
    public void setHargaPerMalam(BigDecimal hargaPerMalam) { this.hargaPerMalam = hargaPerMalam; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getFasilitas() { return fasilitas; }
    public void setFasilitas(String fasilitas) { this.fasilitas = fasilitas; }
}