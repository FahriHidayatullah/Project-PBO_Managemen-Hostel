/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.LaporanDAO;
import Model.Reservasi;
import java.util.List;

public class LaporanController {
    
    private LaporanDAO dao = new LaporanDAO();
    
    public List<Reservasi> getLaporanPendapatan() {
        return dao.getLaporanPendapatan();
    }
    
    public double hitungTotalPendapatan() {
        return dao.getTotalPendapatan();
    }
    
    public List<Reservasi> getLaporanBulanan(String bulan, String tahun) {
        return dao.getLaporanBulanan(bulan, tahun);
    }
}