package dao;

import util.DBConnection;
import java.sql.*;

public class UserDAO {

    public String login(String username, String password) {

        String role = null;

        String sql = "SELECT role FROM users WHERE username=? AND password=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next())
                role = rs.getString("role");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return role;
    }
}
