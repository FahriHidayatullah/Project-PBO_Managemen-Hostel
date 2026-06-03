/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Pembayaran {

    private int id;
    private int idReservasi;
    private BigDecimal jumlahBayar;
    private String metodePembayaran;
    private Timestamp tanggalBayar;
    private String status;

    public Pembayaran() {
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdReservasi() {
        return idReservasi;
    }

    public void setIdReservasi(int idReservasi) {
        this.idReservasi = idReservasi;
    }

    public BigDecimal getJumlahBayar() {
        return jumlahBayar;
    }

    public void setJumlahBayar(BigDecimal jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public Timestamp getTanggalBayar() {
        return tanggalBayar;
    }

    public void setTanggalBayar(Timestamp tanggalBayar) {
        this.tanggalBayar = tanggalBayar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
