package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.Invoice;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class InvoiceToPdf {
    public void invoiceToPdf(Invoice invoice) {
        String dest = "styled66666_invoice.pdf";
        try {
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            // Fonts for styling
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font regularFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            // Add Invoice Title
            document.add(new Paragraph("Invoice No. 1", titleFont));
            document.add(new Paragraph(" ")); // Blank space

            // Add Issue and Due Dates
            PdfPTable dateTable = new PdfPTable(3);
            dateTable.setWidthPercentage(100);
            dateTable.setWidths(new float[]{33, 33, 33});
            dateTable.addCell(createCell("Issue date: 2024-09-20", Element.ALIGN_RIGHT, regularFont));
            dateTable.addCell(createCell("Due date: 2024-09-21", Element.ALIGN_CENTER, regularFont));
            dateTable.addCell(createCell("Payment type: Transfer", Element.ALIGN_RIGHT, regularFont));
            document.add(dateTable);
            document.add(new Paragraph(" ")); // Blank space

            // Add Seller and Buyer Information
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new float[]{50, 50});

            PdfPCell sellerCell = new PdfPCell();
            sellerCell.setBorder(Rectangle.NO_BORDER);
            sellerCell.addElement(new Paragraph("Seller", boldFont));
            sellerCell.addElement(new Paragraph("John Kim", regularFont));
            sellerCell.addElement(new Paragraph("Barboros Mahallesi Halk Caddesi No:6", regularFont));
            sellerCell.addElement(new Paragraph("Kahta", regularFont));
            sellerCell.addElement(new Paragraph("34746 Ataşehir, Turkey", regularFont));
            sellerCell.addElement(new Paragraph("VAT ID 123456", regularFont));
            sellerCell.addElement(new Paragraph("Bank account: 123456789101112, Bank: Techcombank", regularFont));
            infoTable.addCell(sellerCell);

            PdfPCell buyerCell = new PdfPCell();
            buyerCell.setBorder(Rectangle.NO_BORDER);
            buyerCell.addElement(new Paragraph("Buyer", boldFont));
            buyerCell.addElement(new Paragraph("Nguyễn Trí", regularFont));
            buyerCell.addElement(new Paragraph("Barboros Mahallesi Halk Caddesi No:6", regularFont));
            buyerCell.addElement(new Paragraph("Kahta", regularFont));
            buyerCell.addElement(new Paragraph("34746 Ataşehir, Turkey", regularFont));
            infoTable.addCell(buyerCell);

            document.add(infoTable);
            document.add(new Paragraph(" ")); // Blank space

            // Add Table for Items
            PdfPTable itemTable = new PdfPTable(7);
            itemTable.setWidthPercentage(100);
            itemTable.setWidths(new float[]{5, 25, 10, 15, 10, 10, 15});
            itemTable.setSpacingBefore(10f);

            // Table Header
            String[] headers = {"No.", "Item", "Qty", "Unit net price", "Total net", "VAT %", "Total gross"};
            for (String header : headers) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, boldFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                headerCell.setPadding(8);
                itemTable.addCell(headerCell);
            }

            // Table Rows
            addRowToItemTable(itemTable, "1", "Chair", "1", "12.00", "12.00", "10", "13.20", regularFont);
            addRowToItemTable(itemTable, "2", "Table", "1", "8.00", "8.00", "20", "9.60", regularFont);
            addRowToItemTable(itemTable, "2", "Table", "1", "8.00", "8.00", "20", "9.60", regularFont);
            addRowToItemTable(itemTable, "2", "Table", "1", "8.00", "8.00", "20", "9.60", regularFont);
            addRowToItemTable(itemTable, "2", "Table", "1", "8.00", "8.00", "20", "9.60", regularFont);
            // Add Tax Row
            PdfPCell taxRateCell = createCell("Total", Element.ALIGN_RIGHT, boldFont);
            taxRateCell.setColspan(4);
            itemTable.addCell(taxRateCell);
            itemTable.addCell(createCell("20", Element.ALIGN_CENTER, regularFont));
            itemTable.addCell(createCell("20", Element.ALIGN_CENTER, regularFont));
            itemTable.addCell(createCell("9.60", Element.ALIGN_RIGHT, regularFont));
            document.add(itemTable);


            document.add(new Paragraph(" ")); // Blank space

            // Add Totals Section
//            PdfPTable totalsTable = new PdfPTable(2);
//            totalsTable.setWidthPercentage(50);
//            totalsTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            totalsTable.setWidths(new float[]{70, 30});
//
//            totalsTable.addCell(createCell("Total net price", Element.ALIGN_RIGHT, boldFont));
//            totalsTable.addCell(createCell("USD 20.00", Element.ALIGN_RIGHT, regularFont));
//
//            totalsTable.addCell(createCell("VAT amount", Element.ALIGN_RIGHT, boldFont));
//            totalsTable.addCell(createCell("USD 2.80", Element.ALIGN_RIGHT, regularFont));
//
//            totalsTable.addCell(createCell("Total gross price", Element.ALIGN_RIGHT, boldFont));
//            totalsTable.addCell(createCell("USD 22.80", Element.ALIGN_RIGHT, regularFont));
            PdfPTable totalsTable = new PdfPTable(2);
            totalsTable.setWidthPercentage(50);
            totalsTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalsTable.setWidths(new float[]{70, 30});

            totalsTable.addCell(createNoBorderCell("Total net price", Element.ALIGN_RIGHT, boldFont));
            totalsTable.addCell(createNoBorderCell("USD 20.00", Element.ALIGN_RIGHT, regularFont));

            totalsTable.addCell(createNoBorderCell("VAT amount", Element.ALIGN_RIGHT, boldFont));
            totalsTable.addCell(createNoBorderCell("USD 2.80", Element.ALIGN_RIGHT, regularFont));

            totalsTable.addCell(createNoBorderCell("Total gross price", Element.ALIGN_RIGHT, boldFont));
            totalsTable.addCell(createNoBorderCell("USD 22.80", Element.ALIGN_RIGHT, regularFont));

            document.add(totalsTable);
            document.add(new Paragraph(" ")); // Blank space

            // Add Final Total
            Paragraph totalDue = new Paragraph("Total due: USD 22.80", boldFont);
            totalDue.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalDue);

            document.close();
            System.out.println("Styled invoice created: " + dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addRowToItemTable(PdfPTable table, String no, String item, String qty, String unitPrice, String totalNet, String vat, String totalGross, Font font) {
        table.addCell(createCell(no, Element.ALIGN_CENTER, font));
        table.addCell(createCell(item, Element.ALIGN_LEFT, font));
        table.addCell(createCell(qty, Element.ALIGN_CENTER, font));
        table.addCell(createCell(unitPrice, Element.ALIGN_RIGHT, font));
        table.addCell(createCell(totalNet, Element.ALIGN_RIGHT, font));
        table.addCell(createCell(vat, Element.ALIGN_CENTER, font));
        table.addCell(createCell(totalGross, Element.ALIGN_RIGHT, font));
    }

    private static PdfPCell createCell(String content, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(5);
        return cell;
    }
    private static PdfPCell createNoBorderCell(String content, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        return cell;
    }
}
