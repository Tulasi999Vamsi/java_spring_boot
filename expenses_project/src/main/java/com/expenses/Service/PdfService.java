
package com.expenses.Service;

import com.expenses.Models.Expense;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdfService {
	
@Autowired
private UserService user_serv;
    public byte[] generatePdf(List<Expense> expenses, double totalAmount) throws DocumentException {
    	
    	
    	  // Retrieve current user
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	 String username = authentication.getName();
    	 Long cId=user_serv.findUserId(username);
	     
 	    


        // Filter expenses by the current user
        List<Expense> userExpenses = expenses.stream()
            .filter(expense -> expense.getUser().getId().equals(cId))
            .collect(Collectors.toList());

        // Calculate total amount for the user's expenses
        double userTotalAmount = userExpenses.stream()
            .mapToDouble(Expense::getAmount)
            .sum();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Add title
            document.add(new Paragraph("Expense Report"));

            // Create table with headers
            PdfPTable table = new PdfPTable(3);
            table.addCell("ID");
            table.addCell("Description");
            table.addCell("Amount");

            // Add data to table
            for (Expense expense : userExpenses) {
                table.addCell(String.valueOf(expense.getId()));
                table.addCell(expense.getDescription());
                table.addCell(String.valueOf(expense.getAmount()));
            }

            // Add table to document
            document.add(table);

            // Add total amount
            document.add(new Paragraph("Total Amount: " + userTotalAmount));

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DocumentException("Error generating PDF", e);
        }
    }
}
