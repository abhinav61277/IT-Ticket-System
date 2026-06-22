package main;

import service.*;
import util.ExcelExporter;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        LoginService loginService = new LoginService();
        ReportService service = new ReportService();

        String role = loginService.login(sc);

        if(role == null) return;

        // USER MENU
        if(role.equals("USER")) {
            while(true) {
                System.out.println("\n===== USER MENU =====");
                System.out.println("1. Raise Ticket");
                System.out.println("2. Exit");

                int ch = sc.nextInt();

                if(ch == 1)
                    service.raiseTicket();
                else
                    System.exit(0);
            }
        }

        // ADMIN MENU
        if(role.equals("ADMIN")) {
            while(true) {
                System.out.println("\n===== ADMIN MENU =====");
                System.out.println("1. Raise Ticket");
                System.out.println("2. Close Ticket");
                System.out.println("3. Generate Dashboard");
                System.out.println("4. Exit");

                int ch = sc.nextInt();

                switch(ch) {
                    case 1: service.raiseTicket(); break;
                    case 2: service.closeTicket(); break;
                    case 3:
                        ExcelExporter.exportDashboard(
                            service.getCategoryReport(),
                            service.getEngineerReport(),
                            service.getMonthlyTrend(),
                            service.getSLAReport()
                        );
                        break;
                    case 4: System.exit(0);
                }
            }
        }
    }
}
