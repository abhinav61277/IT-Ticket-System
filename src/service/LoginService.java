package service;

import dao.UserDAO;
import java.util.Scanner;

public class LoginService {

    UserDAO dao = new UserDAO();

    // pass scanner from MainApp
    public String login(Scanner sc) {

        System.out.println("\n===== LOGIN =====");

        System.out.print("Username: ");
        String username = sc.next();

        System.out.print("Password: ");
        String password = sc.next();

        String role = dao.login(username, password);

        if(role == null) {
            System.out.println("Invalid credentials!");
            return null;
        }

        System.out.println("Login successful! Role: " + role);
        return role;
    }
}
