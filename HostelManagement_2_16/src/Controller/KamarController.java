/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.KamarDAO;
import Model.Kamar;
import java.util.List;

public class KamarController {
    private KamarDAO dao = new KamarDAO();

    public List<Kamar> getAllKamar() {
        return dao.getAllKamar();
    }

    public boolean insertKamar(Kamar kamar) {
        return dao.insertKamar(kamar);
    }

    public boolean updateKamar(Kamar kamar) {
        return dao.updateKamar(kamar);
    }

    public boolean deleteKamar(int id) {
        return dao.deleteKamar(id);
    }

    public boolean updateKamarStatus(int idKamar, String status) {
        return dao.updateKamarStatus(idKamar, status);
    }

    /**
     * Update status kamar dengan validasi
     * Validasi: kamar "terisi" tidak boleh diubah jika masih ada reservasi Check-In aktif
     */
    public boolean updateKamarStatusWithValidation(int idKamar, String newStatus) {
        // Cek apakah kamar boleh diubah statusnya
        if (!dao.canChangeKamarStatus(idKamar)) {
            System.err.println("Kamar masih terisi dengan reservasi aktif. Tidak bisa mengubah status.");
            return false;
        }
        return dao.updateKamarStatus(idKamar, newStatus);
    }

    /**
     * Get Kamar by ID
     */
    public Kamar getKamarById(int id) {
        return dao.getKamarById(id);
    }

    /**
     * Check apakah kamar memiliki reservasi aktif (Check-In)
     */
    public boolean hasActiveReservasi(int idKamar) {
        return dao.hasActiveReservasi(idKamar);
    }
}