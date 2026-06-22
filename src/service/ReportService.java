package service;

import dao.TicketDAO;
import model.Ticket;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class ReportService {

    TicketDAO dao = new TicketDAO();
    Scanner sc = new Scanner(System.in);

    // Raise Ticket
    public void raiseTicket() {

        Ticket t = new Ticket();

        System.out.print("Enter issue title: ");
        sc.nextLine(); // clear buffer
        t.setTitle(sc.nextLine());

        System.out.print("Enter category (Hardware/Software/Network): ");
        t.setCategory(sc.nextLine());

        System.out.print("Enter priority (Low/Medium/High): ");
        t.setPriority(sc.nextLine());

        t.setStatus("Open");
        t.setCreatedDate(new Date(System.currentTimeMillis()));

        System.out.print("Assign engineer name: ");
        t.setAssignedTo(sc.nextLine());

        t.setResolvedDate(null);

        dao.addTicket(t);
    }

    // Close Ticket (ADMIN) 
    public void closeTicket() {
        System.out.print("Enter Ticket ID to close: ");
        int id = sc.nextInt();
        dao.closeTicket(id);
    }

    // Reports
    public List<String[]> getCategoryReport() {
        return dao.getTicketsByCategory();
    }

    public List<String[]> getEngineerReport() {
        return dao.getTicketsByEngineer();
    }

    public List<String[]> getMonthlyTrend() {
        return dao.getMonthlyTrend();
    }

    public List<String[]> getSLAReport() {
        return dao.getSLAReport();
    }
}
