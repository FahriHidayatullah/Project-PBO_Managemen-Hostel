/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.List;

public interface IDAO<T> {

    List<T> getAll();

    T getById(int id);

    boolean insert(T entity);

    boolean update(T entity);

    boolean delete(int id);
}
