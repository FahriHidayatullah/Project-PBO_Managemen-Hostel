/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.ReservasiDAO;
import DAO.KamarDAO;
import Model.Reservasi;
import java.util.List;

public class ReservasiController {

    private ReservasiDAO dao = new ReservasiDAO();
    private KamarDAO kamarDao = new KamarDAO();

    public List<Reservasi> getAllReservasi() {
        return dao.getAll();
    }

    public Reservasi getReservasiById(int id) {
        return dao.getById(id);
    }

    public boolean addReservasi(Reservasi r) {
        return dao.insert(r);
    }

    public boolean updateReservasi(Reservasi r) {
        return dao.update(r);
    }

    public boolean deleteReservasi(int id) {
        return dao.delete(id);
    }

    public boolean updateStatus(int id, String status) {
        return dao.updateStatus(id, status);
    }

    public boolean updateStatusByReservasi(int idReservasi, String status) {
        return dao.updateStatusByReservasi(idReservasi, status);
    }

    public boolean updateStatusReservasiDanKamar(int idReservasi, String statusReservasi, int idKamar, String statusKamar) {
        return dao.updateStatusReservasiDanKamar(idReservasi, statusReservasi, idKamar, statusKamar);
    }

    /**
     * Proses Check-In: update status reservasi menjadi "Check-In" dan kamar
     * menjadi "terisi"
     */
    public boolean checkIn(int idReservasi) {
        Reservasi r = dao.getById(idReservasi);
        if (r == null) {
            return false;
        }
        // Update status reservasi dan kamar secara otomatis
        return dao.updateStatusReservasiDanKamar(idReservasi, "Check-In", r.getIdKamar(), "terisi");
    }

    /**
     * Proses Check-Out: update status reservasi menjadi "Check-Out" dan kamar
     * menjadi "tersedia"
     */
    public boolean checkOut(int idReservasi) {
        Reservasi r = dao.getById(idReservasi);
        if (r == null) {
            return false;
        }
        // Update status reservasi dan kamar secara otomatis
        return dao.updateStatusReservasiDanKamar(idReservasi, "Check-Out", r.getIdKamar(), "tersedia");
    }

    /**
     * Check apakah ada reservasi aktif pada kamar tertentu
     */
    public boolean hasActiveReservasi(int idKamar) {
        return dao.hasActiveReservasi(idKamar);
    }

    /**
     * Get reservasi aktif (Check-In) pada kamar tertentu
     */
    public Reservasi getActiveReservasiByKamar(int idKamar) {
        return dao.getActiveReservasiByKamar(idKamar);
    }
}
