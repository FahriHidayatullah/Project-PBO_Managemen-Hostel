/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import DAO.PembayaranDAO; 
import Model.Pembayaran; 
import java.util.List;

public class PembayaranController {
    private PembayaranDAO dao = new PembayaranDAO();
    
    public boolean insertPembayaran(Pembayaran p) { return dao.insert(p); }
    
    public List<Pembayaran> getAllPembayaran() { return dao.getAll(); }
    
    public boolean updateStatusPembayaran(int id, String status) {
        return dao.updateStatusPembayaran(id, status);
    }
    
    public boolean updateStatusPembayaranDanReservasi(int idPembayaran, String statusPembayaran, int idReservasi, String statusReservasi) {
        return dao.updateStatusPembayaranDanReservasi(idPembayaran, statusPembayaran, idReservasi, statusReservasi);
    }
}