package dao;

import util.DBConnection;
import model.Ticket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    // ================= ADD TICKET =================
    public void addTicket(Ticket t) {

        String sql = "INSERT INTO tickets(title,category,priority,status,created_date,resolved_date,assigned_to) VALUES (?,?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getTitle());
            ps.setString(2, t.getCategory());
            ps.setString(3, t.getPriority());
            ps.setString(4, t.getStatus());
            ps.setDate(5, t.getCreatedDate());
            ps.setDate(6, t.getResolvedDate());
            ps.setString(7, t.getAssignedTo());

            ps.executeUpdate();
            System.out.println("Ticket Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= CLOSE TICKET =================
    public void closeTicket(int ticketId) {

        String sql = "UPDATE tickets SET status='Closed', resolved_date=CURDATE() WHERE ticket_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ticketId);
            int rows = ps.executeUpdate();

            if(rows > 0)
                System.out.println("Ticket closed successfully!");
            else
                System.out.println("Ticket not found!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= CATEGORY REPORT =================
    public List<String[]> getTicketsByCategory() {

        List<String[]> list = new ArrayList<>();
        String query = "SELECT category, COUNT(*) FROM tickets GROUP BY category";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                list.add(new String[]{ rs.getString(1), rs.getString(2) });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= ENGINEER REPORT =================
    public List<String[]> getTicketsByEngineer() {

        List<String[]> list = new ArrayList<>();
        String query = "SELECT assigned_to, COUNT(*) FROM tickets GROUP BY assigned_to";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                list.add(new String[]{ rs.getString(1), rs.getString(2) });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= MONTHLY TREND =================
    public List<String[]> getMonthlyTrend() {

        List<String[]> list = new ArrayList<>();
        String query = "SELECT MONTH(created_date), COUNT(*) FROM tickets GROUP BY MONTH(created_date)";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                list.add(new String[]{
                        "Month " + rs.getString(1),
                        rs.getString(2)
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= SLA REPORT =================
    public List<String[]> getSLAReport() {

        List<String[]> list = new ArrayList<>();
        String query = "SELECT AVG(DATEDIFF(resolved_date, created_date)) FROM tickets WHERE status='Closed'";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            if (rs.next()) {
                list.add(new String[]{
                        "Avg Resolution Days",
                        rs.getString(1)
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
