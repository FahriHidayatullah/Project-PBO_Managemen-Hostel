/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.TamuDAO;
import Model.Tamu;
import java.util.List;

public class TamuController {

    private TamuDAO dao = new TamuDAO();

    public List<Tamu> getAllTamu() {
        return dao.getAll();
    }

    public boolean insertTamu(Tamu t) {
        return dao.insert(t);
    }

    public boolean updateTamu(Tamu t) {
        return dao.update(t);
    }

    public boolean deleteTamu(int id) {
        return dao.delete(id);
    }
}
