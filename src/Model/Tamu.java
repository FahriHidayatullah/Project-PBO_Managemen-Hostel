/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

public class Tamu {
    private int id;
    private String nama;
    private String noKtp;
    private String noTelepon;
    private String alamat;
    private Date tanggalLahir;
    
    public Tamu() {}
    
    public Tamu(int id, String nama, String noKtp, String noTelepon, String alamat, Date tanggalLahir) {
        this.id = id;
        this.nama = nama;
        this.noKtp = noKtp;
        this.noTelepon = noTelepon;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
    }
    
    // Getters
    public int getId() { 
        return id; 
    }
    
    public String getNama() { 
        return nama; 
    }
    
    public String getNoKtp() { 
        return noKtp; 
    }
    
    public String getNoTelepon() { 
        return noTelepon; 
    }
    
    public String getAlamat() { 
        return alamat; 
    }
    
    public Date getTanggalLahir() { 
        return tanggalLahir; 
    }
    
    // Setters
    public void setId(int id) { 
        this.id = id; 
    }
    
    public void setNama(String nama) { 
        this.nama = nama; 
    }
    
    public void setNoKtp(String noKtp) { 
        this.noKtp = noKtp; 
    }
    
    public void setNoTelepon(String noTelepon) { 
        this.noTelepon = noTelepon; 
    }
    
    public void setAlamat(String alamat) { 
        this.alamat = alamat; 
    }
    
    public void setTanggalLahir(Date tanggalLahir) { 
        this.tanggalLahir = tanggalLahir; 
    }
    
    // FIXED: Added toString method for debugging
    @Override
    public String toString() {
        return nama + " (" + noKtp + ")";
    }
    
    // FIXED: Added equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tamu tamu = (Tamu) obj;
        return id == tamu.id;
    }
}