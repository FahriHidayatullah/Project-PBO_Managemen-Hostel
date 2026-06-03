/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.UserDAO;
import Model.User;

public class LoginController {

    private UserDAO dao = new UserDAO();

    public User login(String u, String p) {
        return dao.login(u, p);
    }

    public boolean register(User user) {
        return dao.register(user);
    }

    public boolean isUsernameAvailable(String username) {
        return dao.isUsernameAvailable(username);
    }
}
