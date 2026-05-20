/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.ReservasiDAO;
import Model.Reservasi;
import java.util.List;

public class ReservasiController {
    private ReservasiDAO dao = new ReservasiDAO();

    public List<Reservasi> getAllReservasi() { return dao.getAll(); }

    public Reservasi getReservasiById(int id) { return dao.getById(id); }

    public boolean addReservasi(Reservasi r) { return dao.insert(r); }

    public boolean updateReservasi(Reservasi r) { return dao.update(r); }

    public boolean deleteReservasi(int id) { return dao.delete(id); }

    public boolean updateStatus(int id, String status) { return dao.updateStatus(id, status); }

    public boolean updateStatusByReservasi(int idReservasi, String status) { 
        return dao.updateStatusByReservasi(idReservasi, status); 
    }

    public boolean updateStatusReservasiDanKamar(int idReservasi, String statusReservasi, int idKamar, String statusKamar) {
        return dao.updateStatusReservasiDanKamar(idReservasi, statusReservasi, idKamar, statusKamar);
    }
}