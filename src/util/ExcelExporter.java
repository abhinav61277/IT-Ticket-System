package util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.util.List;

public class ExcelExporter {

    // ================= MAIN DASHBOARD EXPORT =================
    public static void exportDashboard(
            List<String[]> categoryData,
            List<String[]> engineerData,
            List<String[]> monthlyData,
            List<String[]> slaData) {

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            // Dashboard summary
            createDashboardSheet(workbook, categoryData, slaData);

            // Charts
            createPieChartSheet(workbook, "Category Distribution", categoryData);
            createBarChartSheet(workbook, "Engineer Workload", engineerData);
            createBarChartSheet(workbook, "Monthly Trend", monthlyData);
            createBarChartSheet(workbook, "SLA Report", slaData);

            FileOutputStream out = new FileOutputStream("IT_Ticket_Dashboard.xlsx");
            workbook.write(out);

            System.out.println("Dashboard Excel Generated Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= DASHBOARD SUMMARY SHEET =================
    private static void createDashboardSheet(
            XSSFWorkbook workbook,
            List<String[]> categoryData,
            List<String[]> slaData) {

        XSSFSheet sheet = workbook.createSheet("Dashboard");

        sheet.createRow(0).createCell(0).setCellValue("IT Ticket Dashboard");

        int total = 0;
        for(String[] row : categoryData)
            total += Integer.parseInt(row[1]);

        sheet.createRow(2).createCell(0).setCellValue("Total Tickets:");
        sheet.getRow(2).createCell(1).setCellValue(total);

        sheet.createRow(3).createCell(0).setCellValue("Avg Resolution Days:");
        sheet.getRow(3).createCell(1).setCellValue(Double.parseDouble(slaData.get(0)[1]));

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }

    // ================= PIE CHART (CATEGORY) =================
    private static void createPieChartSheet(
            XSSFWorkbook workbook,
            String sheetName,
            List<String[]> data) {

        XSSFSheet sheet = workbook.createSheet(sheetName);

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Category");
        header.createCell(1).setCellValue("Tickets");

        int rowNum = 1;
        for (String[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowData[0]);
            row.createCell(1).setCellValue(Double.parseDouble(rowData[1]));
        }

        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0,0,0,0,3,1,10,20);
        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText(sheetName);

        XDDFDataSource<String> categories =
                XDDFDataSourcesFactory.fromStringCellRange(
                        sheet, new CellRangeAddress(1, data.size(), 0, 0));

        XDDFNumericalDataSource<Double> values =
                XDDFDataSourcesFactory.fromNumericCellRange(
                        sheet, new CellRangeAddress(1, data.size(), 1, 1));

        XDDFChartData pieData = chart.createData(ChartTypes.PIE, null, null);
        pieData.addSeries(categories, values);
        chart.plot(pieData);
    }

    // ================= BAR CHART SHEETS =================
    private static void createBarChartSheet(
            XSSFWorkbook workbook,
            String sheetName,
            List<String[]> data) {

        XSSFSheet sheet = workbook.createSheet(sheetName);

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Type");
        header.createCell(1).setCellValue("Count");

        int rowNum = 1;
        for (String[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowData[0]);
            row.createCell(1).setCellValue(Double.parseDouble(rowData[1]));
        }

        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0,0,0,0,3,1,10,20);
        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText(sheetName);

        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);

        XDDFDataSource<String> categories =
                XDDFDataSourcesFactory.fromStringCellRange(
                        sheet, new CellRangeAddress(1, data.size(), 0, 0));

        XDDFNumericalDataSource<Double> values =
                XDDFDataSourcesFactory.fromNumericCellRange(
                        sheet, new CellRangeAddress(1, data.size(), 1, 1));

        XDDFChartData chartData =
                chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);

        chartData.addSeries(categories, values);
        chart.plot(chartData);
    }
}
