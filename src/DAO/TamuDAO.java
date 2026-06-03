/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Tamu;
import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TamuDAO extends BaseDAO<Tamu> {

    @Override
    protected String getTableName() {
        return "tamu";
    }

    @Override
    protected Tamu mapResultSet(ResultSet rs) throws SQLException {
        Tamu t = new Tamu();
        t.setId(rs.getInt("id"));
        t.setNama(rs.getString("nama"));
        t.setNoKtp(rs.getString("no_ktp"));
        t.setNoTelepon(rs.getString("no_telepon"));
        t.setAlamat(rs.getString("alamat"));
        t.setTanggalLahir(rs.getDate("tanggal_lahir"));
        return t;
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO tamu (nama, no_ktp, no_telepon, alamat, tanggal_lahir) VALUES (?,?,?,?,?)";
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, Tamu t) throws SQLException {
        stmt.setString(1, t.getNama());
        stmt.setString(2, t.getNoKtp());
        stmt.setString(3, t.getNoTelepon());
        stmt.setString(4, t.getAlamat());
        stmt.setDate(5, t.getTanggalLahir());
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE tamu SET nama=?, no_ktp=?, no_telepon=?, alamat=?, tanggal_lahir=? WHERE id=?";
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, Tamu t) throws SQLException {
        stmt.setString(1, t.getNama());
        stmt.setString(2, t.getNoKtp());
        stmt.setString(3, t.getNoTelepon());
        stmt.setString(4, t.getAlamat());
        stmt.setDate(5, t.getTanggalLahir());
        stmt.setInt(6, t.getId());
    }
}
