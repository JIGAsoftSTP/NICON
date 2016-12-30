/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *
 * @author ahmedjorge
 */
public class teste {
    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        Document d = new Document(PageSize.A4);
        FileOutputStream fos = new FileOutputStream("teste.pdf");
        
        PdfWriter.getInstance(d, fos);
        
        d.open();
        PdfPTable pTable = new PdfPTable(3);
        
            PdfPCell cell1 = new PdfPCell(new Phrase("111111111"));
            PdfPCell cell2 = new PdfPCell(new Phrase("222222222"));
            cell2.setRowspan(5);
            PdfPCell cell3 = new PdfPCell(new Phrase("333333333"));
            
            pTable.addCell(cell1); 
            pTable.addCell(cell2);
            pTable.addCell(cell3);
            
            pTable.addCell(cell1);
            pTable.addCell(cell3);
            pTable.addCell(cell1);
            pTable.addCell(cell3);
            
            pTable.addCell(cell1);
            pTable.addCell(cell3);
            pTable.addCell(cell1);
            pTable.addCell(cell3);
            
            pTable.addCell(cell1);
            pTable.addCell(cell3);
            pTable.addCell(cell1);
            pTable.addCell(cell3);
            
            
         
            d.add(pTable);

        d.close();
        
    }
    
}
