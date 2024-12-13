package com.example.invoiceProject.Service;

import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.DetailInvoice;
import com.example.invoiceProject.Model.Invoice;
import com.example.invoiceProject.Model.Product;
import com.example.invoiceProject.Repository.DetailInvoiceRepository;
import com.example.invoiceProject.Repository.ProductRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class InvoiceToPdf {


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DetailInvoiceRepository detailInvoiceRepository;


    public void invoiceToPdf(Invoice invoice) {


        String desktopPath = System.getProperty("user.home") + "/Desktop/";
        String dest = desktopPath + invoice.getSequenceNo().toString() + ".pdf";


//        String dest = invoice.getSequenceNo().toString()+".pdf";
        try {

            //Get information
            String invoiceNo = invoice.getSequenceNo().toString();
            String issueDate = invoice.getInvoiceDate().toString();
            String paymentDate = invoice.getPaymentTime().toString();
            String paymentType = invoice.getPaymentType();

            //Seller information
            String lastName = invoice.getUser().getLastName();
            String firstName = invoice.getUser().getFirstName();
            String street = invoice.getDepartment().getAddress().getStreet();
            String city = invoice.getDepartment().getAddress().getCity();
            String country = invoice.getDepartment().getAddress().getCountry();
            String postCode = invoice.getDepartment().getAddress().getPostCode();
            String taxId = invoice.getDepartment().getTaxId();
            String bankAcc = invoice.getDepartment().getBankAccount();
            String bank = invoice.getDepartment().getBank();

            //Buyer information
            String lastNameV = invoice.getVendor().getLastname();
            String firstNameV = invoice.getVendor().getFirstname();
            String streetV = invoice.getVendor().getVendorAddress().getStreet();
            String cityV = invoice.getVendor().getVendorAddress().getCity();
            String countryV = invoice.getVendor().getVendorAddress().getCountry();
            String postCodeV = invoice.getVendor().getVendorAddress().getPostCode();
            String taxIdV = invoice.getVendor().getTaxIdentificationNumber();
            String bankAccV = invoice.getVendor().getBankAccount();
            String bankV = invoice.getVendor().getBank();
            System.out.println("VEndor tax id is: "+ taxIdV);
            System.out.println("VEndor Bank and account id is: "+ bankAccV+" "+bankV);


            //Get list of item of invoice
            Map<UUID, Integer> mp = new HashMap<>();
            List<DetailInvoice> items = detailInvoiceRepository.findByInvoice_invoiceNo(invoice.getInvoiceNo());
            System.out.println("List size: " + items.size());

            items.stream()
                    .forEach(item -> {
                        UUID productNo = item.getProduct().getId();
                        mp.put(productNo, mp.getOrDefault(productNo, 0) + item.getQuantity());
                    });

            System.out.println(mp.size());
           for(var entry : mp.entrySet()){
               System.out.println(entry.getKey()+" "+entry.getValue());
           }




            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            // Fonts for styling
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font regularFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            // Add Invoice Title
            document.add(new Paragraph("Invoice No. "+invoiceNo, titleFont));
            document.add(new Paragraph(" ")); // Blank space

            // Add Issue and Due Dates
            PdfPTable dateTable = new PdfPTable(1);
            dateTable.setWidthPercentage(100);
            dateTable.setWidths(new float[]{100});

            PdfPCell sellerCell1 = new PdfPCell();
            sellerCell1.setBorder(Rectangle.NO_BORDER);
            sellerCell1.addElement(new Paragraph("Issue date: "+issueDate, boldFont));
            sellerCell1.addElement(new Paragraph("Due date: "+paymentDate, boldFont));
            sellerCell1.addElement(new Paragraph("Payment type: "+paymentType, boldFont));

            dateTable.addCell(sellerCell1);
            document.add(dateTable);


            document.add(new Paragraph(" ")); // Blank space

            // Add Seller and Buyer Information
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new float[]{50, 50});

            PdfPCell sellerCell = new PdfPCell();
            sellerCell.setBorder(Rectangle.NO_BORDER);
            sellerCell.addElement(new Paragraph("Seller", boldFont));
            sellerCell.addElement(new Paragraph(lastName+" "+firstName, regularFont));
            sellerCell.addElement(new Paragraph(street, regularFont));
//            sellerCell.addElement(new Paragraph("Kahta", regularFont));
            sellerCell.addElement(new Paragraph(postCode+" "+city+" "+country, regularFont));
            sellerCell.addElement(new Paragraph("Tax Id: "+taxId, regularFont));
            sellerCell.addElement(new Paragraph("Bank account: "+ bankAcc, regularFont));
            sellerCell.addElement(new Paragraph("Bank : "+ bank, regularFont));
            infoTable.addCell(sellerCell);

            PdfPCell buyerCell = new PdfPCell();
            buyerCell.setBorder(Rectangle.NO_BORDER);
            buyerCell.addElement(new Paragraph("Buyer", boldFont));
            buyerCell.addElement(new Paragraph(firstNameV+" "+lastNameV, regularFont));
            buyerCell.addElement(new Paragraph(streetV, regularFont));
//            buyerCell.addElement(new Paragraph("Kahta", regularFont));
            buyerCell.addElement(new Paragraph(postCodeV+" "+cityV+" "+countryV, regularFont));
            buyerCell.addElement(new Paragraph("Tax Id: "+taxIdV, regularFont));
            buyerCell.addElement(new Paragraph("Bank account: "+ bankAcc, regularFont));
            buyerCell.addElement(new Paragraph("Bank : "+ bank, regularFont));
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

            int i=0;
            Double finalTotalNet = 0.0;
            Double finalTotalGross = 0.0;
            for(var item:mp.entrySet()){
                Product product = productRepository.findById(item.getKey())
                                .orElseThrow(()-> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
                String quantity = String.valueOf(item.getValue());
                String unitPrice = String.valueOf(product.getPrice());
                String totalNet = String.valueOf(product.getPrice()*item.getValue());
                String tax = String.valueOf(product.getTax());
                String totalGross = String.valueOf(product.getPrice()*item.getValue());
                finalTotalNet+=product.getPrice();
                finalTotalGross+=product.getPrice()*item.getValue();
                addRowToItemTable(itemTable, String.valueOf(i+=1), product.getName(), quantity, unitPrice, totalNet, tax, totalGross, regularFont );
            }





            // Add Tax Row
            PdfPCell taxRateCell = createCell("Total", Element.ALIGN_RIGHT, boldFont);
            taxRateCell.setColspan(4);
            itemTable.addCell(taxRateCell);
            itemTable.addCell(createCell("20", Element.ALIGN_CENTER, regularFont));
            itemTable.addCell(createCell("20", Element.ALIGN_CENTER, regularFont));
            itemTable.addCell(createCell("9.60", Element.ALIGN_RIGHT, regularFont));
            document.add(itemTable);


            document.add(new Paragraph(" ")); // Blank space


            PdfPTable totalsTable = new PdfPTable(2);
            totalsTable.setWidthPercentage(50);
            totalsTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalsTable.setWidths(new float[]{70, 30});

            totalsTable.addCell(createNoBorderCell("Total net price", Element.ALIGN_RIGHT, boldFont));
            totalsTable.addCell(createNoBorderCell("VND "+finalTotalNet, Element.ALIGN_RIGHT, regularFont));

//            totalsTable.addCell(createNoBorderCell("VAT amount", Element.ALIGN_RIGHT, boldFont));
//            totalsTable.addCell(createNoBorderCell("USD 2.80", Element.ALIGN_RIGHT, regularFont));

            totalsTable.addCell(createNoBorderCell("Total gross price", Element.ALIGN_RIGHT, boldFont));
            totalsTable.addCell(createNoBorderCell("VND "+finalTotalGross, Element.ALIGN_RIGHT, regularFont));

            document.add(totalsTable);
            document.add(new Paragraph(" ")); // Blank space

            // Add Final Total
            Paragraph totalDue = new Paragraph("Total due: VND "+finalTotalGross, boldFont);
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
