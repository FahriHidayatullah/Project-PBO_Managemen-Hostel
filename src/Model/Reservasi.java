/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Reservasi {
    private int id;
    private int idTamu;
    private int idKamar;
    private Date tanggalCheckIn;
    private Date tanggalCheckOut;
    private int jumlahMalam;
    private BigDecimal totalHarga;
    private String status;
    private Timestamp tanggalBooking;
    private String namaTamu;
    private String nomorKamar;
    
    // Getters dan Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdTamu() { return idTamu; }
    public void setIdTamu(int idTamu) { this.idTamu = idTamu; }
    public int getIdKamar() { return idKamar; }
    public void setIdKamar(int idKamar) { this.idKamar = idKamar; }
    public Date getTanggalCheckIn() { return tanggalCheckIn; }
    public void setTanggalCheckIn(Date tanggalCheckIn) { this.tanggalCheckIn = tanggalCheckIn; }
    public Date getTanggalCheckOut() { return tanggalCheckOut; }
    public void setTanggalCheckOut(Date tanggalCheckOut) { this.tanggalCheckOut = tanggalCheckOut; }
    public int getJumlahMalam() { return jumlahMalam; }
    public void setJumlahMalam(int jumlahMalam) { this.jumlahMalam = jumlahMalam; }
    public BigDecimal getTotalHarga() { return totalHarga; }
    public void setTotalHarga(BigDecimal totalHarga) { this.totalHarga = totalHarga; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Timestamp getTanggalBooking() { return tanggalBooking; }
    public void setTanggalBooking(Timestamp tanggalBooking) { this.tanggalBooking = tanggalBooking; }
    public String getNamaTamu() { return namaTamu; }
    public void setNamaTamu(String namaTamu) { this.namaTamu = namaTamu; }
    public String getNomorKamar() { return nomorKamar; }
    public void setNomorKamar(String nomorKamar) { this.nomorKamar = nomorKamar; }
}