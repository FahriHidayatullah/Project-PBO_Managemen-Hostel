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
}